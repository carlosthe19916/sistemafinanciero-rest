package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.type.Tipotransaccioncompraventa;

@XmlRootElement(name = "transaccionCompraVentaDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionCompraVentaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Tipotransaccioncompraventa tipoOperacion;
	private BigInteger idMonedaRecibida;
	private BigInteger idMonedaEntregada;
	private BigDecimal montoRecibido;
	private BigDecimal montoEntregado;
	private BigDecimal tasaCambio;
	private String referencia;

	public Tipotransaccioncompraventa getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(Tipotransaccioncompraventa tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public BigInteger getIdMonedaRecibida() {
		return idMonedaRecibida;
	}

	public void setIdMonedaRecibida(BigInteger idMonedaRecibida) {
		this.idMonedaRecibida = idMonedaRecibida;
	}

	public BigInteger getIdMonedaEntregada() {
		return idMonedaEntregada;
	}

	public void setIdMonedaEntregada(BigInteger idMonedaEntregada) {
		this.idMonedaEntregada = idMonedaEntregada;
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

	public BigDecimal getTasaCambio() {
		return tasaCambio;
	}

	public void setTasaCambio(BigDecimal tasaCambio) {
		this.tasaCambio = tasaCambio;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
}
