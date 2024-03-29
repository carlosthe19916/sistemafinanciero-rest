package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CuentaBancariaInteresGenera generated by hbm2java
 */
@Entity
@Table(name = "CUENTA_BANCARIA_INTERES_GENERA", schema = "BDSISTEMAFINANCIERO")
@NamedQueries({ @NamedQuery(name = CuentaBancariaInteresGenera.findByIdAndDate, query = "SELECT c FROM CuentaBancariaInteresGenera c WHERE c.cuentaBancaria.idCuentaBancaria = :idCuentaBancaria AND c.fecha BETWEEN :desde AND :hasta") })
public class CuentaBancariaInteresGenera implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String findByIdAndDate = "CuentaBancariaInteresGenera.findByIdAndDate";

	private BigInteger idCuentaBancariaInteresGen;
	private CuentaBancaria cuentaBancaria;
	private BigDecimal capital;
	private BigDecimal interesGenerado;
	private Date fecha;

	public CuentaBancariaInteresGenera() {
	}

	public CuentaBancariaInteresGenera(BigInteger idCuentaBancariaInteresGen) {
		this.idCuentaBancariaInteresGen = idCuentaBancariaInteresGen;
	}

	public CuentaBancariaInteresGenera(BigInteger idCuentaBancariaInteresGen,
			CuentaBancaria cuentaBancaria, BigDecimal capital,
			BigDecimal interesGenerado, Date fecha) {
		this.idCuentaBancariaInteresGen = idCuentaBancariaInteresGen;
		this.cuentaBancaria = cuentaBancaria;
		this.capital = capital;
		this.interesGenerado = interesGenerado;
		this.fecha = fecha;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ID_CUENTA_BANCARIA_INTERES_GEN", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdCuentaBancariaInteresGen() {
		return this.idCuentaBancariaInteresGen;
	}

	public void setIdCuentaBancariaInteresGen(
			BigInteger idCuentaBancariaInteresGen) {
		this.idCuentaBancariaInteresGen = idCuentaBancariaInteresGen;				
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_BANCARIA")
	public CuentaBancaria getCuentaBancaria() {
		return this.cuentaBancaria;
	}

	public void setCuentaBancaria(CuentaBancaria cuentaBancaria) {
		this.cuentaBancaria = cuentaBancaria;
	}

	@Column(name = "CAPITAL", precision = 18)
	public BigDecimal getCapital() {
		return this.capital;
	}

	public void setCapital(BigDecimal capital) {
		this.capital = capital;
	}

	@Column(name = "INTERES_GENERADO", precision = 18)
	public BigDecimal getInteresGenerado() {
		return this.interesGenerado;
	}

	public void setInteresGenerado(BigDecimal interesGenerado) {
		this.interesGenerado = interesGenerado;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", length = 7)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
