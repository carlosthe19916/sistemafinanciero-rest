package org.sistemafinanciero.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigInteger;
import java.util.Date;

/**
 * The persistent class for the HISTORIAL_TRANSACCION_CAJA database table.
 * 
 */
@Entity
@Table(name = "HISTORIAL_TRANSACCION_CAJA")
@XmlRootElement(name = "moneda")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({ @NamedQuery(name = HistorialTransaccionCaja.findByHistorialCaja, query = "SELECT h FROM HistorialTransaccionCaja h WHERE h.idHistorialCaja = :idHistorialCaja ORDER BY h.numeroOperacion"),
	@NamedQuery(name = HistorialTransaccionCaja.findByTransaccion, query = "select h from HistorialTransaccionCaja h where TRIM(h.idTransaccion) like :filterText or TRIM(h.numeroOperacion) like :filterText order by h.idTransaccion")})
public class HistorialTransaccionCaja implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByHistorialCaja = "HistorialTransaccionCaja.findByHistorialCaja";
	public final static String findByTransaccion = "HistorialTransaccionCaja.findByTransaccion";

	private BigInteger idTransaccion;
	
	private BigInteger idHistorialCaja;

	private BigInteger numeroOperacion;

	private Date fecha;

	private Date hora;

	private String moneda;

	private String monto;

	private String tipoTransaccion;
	
	private String tipoCuenta;

	private int estado;

	public HistorialTransaccionCaja() {
	}
	
	@XmlElement
	@Id
	@Column(name = "ID_TRANSACCION", unique = true, nullable = false)
	public BigInteger getIdTransaccion() {
		return idTransaccion;
	}

	public void setIdTransaccion(BigInteger idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	@XmlElement
	@Column(name = "ESTADO", nullable = false, precision = 22, scale = 0)
	public boolean getEstado() {
		return (this.estado == 1 ? true : false);
	}

	public void setEstado(boolean estado) {
		this.estado = (estado ? 1 : 0);
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HORA", nullable = false)
	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@XmlElement
	@Column(name = "MONEDA", nullable = false, length = 50, columnDefinition = "nvarchar2")
	public String getMoneda() {
		return this.moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	@XmlElement
	@Column(name = "MONTO", nullable = false, length = 60, columnDefinition = "nvarchar2")
	public String getMonto() {
		return this.monto;
	}

	public void setMonto(String monto) {
		this.monto = monto;
	}

	@XmlElement
	@Column(name = "TIPO_TRANSACCION", columnDefinition = "nvarchar2")
	public String getTipoTransaccion() {
		return this.tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	
	@XmlElement
	@Column(name = "TIPO_CUENTA", columnDefinition = "nvarchar2")
	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	@XmlElement
	@Column(name = "ID_HISTORIAL_CAJA")
	public BigInteger getIdHistorialCaja() {
		return idHistorialCaja;
	}

	public void setIdHistorialCaja(BigInteger idHistorialCaja) {
		this.idHistorialCaja = idHistorialCaja;
	}

	@XmlElement
	@Column(name = "NUMERO_OPERACION")
	public BigInteger getNumeroOperacion() {
		return numeroOperacion;
	}

	public void setNumeroOperacion(BigInteger numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}
}