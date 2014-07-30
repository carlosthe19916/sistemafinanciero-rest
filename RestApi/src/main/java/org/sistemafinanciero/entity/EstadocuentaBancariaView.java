package org.sistemafinanciero.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * The persistent class for the ESTADOCUENTA_BANCARIA_VIEW database table.
 * 
 */
@Entity
@Table(name = "ESTADOCUENTA_BANCARIA_VIEW")
@NamedQuery(name = "EstadocuentaBancariaView.findAll", query = "SELECT e FROM EstadocuentaBancariaView e")
@NamedQueries({ @NamedQuery(name = EstadocuentaBancariaView.findByNumeroCuentaAndDesdeHasta, query = "SELECT e FROM EstadocuentaBancariaView e WHERE e.numeroCuenta = :numeroCuenta AND e.fecha BETWEEN :desde AND :hasta") })
public class EstadocuentaBancariaView implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findByNumeroCuentaAndDesdeHasta = "EstadocuentaBancariaView.findByNumeroCuentaAndDesdeHasta";

	@XmlElement
	@Id
	@Column(name = "NUMERO_CUENTA", nullable = false, length = 40, columnDefinition = "nvarchar2")
	private String numeroCuenta;

	@XmlElement
	@Column(name = "SALDO", nullable = false, precision = 18)
	private BigDecimal saldo;

	@Column(name = "ID_TRANSACCION_TRANSFERENCIA")
	private BigDecimal idTransaccionTransferencia;

	@Column(name = "NUMERO_OPERACION")
	private BigInteger numeroOperacion;

	@XmlElement(name = "tipoTransaccion")
	@Column(name = "TIPO_TRANSACCION_TRANSFERENCIA", columnDefinition = "nvarchar2")
	private String tipoTransaccionTransferencia;

	@XmlElement
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", nullable = false, length = 7)
	private Date fecha;

	@XmlElement
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "HORA", nullable = false)
	private Date hora;

	@XmlElement
	@Column(name = "MONTO", nullable = false, precision = 18)
	private BigDecimal monto;

	@XmlElement
	@Column(name = "SALDO_DISPONIBLE", nullable = false, precision = 18)
	private BigDecimal saldoDisponible;

	@XmlElement
	@Column(name = "REFERENCIA", nullable = true, length = 70, columnDefinition = "nvarchar2")
	private String referencia;

	@XmlElement
	@Column(name = "ESTADO", nullable = false, precision = 22, scale = 0)
	private int estado;

	public EstadocuentaBancariaView() {
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

	public BigDecimal getIdTransaccionTransferencia() {
		return this.idTransaccionTransferencia;
	}

	public void setIdTransaccionTransferencia(
			BigDecimal idTransaccionTransferencia) {
		this.idTransaccionTransferencia = idTransaccionTransferencia;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getNumeroCuenta() {
		return this.numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
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

	public String getTipoTransaccionTransferencia() {
		return this.tipoTransaccionTransferencia;
	}

	public void setTipoTransaccionTransferencia(
			String tipoTransaccionTransferencia) {
		this.tipoTransaccionTransferencia = tipoTransaccionTransferencia;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}