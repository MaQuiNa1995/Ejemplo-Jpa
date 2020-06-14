package es.maquina1995.hsqldb.dominio.one2one;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import es.maquina1995.hsqldb.dominio.AbstractEntidadSimple;

/**
 * Clase que extiende de {@link AbstractEntidadSimple} para obtener sus
 * atributos si queremos sobreescribir el nombre de algun campo de esta clase
 * debemos usar el {@link AttributeOverride}
 * 
 * @author MaQuiNa1995
 *
 */
@Entity
@Table(name = "NINJUTSU")
@AttributeOverride(name = "id", column = @Column(name = "ID_NINJUTSU"))
public class Ninjutsu extends AbstractEntidadSimple<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8892081097245982860L;

}
