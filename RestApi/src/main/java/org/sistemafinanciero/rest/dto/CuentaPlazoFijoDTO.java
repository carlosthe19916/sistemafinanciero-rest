package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Beneficiario;
import org.sistemafinanciero.entity.type.TipoPersona;

@XmlRootElement(name = "crearCuentaPlazoFijo")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class CuentaPlazoFijoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger idMoneda;
	private int periodo;
	private BigDecimal monto;
	private BigDecimal tasaInteres;

	private TipoPersona tipoPersona;
	private BigInteger idTipoDocumento;
	private String numeroDocumento;

	private int cantRetirantes;
	private List<BigInteger> titulares;
	private List<Beneficiario> beneficiarios;

	public BigInteger getIdMoneda() {
		return idMoneda;
	}

	public void setIdMoneda(BigInteger idMoneda) {
		this.idMoneda = idMoneda;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public BigDecimal getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(BigDecimal tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public BigInteger getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(BigInteger idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public int getCantRetirantes() {
		return cantRetirantes;
	}

	public void setCantRetirantes(int cantRetirantes) {
		this.cantRetirantes = cantRetirantes;
	}

	public List<BigInteger> getTitulares() {
		return titulares;
	}

	public void setTitulares(List<BigInteger> titulares) {
		this.titulares = titulares;
	}

	public List<Beneficiario> getBeneficiarios() {
		return beneficiarios;
	}

	public void setBeneficiarios(List<Beneficiario> beneficiarios) {
		this.beneficiarios = beneficiarios;
	}

}
