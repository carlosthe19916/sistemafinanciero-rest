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
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Moneda generated by hbm2java
 */
@Entity
@Table(name = "MONEDA", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "moneda")
public class Moneda implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger idMoneda;
	private String denominacion;
	private String simbolo;
	private int estado;

	private Set transaccionCompraVentasForIdMonedaRecibido = new HashSet(0);
	private Set bovedas = new HashSet(0);
	private Set transaccionCompraVentasForIdMonedaEntregado = new HashSet(0);
	private Set monedaDenominacions = new HashSet(0);
	private Set pendienteCajas = new HashSet(0);
	private Set transaccionCajaCajas = new HashSet(0);

	public Moneda() {
	}

	public Moneda(BigInteger idMoneda, String denominacion, String simbolo, boolean estado) {
		this.idMoneda = idMoneda;
		this.denominacion = denominacion;
		this.simbolo = simbolo;
		this.estado = (estado == true ? 1 : 0);
	}

	public Moneda(BigInteger idMoneda, String denominacion, String simbolo, boolean estado, Set transaccionCompraVentasForIdMonedaRecibido, Set bovedas, Set transaccionCompraVentasForIdMonedaEntregado, Set monedaDenominacions, Set pendienteCajas, Set transaccionCajaCajas) {
		this.idMoneda = idMoneda;
		this.denominacion = denominacion;
		this.simbolo = simbolo;
		this.estado = (estado == true ? 1 : 0);
		this.transaccionCompraVentasForIdMonedaRecibido = transaccionCompraVentasForIdMonedaRecibido;
		this.bovedas = bovedas;
		this.transaccionCompraVentasForIdMonedaEntregado = transaccionCompraVentasForIdMonedaEntregado;
		this.monedaDenominacions = monedaDenominacions;
		this.pendienteCajas = pendienteCajas;
		this.transaccionCajaCajas = transaccionCajaCajas;
	}

	@XmlElement(name = "id")
	@DecimalMin(value = "0", inclusive = false)
	@Id
	@Column(name = "ID_MONEDA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdMoneda() {
		return this.idMoneda;
	}

	public void setIdMoneda(BigInteger idMoneda) {
		this.idMoneda = idMoneda;
	}

	@XmlElement(name = "denominacion")
	@NotNull
	@Size(min = 1, max = 20)
	@NotBlank
	@NotEmpty
	@Column(name = "DENOMINACION", nullable = false, length = 40, columnDefinition = "nvarchar2")
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@XmlElement(name = "simbolo")
	@NotNull
	@Size(min = 1, max = 5)
	@NotBlank
	@NotEmpty
	@Column(name = "SIMBOLO", nullable = false, length = 10, columnDefinition = "nvarchar2")
	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	@XmlTransient
	@NotNull
	@Column(name = "ESTADO", nullable = false, precision = 22, scale = 0)
	public boolean getEstado() {
		return (this.estado == 1 ? true : false);
	}

	public void setEstado(boolean estado) {
		this.estado = (estado == true ? 1 : 0);
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "monedaRecibida")
	public Set<TransaccionCompraVenta> getTransaccionCompraVentasForIdMonedaRecibido() {
		return this.transaccionCompraVentasForIdMonedaRecibido;
	}

	public void setTransaccionCompraVentasForIdMonedaRecibido(Set transaccionCompraVentasForIdMonedaRecibido) {
		this.transaccionCompraVentasForIdMonedaRecibido = transaccionCompraVentasForIdMonedaRecibido;
	}
	
	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moneda")
	public Set<Boveda> getBovedas() {
		return this.bovedas;
	}

	public void setBovedas(Set bovedas) {
		this.bovedas = bovedas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "monedaEntregada")
	public Set<TransaccionCompraVenta> getTransaccionCompraVentasForIdMonedaEntregado() {
		return this.transaccionCompraVentasForIdMonedaEntregado;
	}

	public void setTransaccionCompraVentasForIdMonedaEntregado(Set transaccionCompraVentasForIdMonedaEntregado) {
		this.transaccionCompraVentasForIdMonedaEntregado = transaccionCompraVentasForIdMonedaEntregado;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moneda")
	public Set<MonedaDenominacion> getMonedaDenominacions() {
		return this.monedaDenominacions;
	}

	public void setMonedaDenominacions(Set monedaDenominacions) {
		this.monedaDenominacions = monedaDenominacions;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moneda")
	public Set<PendienteCaja> getPendienteCajas() {
		return this.pendienteCajas;
	}

	public void setPendienteCajas(Set pendienteCajas) {
		this.pendienteCajas = pendienteCajas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "moneda")
	public Set<TransaccionCajaCaja> getTransaccionCajaCajas() {
		return this.transaccionCajaCajas;
	}

	public void setTransaccionCajaCajas(Set transaccionCajaCajas) {
		this.transaccionCajaCajas = transaccionCajaCajas;
	}

	@Override
	public String toString() {
		return this.denominacion;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Moneda)) {
			return false;
		}
		final Moneda other = (Moneda) obj;
		return other.getSimbolo().equalsIgnoreCase(this.simbolo);
	}

	@Override
	public int hashCode() {
		return this.denominacion.hashCode() * this.simbolo.hashCode();
	}
}