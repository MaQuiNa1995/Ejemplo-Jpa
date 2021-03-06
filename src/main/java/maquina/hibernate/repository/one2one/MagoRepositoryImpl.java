package maquina.hibernate.repository.one2one;

import org.springframework.stereotype.Repository;

import maquina.hibernate.dominio.one2one.Mago;
import maquina.hibernate.repository.CustomGenericRepositoryImpl;

@Repository
public class MagoRepositoryImpl extends CustomGenericRepositoryImpl<Long, Mago> implements MagoRepository {

	@Override
	public Class<Mago> getClassDeT() {
		return Mago.class;
	}
}