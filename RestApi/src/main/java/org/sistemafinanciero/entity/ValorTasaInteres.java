package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "VALOR_TASA_INTERES", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "valorTasaInteres")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({
		@NamedQuery(name = ValorTasaInteres.finByDenominacionTasaAndIdMoneda, query = "SELECT v FROM ValorTasaInteres v INNER JOIN v.moneda m INNER JOIN v.tasaInteres t WHERE t.denominacion = :tasaInteresDenominacion AND m.idMoneda = :idMoneda "),
		@NamedQuery(name = ValorTasaInteres.finByDenominacionTasaAndIdMonedaPeriodoMonto, query = "SELECT v FROM ValorTasaInteres v INNER JOIN v.moneda m INNER JOIN v.tasaInteres t WHERE t.denominacion = :tasaInteresDenominacion AND m.idMoneda = :idMoneda AND (:periodo BETWEEN v.periodoMinimo AND v.montoMaximo) AND (:monto BETWEEN v.montoMinimo AND v.montoMaximo) ") })
public class ValorTasaInteres implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String finByDenominacionTasaAndIdMoneda = "ValorTasaInteres.finByDenominacionTasaAndIdMoneda";
	public final static String finByDenominacionTasaAndIdMonedaPeriodoMonto = "ValorTasaInteres.finByDenominacionTasaAndIdMonedaPeriodoMonto";

	private BigInteger idValorTasaInteres;
	private BigDecimal valor;
	private BigDecimal montoMinimo;
	private BigDecimal montoMaximo;
	private Integer periodoMinimo;
	private Integer periodoMaximo;
	private Moneda moneda;
	private Servicio servicio;
	private TasaInteres tasaInteres;

	public ValorTasaInteres() {
	}

	@XmlElement(name = "id")
	@Id
	@Column(name = "ID_VALOR_TASA_INTERES", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdValorTasaInteres() {
		return idValorTasaInteres;
	}

	public void setIdValorTasaInteres(BigInteger idValorTasaInteres) {
		this.idValorTasaInteres = idValorTasaInteres;
	}

	@XmlElement
	@Column(name = "VALOR", nullable = false, precision = 5, scale = 4)
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@XmlElement
	@Column(name = "MONTO_MINIMO", nullable = false, precision = 18)
	public BigDecimal getMontoMinimo() {
		return montoMinimo;
	}

	public void setMontoMinimo(BigDecimal montoMinimo) {
		this.montoMinimo = montoMinimo;
	}

	@XmlElement
	@Column(name = "MONTO_MAXIMO", nullable = false, precision = 18)
	public BigDecimal getMontoMaximo() {
		return montoMaximo;
	}

	public void setMontoMaximo(BigDecimal montoMaximo) {
		this.montoMaximo = montoMaximo;
	}

	@XmlElement
	@Column(name = "PERIODO_MINIMO", nullable = false, precision = 22, scale = 0)
	public Integer getPeriodoMinimo() {
		return periodoMinimo;
	}

	public void setPeriodoMinimo(Integer periodoMinimo) {
		this.periodoMinimo = periodoMinimo;
	}

	@XmlElement
	@Column(name = "PERIODO_MAXIMO", nullable = false, precision = 22, scale = 0)
	public Integer getPeriodoMaximo() {
		return periodoMaximo;
	}

	public void setPeriodoMaximo(Integer periodoMaximo) {
		this.periodoMaximo = periodoMaximo;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MONEDA", nullable = false)
	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SERVICIO", nullable = false)
	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TASA_INTERES", nullable = false)
	public TasaInteres getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(TasaInteres tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

}
