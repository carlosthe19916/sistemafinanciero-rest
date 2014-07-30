package org.sistemafinanciero.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * The persistent class for the ESTADOCUENTA_APORTES_VIEW database table.
 * 
 */
@Entity
@Table(name = "ESTADOCUENTA_APORTES_VIEW")
@NamedQuery(name = "EstadocuentaAportesView.findAll", query = "SELECT e FROM EstadocuentaAportesView e")
public class EstadocuentaAportesView implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	@Id
	@Column(name = "NUMERO_CUENTA_APORTE", nullable = false, length = 40, columnDefinition = "nvarchar2")
	private String numeroCuentaAporte;

	@XmlElement
	@Column(name = "SALDO", nullable = false, precision = 18)
	private BigDecimal saldo;

	@Column(name = "ID_TRANSACCION")
	private BigDecimal idTransaccion;

	@Column(name = "NUMERO_OPERACION")
	private BigInteger numeroOperacion;

	@Column(name = "TIPO_TRANSACCION", columnDefinition = "nvarchar2")
	private String tipoTransaccion;

	@XmlElement
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_TRANSACCION", nullable = false, length = 7)
	private Date fecha;

	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HORA_TRANSACCION", nullable = false)
	private Date hora;

	@XmlElement
	@Column(name = "MONTO_TRANSACCON", nullable = false, precision = 18)
	private BigDecimal monto;

	@XmlElement
	@Column(name = "SALDO_DISPONIBLE", nullable = false, precision = 18)
	private BigDecimal saldoDisponible;

	@XmlElement
	@Column(name = "REFERECIA_TRANSACCION", nullable = true, length = 70, columnDefinition = "nvarchar2")
	private String referencia;

	@XmlElement
	@Column(name = "ESTADO_TRANSACCION", nullable = false, precision = 22, scale = 0)
	private int estado;

	public EstadocuentaAportesView() {
	}

	public boolean getEstado() {
		return (this.estado == 1 ? true : false);
	}

	public void setEstado(boolean estado) {
		this.estado = (estado ? 1 : 0);
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return this.hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public BigDecimal getIdTransaccion() {
		return this.idTransaccion;
	}

	public void setIdTransaccion(BigDecimal idTransaccion) {
		this.idTransaccion = idTransaccion;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getNumeroCuentaAporte() {
		return this.numeroCuentaAporte;
	}

	public void setNumeroCuentaAporte(String numeroCuentaAporte) {
		this.numeroCuentaAporte = numeroCuentaAporte;
	}

	public BigInteger getNumeroOperacion() {
		return this.numeroOperacion;
	}

	public void setNumeroOperacion(BigInteger numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	public String getReferencia() {
		return this.referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigDecimal getSaldoDisponible() {
		return this.saldoDisponible;
	}

	public void setSaldoDisponible(BigDecimal saldoDisponible) {
		this.saldoDisponible = saldoDisponible;
	}

	public String getTipoTransaccion() {
		return this.tipoTransaccion;
	}

	public void setTipoTransaccionTransferencia(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}