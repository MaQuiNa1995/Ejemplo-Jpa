package maquina1995.hibernate.repository;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import maquina1995.hibernate.configuration.ConfigurationSpring;
import maquina1995.hibernate.constants.ConstantesTesting;
import maquina1995.hibernate.dominio.AbstractEntidadSimple;
import maquina1995.hibernate.repository.CrudRepository;

/**
 * Clase de test genérica para hacer test al vuelo de método tipicos de un CRUD
 * <p>
 * Lecciones Aprendidas:
 * <p>
 * si no pones el {@link TransactionalTestExecutionListener} spring no puede
 * crear el transactionManager en test con este error me tiré 1 tarde entera
 * <p>
 * Me daba un error de:
 * <p>
 * javax.persistence.TransactionRequiredException: No EntityManager with actual
 * transaction available for current thread - cannot reliably process 'persist'
 * call
 * <p>
 * <a href=
 * "https://github.com/MaQuiNa1995/Ejemplo-HSQL/commit/2cb42961e4834f20a3b772cd64a3cee5547f6326">Este
 * es el commit</a> donde arreglé el error
 * 
 * @author MaQuiNa1995
 *
 * @param <K> Genérico que representa un objeto que sea hijo de
 *            {@link java.lang.Number}
 * @param <T> Genérico que representa un objeto que esté anotado con
 *            {@link javax.persistence.Entity}
 */
@Rollback
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { ConfigurationSpring.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
public abstract class CrudRepositoryImplTest<K, T extends AbstractEntidadSimple<K>> {

	@PersistenceContext
	protected EntityManager entityManager;

	protected abstract CrudRepository<K, T> getRepository();

	protected abstract T getInstanceDeT();

	protected abstract boolean sonDatosIguales(T objeto1, T objeto2);

	protected abstract K getClavePrimariaNoExistente();

	protected T getInstanceDeTParaModificar(K id) {
		T objetoModificar = this.getInstanceDeT();
		objetoModificar.setId(id);
		objetoModificar.setNombre(ConstantesTesting.CADENA_TEXTO);

		return objetoModificar;

	}

	@Test
	@Transactional
	public void addTest() {
		T instancia = this.getInstanceDeT();
		Assertions.assertNull(instancia.getId());
		instancia = this.getRepository()
				.persist(instancia);
		Assertions.assertNotNull(instancia.getId());
	}

	@Test
	@Transactional
	public void readTest() {
		K clavePrimaria = this.generaDatoLectura();

		T resultado = this.getRepository()
				.readByPk(clavePrimaria);

		Assertions.assertNotNull(resultado);
		Assertions.assertTrue(this.sonDatosIguales(this.getInstanceDeT(), resultado));
	}

	@Test
	@Transactional
	public void readByNaturalIdTest() {
		UUID idNatural = this.generaDatoLecturaNaturalId();

		T resultado = this.getRepository()
				.readByNaturalId(idNatural);

		Assertions.assertNotNull(resultado);
		Assertions.assertTrue(this.sonDatosIguales(this.getInstanceDeT(), resultado));
	}

	@Test
	@Transactional(readOnly = true)
	public void readNoExisteTest() {
		K clavePrimaria = this.getClavePrimariaNoExistente();

		Assertions.assertNull(this.getRepository()
				.readByPk(clavePrimaria));

	}

	@Test
	@Transactional
	public void findAllTest() {

		Assertions.assertTrue(this.getRepository()
				.findAll()
				.isEmpty());

		for (int i = 0; i < 3; i++) {
			this.generaDatoLectura();
		}

		List<T> resultado = this.getRepository()
				.findAll();

		Assertions.assertEquals(3, resultado.size());
	}

	@Test
	@Transactional
	public void updateTest() {
		K clavePrimaria = this.generaDatoLectura();

		T objetoUpdate = this.getInstanceDeTParaModificar(clavePrimaria);

		this.getRepository()
				.merge(objetoUpdate);

		T enBBDD = this.entityManager.find(getRepository().getClassDeT(), clavePrimaria);

		Assertions.assertTrue(this.sonDatosIguales(this.getInstanceDeTParaModificar(clavePrimaria), enBBDD));
	}

	@Test
	@Transactional
	public void deleteTest() {
		K clavePrimaria = this.generaDatoLectura();

		Assertions.assertFalse(this.getRepository()
				.findAll()
				.isEmpty());

		this.getRepository()
				.deleteByPk(clavePrimaria);

		AbstractEntidadSimple<K> objetoBd = this.entityManager.find(getRepository().getClassDeT(), clavePrimaria);

		Assertions.assertNull(objetoBd);
	}

	/**
	 * Se hace {@link EntityManager#flush()} para que cree los campos de
	 * {@link maquina1995.hibernate.dominio.AbstractAuditable}
	 * 
	 * @return
	 */
	@Transactional
	private K generaDatoLectura() {

		T instancia = getInstanceDeT();
		this.entityManager.persist(instancia);
		this.entityManager.flush();

		return instancia.getId();
	}

	@Transactional
	private UUID generaDatoLecturaNaturalId() {
		T instancia = this.getInstanceDeT();
		entityManager.persist(instancia);
		return instancia.getReferencia();
	}

}