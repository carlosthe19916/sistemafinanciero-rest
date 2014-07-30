package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TrabajadorCaja generated by hbm2java
 */
@Entity
@Table(name = "TRABAJADOR_CAJA", schema = "BDSISTEMAFINANCIERO")
public class TrabajadorCaja implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TrabajadorCajaId id;
	private Caja caja;
	private Trabajador trabajador;

	public TrabajadorCaja() {
	}

	public TrabajadorCaja(TrabajadorCajaId id, Caja caja, Trabajador trabajador) {
		this.id = id;
		this.caja = caja;
		this.trabajador = trabajador;
	}

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "idTrabajador", column = @Column(name = "ID_TRABAJADOR", nullable = false, precision = 22, scale = 0)), @AttributeOverride(name = "idCaja", column = @Column(name = "ID_CAJA", nullable = false, precision = 22, scale = 0)) })
	public TrabajadorCajaId getId() {
		return this.id;
	}

	public void setId(TrabajadorCajaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CAJA", nullable = false, insertable = false, updatable = false)
	public Caja getCaja() {
		return this.caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TRABAJADOR", nullable = false, insertable = false, updatable = false)
	public Trabajador getTrabajador() {
		return this.trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

}
