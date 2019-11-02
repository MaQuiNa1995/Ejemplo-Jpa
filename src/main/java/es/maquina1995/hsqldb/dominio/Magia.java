package es.maquina1995.hsqldb.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.maquina1995.hsqldb.repository.MagiaRepositoryImpl;
import es.maquina1995.hsqldb.repository.Persistible;

@Entity
@Table(name = MagiaRepositoryImpl.TABLA)
public class Magia implements Persistible<Long> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RANGO_ID")
    private Rango rango;

    @Override
    public Long getId() {
	return id;
    }

    @Override
    public void setId(Long id) {
	this.id = id;
    }
    public Rango getRango() {
        return rango;
    }

    public void setRango(Rango rango) {
        this.rango = rango;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

}