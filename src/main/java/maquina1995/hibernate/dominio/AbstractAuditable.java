package maquina1995.hibernate.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;

import maquina1995.hibernate.audit.AuditManager;

/**
 * Clase creada para que las tablas que queramos auditar puedan extender de ella
 * y guarde información útil sobre ellas, solo serviría para acciones que no
 * sean borrados físicos , ya que con estos se borra toda la entidad de base de
 * datos para llevar un historico completo debemos usar otra manera:
 * <a href="https://www.baeldung.com/database-auditing-jpa">como esta</a>
 * <p>
 * <a href=
 * "https://vladmihalcea.com/how-to-emulate-createdby-and-lastmodifiedby-from-spring-data-using-the-generatortype-hibernate-annotation/">Más
 * info aquí</a>
 * 
 * @author MaQuiNa1995
 *
 */
@MappedSuperclass

public abstract class AbstractAuditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2680594333158867645L;

	/**
	 * Marcamos la columna para que cada vez que se haga
	 * {@link GenerationTime#INSERT} vaya a {@link AuditManager} y meta el usuario
	 * que ha tocado la entidad
	 */
	@Column(name = "CREADO_POR")
	@GeneratorType(type = AuditManager.class, when = GenerationTime.INSERT)
	private String creadoPor;

	/**
	 * Marcamos la columna para que salga el log de la fecha y hora en la que se
	 * creó la entidad esta anotación soporta múltiples tipos de fecha
	 * <p>
	 * <a href=
	 * "https://stackoverflow.com/questions/221611/creation-timestamp-and-last-update-timestamp-with-hibernate-and-mysql#221827">Mas
	 * Info</a>
	 */
	@CreationTimestamp
	@Column(name = "FECHA_CREACION")
	private LocalDateTime fechaCreacion;

	/**
	 * Marcamos la columna para que cada vez que se haga cualquier operación
	 * {@link GenerationTime#ALWAYS} vaya a {@link AuditManager} y meta el usuario
	 * que ha tocado la entidad
	 * 
	 */
	@Column(name = "MODIFICADO_POR")
	@GeneratorType(type = AuditManager.class, when = GenerationTime.ALWAYS)
	private String modificadoPor;

	/**
	 * Marcamos la columna para que salga el log de la fecha y hora en la que se
	 * modificó la entidad esta anotación soporta múltiples tipos de fecha
	 * <p>
	 * <a href=
	 * "https://stackoverflow.com/questions/221611/creation-timestamp-and-last-update-timestamp-with-hibernate-and-mysql#221827">Mas
	 * Info</a>
	 */
	@UpdateTimestamp
	@Column(name = "FECHA_MODIFICACION")
	private LocalDateTime fechaModificacion;

}