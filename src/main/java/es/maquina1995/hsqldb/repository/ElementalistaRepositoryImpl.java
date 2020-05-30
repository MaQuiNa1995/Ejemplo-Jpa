package es.maquina1995.hsqldb.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import es.maquina1995.hsqldb.dominio.Elementalista;
import es.maquina1995.hsqldb.dominio.ElementalistaPk;

@Repository
public class ElementalistaRepositoryImpl implements ElementalistaRepository {

	public static final String ELEMENTALISTA_FIND_ALL = "findAll";

	public static final String TABLA = "ELEMENTALISTA";

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public Elementalista create(Elementalista elementalista) {

		entityManager.persist(elementalista);

		return elementalista;

	}

	@Override
	@Transactional
	public Elementalista findByPk(ElementalistaPk elementalistaPk) {

		return entityManager.find(Elementalista.class, elementalistaPk);

	}

	@Override
	@Transactional
	public List<Elementalista> findAll() {
		TypedQuery<Elementalista> queryElementalistaFindAll = entityManager.createNamedQuery(ELEMENTALISTA_FIND_ALL,
				Elementalista.class);

		return queryElementalistaFindAll.getResultList();

	}

	@Override
	@Transactional
	public Elementalista update(Elementalista elementalista) {
		return entityManager.merge(elementalista);
	}

	@Override
	@Transactional
	public void delete(Elementalista elementalista) {
		entityManager.remove(elementalista);
	}

}