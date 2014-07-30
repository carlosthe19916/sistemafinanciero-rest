package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Moneda generated by hbm2java
 */
@Entity
@Table(name = "TASA_INTERES", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "tipoServicio")
@XmlAccessorType(XmlAccessType.NONE)
public class TasaInteres implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigInteger idTasaInteres;
	private String denominacion;
	private String abreviatura;
	private String descripcion;
	private Set valorTasasInteres = new HashSet(0);

	public TasaInteres() {
	}	

	@XmlElement(name="id")
	@Id
	@Column(name = "ID_TASA_INTERES", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdTasaInteres() {
		return this.idTasaInteres;
	}

	public void setIdTasaInteres(BigInteger idTasaInteres) {
		this.idTasaInteres = idTasaInteres;
	}

	@XmlElement
	@Column(name = "DENOMINACION", length = 50, columnDefinition = "nvarchar2")
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
	
	@XmlElement
	@Column(name = "ABREVIATURA", length = 20, columnDefinition = "nvarchar2")
	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@XmlElement
	@Column(name = "DESCRIPCION", length = 70, columnDefinition = "nvarchar2")
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tasaInteres")
	public Set<ValorTasaInteres> getValorTasasInteres() {
		return this.valorTasasInteres;
	}

	public void setValorTasasInteres(Set valorTasasInteres) {
		this.valorTasasInteres = valorTasasInteres;
	}
	
}