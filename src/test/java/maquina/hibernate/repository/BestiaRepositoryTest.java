package maquina.hibernate.repository;

import org.springframework.beans.factory.annotation.Autowired;

import maquina.hibernate.dominio.many2many.Bestia;
import maquina.hibernate.repository.many2many.BestiaRepository;

public class BestiaRepositoryTest extends JpaRepositoryImplTest<Long, Bestia> {

	@Autowired
	private BestiaRepository cut;

	@Override
	public CustomGenericRepository<Long, Bestia> getRepository() {
		return cut;
	}

	@Override
	public Bestia getInstanceDeT() {
		Bestia bestia = new Bestia();
		bestia.setNombre("Gran Molbo");

		return bestia;
	}

	@Override
	public boolean sonDatosIguales(Bestia bestia, Bestia bestia2) {
		return bestia.equals(bestia2);
	}

	@Override
	public Long getClavePrimariaNoExistente() {
		return Long.MAX_VALUE;
	}

}
