package br.com.bancocbss.parcele.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ClientePersistenceTest {
	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war").addPackage(Cliente.class.getPackage())
				.addAsResource("test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	private static final String[][] CLIENTE_NAME_MAILs = {
			{ "Super Mario Brothers", "123456", "Super.Mario@nantendinho.com" },
			{ "Mario Kart", "987654", "Mario.Kart@nantendinho.com" },
			{ "F-Zero", "852963", "F-Zero@nantendinho.com" } };

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	// tests go here

	// invoked before each test,
	@Before
	public void preparePersistenceTest() throws Exception {
		clearData();
		insertData();
		startTransaction();
	}

	private void clearData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Dumping old records...");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<Cliente> criteriaDelete = cb.createCriteriaDelete(Cliente.class);
		criteriaDelete.from(Cliente.class);
		em.createQuery(criteriaDelete).executeUpdate();
		utx.commit();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Inserting records...");
		for (String[] cliente_nome_mail : CLIENTE_NAME_MAILs) {
			Cliente cliente = new Cliente(cliente_nome_mail[0], cliente_nome_mail[1], cliente_nome_mail[2]);
			em.persist(cliente);
		}
		utx.commit();
		// clear the persistence context (first-level cache)
		em.clear();
	}

	private void startTransaction() throws Exception {
		utx.begin();
		em.joinTransaction();
	}

	@After
	public void commitTransaction() throws Exception {
		utx.commit();
	}

	@Test
	public void shouldFindAllGamesUsingJpqlQuery() throws Exception {
		// given
		String fetchingAllGamesInJpql = "select g from Cliente g order by g.id";

		// when
		System.out.println("Selecting (using JPQL)...");
		List<Cliente> clientes = em.createQuery(fetchingAllGamesInJpql, Cliente.class).getResultList();

		// then
		System.out.println("Found " + clientes.size() + " games (using JPQL):");
		assertContainsAllGames(clientes);
	}

	private static void assertContainsAllGames(Collection<Cliente> retrievedClientes) {
		Assert.assertEquals(CLIENTE_NAME_MAILs.length, retrievedClientes.size());
		final Set<Set<String>> retrievedClienteNameEmail = new HashSet<>();
		for (Cliente cliente : retrievedClientes) {
			System.out.println("* " + cliente);
			HashSet<String> cli = new HashSet<String>();
			cli.add(cliente.getNome());
			cli.add(cliente.getCpf());
			cli.add(cliente.getEmail());
			retrievedClienteNameEmail.add(cli);
		}
		Assert.assertTrue(retrievedClienteNameEmail.containsAll(Arrays.asList(CLIENTE_NAME_MAILs)));
	}
}