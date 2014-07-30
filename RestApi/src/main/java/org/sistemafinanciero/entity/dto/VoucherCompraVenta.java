package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.type.Tipotransaccioncompraventa;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class VoucherCompraVenta implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "id")
	private BigInteger idCompraVenta;

	@XmlElement
	private Date fecha;

	@XmlElement
	private Date hora;

	@XmlElement
	private BigInteger numeroOperacion;

	@XmlElement
	private BigDecimal montoRecibido;

	@XmlElement
	private BigDecimal montoEntregado;

	@XmlElement
	private Moneda monedaRecibida;

	@XmlElement
	private Moneda monedaEntregada;

	@XmlElement
	private BigDecimal tipoCambio;
	
	@XmlElement
	private Tipotransaccioncompraventa tipoTransaccion;

	@XmlElement
	private String referencia;

	@XmlElement
	private boolean estado;

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
	

	public BigInteger getIdCompraVenta() {
		return idCompraVenta;
	}

	public void setIdCompraVenta(BigInteger idCompraVenta) {
		this.idCompraVenta = idCompraVenta;
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

	public BigDecimal getMontoRecibido() {
		return montoRecibido;
	}

	public void setMontoRecibido(BigDecimal montoRecibido) {
		this.montoRecibido = montoRecibido;
	}

	public BigDecimal getMontoEntregado() {
		return montoEntregado;
	}

	public void setMontoEntregado(BigDecimal montoEntregado) {
		this.montoEntregado = montoEntregado;
	}

	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
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

	public Moneda getMonedaRecibida() {
		return monedaRecibida;
	}

	public void setMonedaRecibida(Moneda monedaRecibida) {
		this.monedaRecibida = monedaRecibida;
	}

	public Moneda getMonedaEntregada() {
		return monedaEntregada;
	}

	public void setMonedaEntregada(Moneda monedaEntregada) {
		this.monedaEntregada = monedaEntregada;
	}

	public Tipotransaccioncompraventa getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(Tipotransaccioncompraventa tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

}
