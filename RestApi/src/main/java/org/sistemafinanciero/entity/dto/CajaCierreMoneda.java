package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Moneda;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class CajaCierreMoneda implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private Moneda moneda;

	@XmlElement
	private String agencia;

	@XmlElement
	private String caja;

	@XmlElement
	private Date fechaApertura;

	@XmlElement
	private Date fechaCierre;

	@XmlElement
	private Date horaApertura;

	@XmlElement
	private Date horaCierre;

	@XmlElement
	private String trabajador;

	@XmlElement
	private Set<GenericDetalle> detalle;

	@XmlElement
	private BigDecimal saldoAyer;

	@XmlElement
	private BigDecimal entradas;

	@XmlElement
	private BigDecimal salidas;

	@XmlElement
	private BigDecimal sobrante;

	@XmlElement
	private BigDecimal faltante;

	@XmlElement
	private BigDecimal porDevolver;

	public CajaCierreMoneda() {
		// TODO Auto-generated constructor stub
	}
	
	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Date getHoraApertura() {
		return horaApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}

	public Date getHoraCierre() {
		return horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}

	public String getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}

	public Set<GenericDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(Set<GenericDetalle> detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getSaldoAyer() {
		return saldoAyer;
	}

	public void setSaldoAyer(BigDecimal saldoAyer) {
		this.saldoAyer = saldoAyer;
	}

	public BigDecimal getEntradas() {
		return entradas;
	}

	public void setEntradas(BigDecimal entradas) {
		this.entradas = entradas;
	}

	public BigDecimal getSalidas() {
		return salidas;
	}

	public void setSalidas(BigDecimal salidas) {
		this.salidas = salidas;
	}

	public BigDecimal getSobrante() {
		return sobrante;
	}

	public void setSobrante(BigDecimal sobrante) {
		this.sobrante = sobrante;
	}

	public BigDecimal getFaltante() {
		return faltante;
	}

	public void setFaltante(BigDecimal faltante) {
		this.faltante = faltante;
	}

	public BigDecimal getPorDevolver() {
		return porDevolver;
	}

	public void setPorDevolver(BigDecimal porDevolver) {
		this.porDevolver = porDevolver;
	}
}
