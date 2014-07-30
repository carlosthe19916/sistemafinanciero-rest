package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Agencia generated by hbm2java
 */
@Entity
@Table(name = "AGENCIA", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "agencia")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({ @NamedQuery(name = Agencia.findByCodigo, query = "SELECT a FROM Agencia a WHERE a.codigo = :codigo") })
public class Agencia implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByCodigo = "Agencia.findByCodigo";

	private BigInteger idAgencia;
	private String codigo;
	private String denominacion;
	private String abreviatura;
	private String ubigeo;
	private int estado;
	private Sucursal sucursal;

	private Set bovedas = new HashSet(0);
	private Set trabajadores = new HashSet(0);

	public Agencia() {
	}

	public Agencia(BigInteger idAgencia, Sucursal sucursal, String denominacion, boolean estado, String abreviatura, String ubigeo) {
		this.idAgencia = idAgencia;
		this.sucursal = sucursal;
		this.denominacion = denominacion;
		this.estado = (estado ? 1 : 0);
		this.abreviatura = abreviatura;
		this.ubigeo = ubigeo;
	}

	public Agencia(BigInteger idAgencia, Sucursal sucursal, String denominacion, boolean estado, String abreviatura, String ubigeo, Set bovedas, Set trabajadors) {
		this.idAgencia = idAgencia;
		this.sucursal = sucursal;
		this.denominacion = denominacion;
		this.estado = (estado ? 1 : 0);
		this.abreviatura = abreviatura;
		this.ubigeo = ubigeo;
		this.bovedas = bovedas;
		this.trabajadores = trabajadors;
	}

	@XmlElement(name = "id")
	@DecimalMin(value = "0", inclusive = false)
	@Id
	@Column(name = "ID_AGENCIA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdAgencia() {
		return this.idAgencia;
	}

	public void setIdAgencia(BigInteger idAgencia) {
		this.idAgencia = idAgencia;
	}

	@XmlElement(name = "codigo")
	@NotNull
	@Size(min = 1, max = 3)
	@NotEmpty
	@NotBlank
	@Column(name = "CODIGO", nullable = false, length = 3, columnDefinition = "nvarchar2")
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@XmlElement(name = "denominacion")
	@NotNull
	@Size(min = 1, max = 100)
	@NotEmpty
	@NotBlank
	@Column(name = "DENOMINACION", nullable = false, length = 100, columnDefinition = "nvarchar2")
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@XmlElement(name = "abreviatura")
	@NotNull
	@Size(min = 1, max = 20)
	@NotEmpty
	@NotBlank
	@Column(name = "ABREVIATURA", nullable = false, length = 20, columnDefinition = "nvarchar2")
	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@XmlElement(name = "ubigeo")
	@NotNull
	@Size(min = 1, max = 6)
	@NotEmpty
	@NotBlank
	@Column(name = "UBIGEO", nullable = false, length = 12, columnDefinition = "nvarchar2")
	public String getUbigeo() {
		return this.ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	@XmlElement(name = "estado")
	@NotNull
	@Min(value = 0)
	@Max(value = 1)
	@Column(name = "ESTADO", nullable = false, precision = 22, scale = 0)
	public boolean getEstado() {
		return (this.estado == 1 ? true : false);
	}

	public void setEstado(boolean estado) {
		this.estado = (estado ? 1 : 0);
	}

	@XmlTransient
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SUCURSAL", nullable = false)
	public Sucursal getSucursal() {
		return this.sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agencia")
	public Set<Boveda> getBovedas() {
		return this.bovedas;
	}

	public void setBovedas(Set bovedas) {
		this.bovedas = bovedas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "agencia")
	public Set<Trabajador> getTrabajadores() {
		return this.trabajadores;
	}

	public void setTrabajadores(Set trabajadores) {
		this.trabajadores = trabajadores;
	}

}
