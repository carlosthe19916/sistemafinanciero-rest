package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * HistorialCaja generated by hbm2java
 */
@Entity
@Table(name = "HISTORIAL_CAJA", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "historialcaja")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({ @NamedQuery(name = HistorialCaja.findByHistorialActivo, query = "SELECT c FROM HistorialCaja c WHERE c.caja.idCaja = :idcaja AND c.estado = true"), @NamedQuery(name = HistorialCaja.findByHistorialDateRange, query = "SELECT h FROM HistorialCaja h WHERE h.caja.idCaja = :idcaja AND h.horaApertura BETWEEN :desde AND :hasta AND h.estado = false ORDER BY h.horaApertura DESC"), @NamedQuery(name = HistorialCaja.findByHistorialDateRangePenultimo, query = "SELECT h FROM HistorialCaja h WHERE h.caja.idCaja = :idcaja AND h.fechaApertura <= :fecha ORDER BY h.horaApertura DESC") })
public class HistorialCaja implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String findByHistorialActivo = "findByHistorialActivo";
	public final static String findByHistorialDateRange = "findByHistorialDateRange";
	public final static String findByHistorialDateRangePenultimo = "findByHistorialDateRangePenultimo";

	private BigInteger idHistorialCaja;
	private Caja caja;
	private Date fechaApertura;
	private Date fechaCierre;
	private Date horaApertura;
	private Date horaCierre;
	private String trabajador;
	private int estado;
	private Set transaccionCajaCajasForIdCajaHistorialDestino = new HashSet(0);
	private Set transaccionCuentaAportes = new HashSet(0);
	private Set transaccionCompraVentas = new HashSet(0);
	private Set pendienteCajas = new HashSet(0);
	private Set transaccionCajaCajasForIdCajaHistorialOrigen = new HashSet(0);
	private Set transaccionBovedaCajas = new HashSet(0);
	private Set transaccionBancarias = new HashSet(0);
	private Set detalleHistorialCajas = new HashSet(0);
	private Set transferenciaBancarias = new HashSet(0);

	public HistorialCaja() {
	}

	public HistorialCaja(BigInteger idHistorialCaja, Caja caja, Date fechaApertura, Date horaApertura, boolean estado) {
		this.idHistorialCaja = idHistorialCaja;
		this.caja = caja;
		this.fechaApertura = fechaApertura;
		this.horaApertura = horaApertura;
		this.estado = (estado ? 1 : 0);
	}

	public HistorialCaja(BigInteger idHistorialCaja, Caja caja, Date fechaApertura, Date fechaCierre, Date horaApertura, Date horaCierre, boolean estado, Set transaccionCajaCajasForIdCajaHistorialDestino, Set transaccionCuentaAportes, Set transaccionCompraVentas, Set pendienteCajas, Set transaccionCajaCajasForIdCajaHistorialOrigen, Set transaccionBovedaCajas, Set transaccionBancarias, Set detalleHistorialCajas, Set transferenciaBancarias) {
		this.idHistorialCaja = idHistorialCaja;
		this.caja = caja;
		this.fechaApertura = fechaApertura;
		this.fechaCierre = fechaCierre;
		this.horaApertura = horaApertura;
		this.horaCierre = horaCierre;
		this.estado = (estado ? 1 : 0);
		this.transaccionCajaCajasForIdCajaHistorialDestino = transaccionCajaCajasForIdCajaHistorialDestino;
		this.transaccionCuentaAportes = transaccionCuentaAportes;
		this.transaccionCompraVentas = transaccionCompraVentas;
		this.pendienteCajas = pendienteCajas;
		this.transaccionCajaCajasForIdCajaHistorialOrigen = transaccionCajaCajasForIdCajaHistorialOrigen;
		this.transaccionBovedaCajas = transaccionBovedaCajas;
		this.transaccionBancarias = transaccionBancarias;
		this.detalleHistorialCajas = detalleHistorialCajas;
		this.transferenciaBancarias = transferenciaBancarias;
	}

	@XmlElement(name = "id")
	@DecimalMin(value = "1", inclusive = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "ID_HISTORIAL_CAJA", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdHistorialCaja() {
		return this.idHistorialCaja;
	}

	public void setIdHistorialCaja(BigInteger idHistorialCaja) {
		this.idHistorialCaja = idHistorialCaja;
	}

	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CAJA", nullable = false)
	public Caja getCaja() {
		return this.caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	@XmlElement(name = "fechaApertura")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_APERTURA", nullable = false, length = 7)
	public Date getFechaApertura() {
		return this.fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	@XmlElement(name = "fechaCierre")
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_CIERRE", length = 7)
	public Date getFechaCierre() {
		return this.fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	@XmlElement(name = "horaApertura")
	@NotNull
	@Column(name = "HORA_APERTURA", nullable = false)
	public Date getHoraApertura() {
		return this.horaApertura;
	}

	public void setHoraApertura(Date horaApertura) {
		this.horaApertura = horaApertura;
	}

	@XmlElement(name = "horaCierre")
	@NotNull
	@Column(name = "HORA_CIERRE")
	public Date getHoraCierre() {
		return this.horaCierre;
	}

	public void setHoraCierre(Date horaCierre) {
		this.horaCierre = horaCierre;
	}

	@XmlElement(name = "trabajador")
	@NotNull
	@Size(min = 1, max = 100)
	@NotBlank
	@NotEmpty
	@Column(name = "TRABAJADOR", nullable = false, columnDefinition = "nvarchar2")
	public String getTrabajador() {
		return this.trabajador;
	}

	public void setTrabajador(String trabajador) {
		this.trabajador = trabajador;
	}

	@XmlTransient
	@NotNull
	@Column(name = "ESTADO", nullable = false, precision = 22, scale = 0)
	public boolean getEstado() {
		return (this.estado == 1 ? true : false);
	}

	public void setEstado(boolean estado) {
		this.estado = (estado ? 1 : 0);
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCajaByIdCajaHistorialDestino")
	public Set<TransaccionCajaCaja> getTransaccionCajaCajasForIdCajaHistorialDestino() {
		return this.transaccionCajaCajasForIdCajaHistorialDestino;
	}

	public void setTransaccionCajaCajasForIdCajaHistorialDestino(Set transaccionCajaCajasForIdCajaHistorialDestino) {
		this.transaccionCajaCajasForIdCajaHistorialDestino = transaccionCajaCajasForIdCajaHistorialDestino;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionCuentaAporte> getTransaccionCuentaAportes() {
		return this.transaccionCuentaAportes;
	}

	public void setTransaccionCuentaAportes(Set transaccionCuentaAportes) {
		this.transaccionCuentaAportes = transaccionCuentaAportes;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionCompraVenta> getTransaccionCompraVentas() {
		return this.transaccionCompraVentas;
	}

	public void setTransaccionCompraVentas(Set transaccionCompraVentas) {
		this.transaccionCompraVentas = transaccionCompraVentas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<PendienteCaja> getPendienteCajas() {
		return this.pendienteCajas;
	}

	public void setPendienteCajas(Set pendienteCajas) {
		this.pendienteCajas = pendienteCajas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCajaByIdCajaHistorialOrigen")
	public Set<TransaccionCajaCaja> getTransaccionCajaCajasForIdCajaHistorialOrigen() {
		return this.transaccionCajaCajasForIdCajaHistorialOrigen;
	}

	public void setTransaccionCajaCajasForIdCajaHistorialOrigen(Set transaccionCajaCajasForIdCajaHistorialOrigen) {
		this.transaccionCajaCajasForIdCajaHistorialOrigen = transaccionCajaCajasForIdCajaHistorialOrigen;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionBovedaCaja> getTransaccionBovedaCajas() {
		return this.transaccionBovedaCajas;
	}

	public void setTransaccionBovedaCajas(Set transaccionBovedaCajas) {
		this.transaccionBovedaCajas = transaccionBovedaCajas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransaccionBancaria> getTransaccionBancarias() {
		return this.transaccionBancarias;
	}

	public void setTransaccionBancarias(Set transaccionBancarias) {
		this.transaccionBancarias = transaccionBancarias;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<DetalleHistorialCaja> getDetalleHistorialCajas() {
		return this.detalleHistorialCajas;
	}

	public void setDetalleHistorialCajas(Set detalleHistorialCajas) {
		this.detalleHistorialCajas = detalleHistorialCajas;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "historialCaja")
	public Set<TransferenciaBancaria> getTransferenciaBancarias() {
		return this.transferenciaBancarias;
	}

	public void setTransferenciaBancarias(Set transferenciaBancarias) {
		this.transferenciaBancarias = transferenciaBancarias;
	}

	@Override
	public String toString() {
		return "[Apertura:" + this.fechaApertura + "horaApertura:" + this.horaApertura + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof HistorialCaja)) {
			return false;
		}
		final HistorialCaja other = (HistorialCaja) obj;
		return (other.fechaApertura.equals(this.fechaApertura) && other.horaApertura.equals(this.horaApertura));
	}

	@Override
	public int hashCode() {
		return this.fechaApertura.hashCode() * this.horaApertura.hashCode();
	}

}
