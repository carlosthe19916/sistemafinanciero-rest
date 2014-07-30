
package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.sistemafinanciero.entity.type.TipoPendiente;

@Entity
@Table(name = "PENDIENTE_CAJA", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "pendientecaja")
@XmlAccessorType(XmlAccessType.NONE)
public class PendienteCaja implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger idPendienteCaja;
	private HistorialCaja historialCaja;
	private Moneda moneda;
	private BigDecimal monto;
	private String observacion;
	private Date fecha;
	private Date hora;
	private TipoPendiente tipoPendiente;
	private String trabajador;

	public PendienteCaja() {
	}

	public PendienteCaja(BigInteger idPendienteCaja,
			HistorialCaja historialCaja, Moneda moneda, BigDecimal monto,
			Date fecha, Date hora, TipoPendiente tipoPendiente) {
		this.idPendienteCaja = idPendienteCaja;
		this.historialCaja = historialCaja;
		this.moneda = moneda;
		this.monto = monto;
		this.fecha = fecha;
		this.hora = hora;
		this.tipoPendiente = tipoPendiente;
	}

	public PendienteCaja(BigInteger idPendienteCaja,
			HistorialCaja historialCaja, Moneda moneda, BigDecimal monto,
			String observacion, Date fecha, Date hora,
			TipoPendiente tipoPendiente) {
		this.idPendienteCaja = idPendienteCaja;
		this.historialCaja = historialCaja;
		this.moneda = moneda;
		this.monto = monto;
		this.observacion = observacion;
		this.fecha = fecha;
		this.hora = hora;
		this.tipoPendiente = tipoPendiente;
	}

	@XmlElement(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "ID_PENDIENTE_CAJA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdPendienteCaja() {
		return this.idPendienteCaja;
	}

	public void setIdPendienteCaja(BigInteger idPendienteCaja) {
		this.idPendienteCaja = idPendienteCaja;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_HISTORIAL_CAJA", nullable = false)
	public HistorialCaja getHistorialCaja() {
		return this.historialCaja;
	}

	public void setHistorialCaja(HistorialCaja historialCaja) {
		this.historialCaja = historialCaja;
	}

	@XmlElement
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MONEDA", nullable = false)
	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	@XmlElement
	@Column(name = "MONTO", nullable = false, precision = 18)
	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	@XmlElement
	@Column(name = "OBSERVACION", length = 40, columnDefinition = "nvarchar2")
	public String getObservacion() {
		return this.observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@XmlElement
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", nullable = false, length = 7)
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@XmlElement
	@Column(name = "HORA", nullable = false)
	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@XmlElement
	@Enumerated(value = EnumType.STRING)
	@Column(name = "TIPO_PENDIENTE", nullable = false, length = 40, columnDefinition = "nvarchar2")
	public TipoPendiente getTipoPendiente() {
		return this.tipoPendiente;
	}

	public void setTipoPendiente(TipoPendiente tipoPendiente) {
		this.tipoPendiente = tipoPendiente;
	}
	
	@XmlElement
	@Column(name = "TRABAJADOR", nullable = false, columnDefinition = "nvarchar2")
	public String getTrabajador() {
		return this.trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}
}

