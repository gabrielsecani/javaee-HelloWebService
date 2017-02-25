package br.com.bancocbss.parcele.model;

import java.util.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.*;
import org.junit.runner.RunWith;

import br.com.bancocbss.parcele.resources.Resources;

@RunWith(Arquillian.class)
public class ClientePersistenceTest {
	private static final String[][] CLIENTE_NAME_MAILs = {
			{ "Super Mario Brothers", "123456", "Super.Mario@nantendinho.com" },
			{ "Mario Kart", "987654", "Mario.Kart@nantendinho.com" },
			{ "F-Zero", "852963", "F-Zero@nantendinho.com" } };
	private static final List<Cliente> clientes = new ArrayList<>(3);
	@PersistenceContext
	EntityManager em;
	@Inject
	UserTransaction utx;

	@Deployment
	public static Archive<?> createDeployment() {

		// You can use war packaging...
		// WebArchive archw = ShrinkWrap.create(WebArchive.class, "test.war")
		// .addPackage(Cliente.class.getPackage())
		// .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
		// .addAsWebInfResource("jbossas-ds.xml")
		// .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

		// or jar packaging...
		JavaArchive archj = ShrinkWrap.create(JavaArchive.class)
				.addPackage(Cliente.class.getPackage())
				.addClass(Resources.class)
				.addAsManifestResource("test-persistence.xml", "persistence.xml")
				.addAsManifestResource("jbossas-ds.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

		// choose your packaging here
		return archj;

	}

	// tests go here

	private static void assertContainsAllClientes(Collection<Cliente> retrievedClientes) {
		Assert.assertEquals(CLIENTE_NAME_MAILs.length, retrievedClientes.size());
		final Set<Set<String>> retrievedClienteNameEmail = new HashSet<>();
		for (Cliente cliente : retrievedClientes) {
			System.out.println("* " + cliente);
			HashSet<String> cli = new HashSet<>();
			cli.add(cliente.getNome());
			cli.add(cliente.getCpf());
			cli.add(cliente.getEmail());
			retrievedClienteNameEmail.add(cli);
		}
		List<String[]> arr_const = Arrays.asList(CLIENTE_NAME_MAILs);
		Assert.assertTrue("Contagem não são iguais", retrievedClienteNameEmail.size() == arr_const.size());
		Assert.assertTrue("São diferentes", clientes.equals(retrievedClientes));
	}

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

		int res = em.createQuery("delete from " + Cliente._TABLE_NAME).executeUpdate();
		System.out.println(" resultado: " + res);

		utx.commit();
		// clear the persistence context (first-level cache)
		em.clear();
	}

	private void insertData() throws Exception {
		utx.begin();
		em.joinTransaction();
		System.out.println("Inserting records...");
		for (String[] cliente_nome_mail : CLIENTE_NAME_MAILs) {
			Cliente cliente = new Cliente(cliente_nome_mail[0], cliente_nome_mail[1], cliente_nome_mail[2]);
			clientes.add(cliente);
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
	public void shouldFindAllClientesUsingEntityQuery() throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> qry = cb.createQuery(Cliente.class);
		Root<Cliente> wh = qry.from(Cliente.class);

		List<Cliente> clientes = em.createQuery(qry).getResultList();

		System.out.println("Found " + clientes.size() + " clientes.");

	}

	@Test
	public void shouldFindAllClientesUsingJpqlQuery() throws Exception {
		// given
		String fetchingAllClientesInJpql = "select g from " + Cliente._TABLE_NAME + " g order by g.id";

		// when
		System.out.println("Selecting (using JPQL)...");
		List<Cliente> clientes = em.createQuery(fetchingAllClientesInJpql,
				Cliente.class).getResultList();

		// then
		System.out.println("Found " + clientes.size() + " clientes (using JPQL):");
		assertContainsAllClientes(clientes);
	}

}