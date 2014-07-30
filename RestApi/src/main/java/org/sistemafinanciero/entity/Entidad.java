package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad generated by hbm2java
 */
@Entity
@Table(name = "ENTIDAD", schema = "BDSISTEMAFINANCIERO")
public class Entidad implements java.io.Serializable {

	private BigDecimal idEntidad;
	private String denominacion;
	private String abreviatura;
	private BigDecimal estado;
	private Set transaccionBovedaOtros = new HashSet(0);

	public Entidad() {
	}

	public Entidad(BigDecimal idEntidad, String denominacion,
			String abreviatura, BigDecimal estado) {
		this.idEntidad = idEntidad;
		this.denominacion = denominacion;
		this.abreviatura = abreviatura;
		this.estado = estado;
	}

	public Entidad(BigDecimal idEntidad, String denominacion,
			String abreviatura, BigDecimal estado, Set transaccionBovedaOtros) {
		this.idEntidad = idEntidad;
		this.denominacion = denominacion;
		this.abreviatura = abreviatura;
		this.estado = estado;
		this.transaccionBovedaOtros = transaccionBovedaOtros;
	}

	@Id
	@Column(name = "ID_ENTIDAD", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getIdEntidad() {
		return this.idEntidad;
	}

	public void setIdEntidad(BigDecimal idEntidad) {
		this.idEntidad = idEntidad;
	}

	@Column(name = "DENOMINACION", nullable = false, length = 100,columnDefinition = "nvarchar2")
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@Column(name = "ABREVIATURA", nullable = false, length = 20,columnDefinition = "nvarchar2")
	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@Column(name = "ESTADO", nullable = false, precision = 22, scale = 0)
	public BigDecimal getEstado() {
		return this.estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "entidad")
	public Set<TransaccionBovedaOtro> getTransaccionBovedaOtros() {
		return this.transaccionBovedaOtros;
	}

	public void setTransaccionBovedaOtros(Set transaccionBovedaOtros) {
		this.transaccionBovedaOtros = transaccionBovedaOtros;
	}

}