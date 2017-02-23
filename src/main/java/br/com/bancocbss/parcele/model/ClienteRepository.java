package br.com.bancocbss.parcele.model;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;


@ApplicationScoped
public class ClienteRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public ClienteRepository() {
	}

	public Cliente findById(Long id) {
		log.info("findById("+Long.toString(id)+")");
		return em.find(Cliente.class, id);
	}

	public Cliente findByNome(String nome) {
		log.info("findByNome("+nome+")");
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = cb.createQuery(Cliente.class);
		Root<Cliente> cliente = criteria.from(Cliente.class);
		criteria.select(cliente).where(cb.equal(cliente.get("nome"), nome));
		return em.createQuery(criteria).getSingleResult();
	}

	public Cliente findByEmail(String email) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = cb.createQuery(Cliente.class);
		Root<Cliente> cliente = criteria.from(Cliente.class);
		criteria.select(cliente).where(cb.equal(cliente.get("email"), email));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Cliente> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = cb.createQuery(Cliente.class);
		Root<Cliente> cliente = criteria.from(Cliente.class);
		criteria.select(cliente).orderBy(cb.asc(cliente.get("nome")));
		return em.createQuery(criteria).getResultList();
	}

	public void add(Cliente cliente) throws Exception {
		log.info("Registering " + cliente.getNome());
		em.persist(cliente);
		em.flush();
	}

	public void remove(Cliente c) {
		log.info("Removing "+c.toString());
		em.remove(c);
		em.flush();
	}

	public int removeAll() {
		log.warn("Removing ALL");
		CriteriaDelete<Cliente> deleteQuery = em.getCriteriaBuilder().createCriteriaDelete(Cliente.class);
		deleteQuery.from(Cliente.class);
		int resultado=em.createQuery(deleteQuery).executeUpdate();
		em.flush();
		return resultado;
	}
}
