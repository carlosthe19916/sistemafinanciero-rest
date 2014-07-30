package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Moneda;
import org.sistemafinanciero.entity.type.TipoPendiente;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class VoucherPendienteCaja implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private BigInteger idPendienteCaja;
	
	@XmlElement
	private Moneda moneda;
	
	@XmlElement
	private BigDecimal monto;
	
	@XmlElement
	private String observacion;
	
	@XmlElement
	private Date fecha;
	
	@XmlElement
	private Date hora;
	
	@XmlElement
	private TipoPendiente tipoPendiente;
	
	@XmlElement
	private String trabajador;
	
	@XmlElement
	private String agenciaAbreviatura;
	
	@XmlElement
	private String agenciaDenominacion;
	
	@XmlElement
	private String cajaDenominacion;
	
	@XmlElement
	private String cajaAbreviatura;

	public BigInteger getIdPendienteCaja() {
		return idPendienteCaja;
	}

	public void setIdPendienteCaja(BigInteger idPendienteCaja) {
		this.idPendienteCaja = idPendienteCaja;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
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

	public TipoPendiente getTipoPendiente() {
		return tipoPendiente;
	}

	public void setTipoPendiente(TipoPendiente tipoPendiente) {
		this.tipoPendiente = tipoPendiente;
	}

	public String getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
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
}
