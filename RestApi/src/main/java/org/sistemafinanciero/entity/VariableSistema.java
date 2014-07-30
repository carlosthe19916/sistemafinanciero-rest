package org.sistemafinanciero.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the variable_sistema database table.
 * 
 */
@Entity
@Table(name = "VARIABLE_SISTEMA", schema = "BDSISTEMAFINANCIERO")
@NamedQuery(name = "VariableSistema.findAll", query = "SELECT v FROM VariableSistema v")
@NamedQueries({ @NamedQuery(name = VariableSistema.findByDenominacion, query = "SELECT v FROM VariableSistema v WHERE v.denominacion = :denominacion") })
public class VariableSistema implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByDenominacion = "findByDenominacion";

	@Id
	@Column(name = "ID_VARIABLE_SISTEMA", unique = true, nullable = false)
	private int idvariablesistema;

	@Column(nullable = false, length = 30, columnDefinition = "nvarchar2")
	private String denominacion;

	@Column(length = 60, columnDefinition = "nvarchar2")
	private String descripcion;

	@Column(nullable = false, precision = 18, scale = 3)
	private BigDecimal valor;

	public VariableSistema() {
	}

	public Integer getIdvariablesistema() {
		return this.idvariablesistema;
	}

	public void setIdvariablesistema(Integer idvariablesistema) {
		this.idvariablesistema = idvariablesistema;
	}

	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}