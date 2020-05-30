package es.maquina1995.hsqldb.dominio;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

/**
 * Clase que representa el id compuesto de la clase {@link Elementalista}
 * 
 * @author MaQuiNa1995
 *
 */
@Embeddable
public class ElementalistaPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2179897527538033194L;

	private Long poder;
	private String elemento;

	/**
	 * @return the poder
	 */
	public Long getPoder() {
		return poder;
	}

	/**
	 * @param poder the poder to set
	 */
	public void setPoder(Long poder) {
		this.poder = poder;
	}

	/**
	 * @return the elemento
	 */
	public String getElemento() {
		return elemento;
	}

	/**
	 * @param elemento the elemento to set
	 */
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(elemento, poder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ElementalistaPk other = (ElementalistaPk) obj;
		return Objects.equals(elemento, other.elemento) && Objects.equals(poder, other.poder);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ElementalistaPk [poder=");
		builder.append(poder);
		builder.append(", elemento=");
		builder.append(elemento);
		builder.append("]");
		return builder.toString();
	}

}
