package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.entity.type.Tipotransaccionbancaria;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class VoucherTransaccionCuentaAporte implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(name = "id")
	private BigInteger idTransaccion;

	@XmlElement
	private Moneda moneda;

	@XmlElement
	private String numeroCuenta;

	@XmlElement
	private Date fecha;

	@XmlElement
	private Date hora;

	@XmlElement
	private BigInteger numeroOperacion;

	@XmlElement
	private BigDecimal monto;

	@XmlElement
	private String referencia;

	@XmlElement
	private BigDecimal saldoDisponible;

	@XmlElement
	private Tipotransaccionbancaria tipoTransaccion;

	@XmlElement
	private String observacion;

	@XmlElement
	private String agenciaAbreviatura;

	@XmlElement
	private String agenciaDenominacion;

	@XmlElement
	private String cajaDenominacion;

	@XmlElement
	private String cajaAbreviatura;

	@XmlElement
	private BigInteger idSocio;

	@XmlElement
	private TipoDocumento tipoDocumento;

	@XmlElement
	private String numeroDocumento;

	@XmlElement
	private String socio;

	public BigInteger getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(BigInteger idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public BigInteger getNumeroOperacion() {
		return numeroOperacion;
	}

	public void setNumeroOperacion(BigInteger numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getSaldoDisponible() {
		return saldoDisponible;
	}

	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public Tipotransaccionbancaria getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(Tipotransaccionbancaria tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getAgenciaAbreviatura() {
		return agenciaAbreviatura;
	}

	public void setAgenciaAbreviatura(String agenciaAbreviatura) {
		this.agenciaAbreviatura = agenciaAbreviatura;
	}

	public String getAgenciaDenominacion() {
		return agenciaDenominacion;
	}

	public void setAgenciaDenominacion(String agenciaDenominacion) {
		this.agenciaDenominacion = agenciaDenominacion;
	}

	public String getCajaDenominacion() {
		return cajaDenominacion;
	}

	public void setCajaDenominacion(String cajaDenominacion) {
		this.cajaDenominacion = cajaDenominacion;
	}

	public String getCajaAbreviatura() {
		return cajaAbreviatura;
	}

	public void setCajaAbreviatura(String cajaAbreviatura) {
		this.cajaAbreviatura = cajaAbreviatura;
	}

	public BigInteger getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(BigInteger idSocio) {
		this.idSocio = idSocio;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}
}
