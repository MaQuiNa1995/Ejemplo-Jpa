package es.maquina1995.hsqldb.repository;

import org.springframework.stereotype.Repository;

import es.maquina1995.hsqldb.dominio.Ninja;

@Repository
public class NinjaRepositoryImpl
		extends CrudRepositoryImpl < Long, Ninja > implements NinjaRepository
{

	public static final String TABLA = "NINJA";

	@Override
	public Class < Ninja > getClassDeT ( )
	{
		return Ninja.class;
	}

	/**
	 * Método usado para hacer el get del nombre de la tabla (Se hace get de una variable public statica para los metodos abstractos del CrudRepository de otra forma no sería posible hacer tan generica la clase)
	 * 
	 * @return {@link java.lang.String} del nombre de la tabla en base de datos que representa el dominio asociado de este repositorio
	 * 
	 */
	@Override
	public String getNombreTabla ( )
	{
		return TABLA;
	}
}