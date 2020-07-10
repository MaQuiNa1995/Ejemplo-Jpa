package es.maquina1995.hsqldb.repository;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import es.maquina1995.hsqldb.dominio.AbstractEntidadSimple;

public abstract class CrudRepositoryImpl<K, T extends AbstractEntidadSimple<K>> implements CrudRepository<K, T> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional
	public T persist(T nuevo) {
		entityManager.persist(nuevo);
		return nuevo;
	}

	/**
	 * Si hemos anotado nuestra entidad con 1
	 * {@link org.hibernate.annotations.NaturalId} debemos usar
	 * {@link Session #bySimpleNaturalId(Class)}
	 * <p>
	 * En caso contrario si anotaste tu entidad con mas de 1 debes usar:
	 * <code> entityManager.unwrap(Session.class).byNaturalId(getClassDeT()).using("nombreCampoNaturalId", id).load() </code>
	 * 
	 * @param id el Id Natural de la entidad
	 * 
	 * @return T entidad devuelta
	 */
	@Override
	@Transactional(readOnly = true)
	public T readByNaturalId(UUID id) {

		return entityManager.unwrap(Session.class)
				.bySimpleNaturalId(this.getClassDeT())
				.load(id);
	}

	@Override
	@Transactional(readOnly = true)
	public T readByPk(K id) {
		return entityManager.find(getClassDeT(), id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {

		String query = String.format("Select entity FROM %s entity", getClassDeT().getName());

		return entityManager.createQuery(query, getClassDeT())
				.getResultList();
	}

	@Override
	@Transactional
	public void deleteByPk(K id) {
		T entidadBorrar = entityManager.getReference(getClassDeT(), id);
		this.delete(entidadBorrar);
	}

	@Override
	@Transactional
	public void delete(T entidadBorrar) {
		entityManager.remove(entidadBorrar);
	}

	@Override
	@Transactional
	public T merge(T entidadModificar) {
		return entityManager.merge(entidadModificar);
	}
}
