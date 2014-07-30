package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaccionBancariaDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionCuentaAporteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger idSocio;
	private BigDecimal monto;
	private int mes;
	private int anio;
	private String referencia;

	public BigInteger getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(BigInteger idSocio) {
		this.idSocio = idSocio;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

}
