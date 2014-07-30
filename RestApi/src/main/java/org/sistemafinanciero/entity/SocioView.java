package org.sistemafinanciero.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.type.EstadoCuentaAporte;
import org.sistemafinanciero.entity.type.TipoPersona;

/**
 * The persistent class for the SOCIO_VIEW database table.
 * 
 */
@Entity
@Table(name = "SOCIO_VIEW", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "socioview")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({
		@NamedQuery(name = SocioView.findAll, query = "SELECT sv FROM SocioView sv WHERE sv.estado IN :modeEstado ORDER BY sv.socio, sv.idsocio ASC"),
		@NamedQuery(name = SocioView.FindAllHaveCuentaAporte, query = "SELECT sv FROM SocioView sv WHERE sv.cuentaAporte IS NOT NULL AND sv.estado IN :modeEstado ORDER BY sv.socio, sv.idsocio ASC"),
		@NamedQuery(name = SocioView.FindByFilterTextSocioView, query = "SELECT sv FROM SocioView sv WHERE sv.estado IN :modeEstado AND (sv.socio LIKE :filtertext or sv.numerodocumento like :filtertext) ORDER BY sv.socio, sv.idsocio ASC"),
		@NamedQuery(name = SocioView.FindByFilterTextSocioViewAllHaveCuentaAporte, query = "SELECT sv FROM SocioView sv WHERE sv.cuentaAporte IS NOT NULL AND sv.estado IN :modeEstado AND (sv.socio LIKE :filtertext or sv.numerodocumento like :filtertext) ORDER BY sv.socio, sv.idsocio ASC") })
public class SocioView implements Serializable {
	private static final long serialVersionUID = 1L;

	public final static String findAll = "SocioView.findAll";
	public final static String FindAllHaveCuentaAporte = "SocioView.FindAllHaveCuentaAporte";
	public final static String FindByFilterTextSocioView = "SocioView.FindByFilterTextSocioView";
	public final static String FindByFilterTextSocioViewAllHaveCuentaAporte = "SocioView.FindByFilterTextSocioViewAllHaveCuentaAporte";

	private BigInteger idsocio;

	private int estado;

	private CuentaAporte cuentaAporte;

	private String numeroCuentaAporte;

	private EstadoCuentaAporte estadoCuentaAporte;

	private String tipodocumento;

	private TipoPersona tipopersona;

	private String numerodocumento;

	private String socio;

	private Date fecNacfecConst;

	private Date fechaasociado;

	private String direccion;

	private String email;

	public SocioView() {
	}

	@XmlElement(name = "id")
	@Id
	@Column(name = "ID_SOCIO", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdsocio() {
		return this.idsocio;
	}

	public void setIdsocio(BigInteger idsocio) {
		this.idsocio = idsocio;
	}

	@XmlElement(name = "direccion")
	@Column(name = "DIRECCION", nullable = false, length = 70, columnDefinition = "nvarchar2")
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@XmlElement(name = "email")
	@Column(name = "EMAIL", nullable = false, length = 70, columnDefinition = "nvarchar2")
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@XmlElement
	@Column(name = "SOCIO", nullable = false, length = 192, columnDefinition = "nvarchar2")
	public String getSocio() {
		return this.socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	@XmlElement(name = "tipoDocumento")
	@Column(name = "TIPO_DOCUMENTO", nullable = false, length = 20, columnDefinition = "nvarchar2")
	public String getTipodocumento() {
		return tipodocumento;
	}

	public void setTipodocumento(String tipodocumento) {
		this.tipodocumento = tipodocumento;
	}

	@XmlElement(name = "numeroDocumento")
	@Column(name = "NUMERO_DOCUMENTO", nullable = false, length = 20, columnDefinition = "nvarchar2")
	public String getNumerodocumento() {
		return numerodocumento;
	}

	public void setNumerodocumento(String numerodocumento) {
		this.numerodocumento = numerodocumento;
	}

	@XmlElement(name = "tipoPersona")
	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_PERSONA", nullable = false, length = 20, columnDefinition = "nvarchar2")
	public TipoPersona getTipopersona() {
		return tipopersona;
	}

	public void setTipopersona(TipoPersona tipopersona) {
		this.tipopersona = tipopersona;
	}

	@XmlElement
	@Temporal(TemporalType.DATE)
	@Column(name = "\"FEC. NAC. / FEC. CONST.\"")
	public Date getFecNacFecConst() {
		return fecNacfecConst;
	}

	public void setFecNacFecConst(Date fecNacFecConst) {
		fecNacfecConst = fecNacFecConst;
	}

	@XmlElement(name = "fechaAsociado")
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_ASOCIADO")
	public Date getFechaasociado() {
		return fechaasociado;
	}

	public void setFechaasociado(Date fechaasociado) {
		this.fechaasociado = fechaasociado;
	}

	@XmlElement(name = "estado")
	@Column(name = "ESTADO_SOCIO", nullable = false)
	public boolean getEstado() {
		return (this.estado == 1 ? true : false);
	}

	public void setEstado(boolean estado) {
		this.estado = (estado ? 1 : 0);
	}

	@XmlElement(name = "cuentaAporte")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CUENTA_APORTE", nullable = true)
	public CuentaAporte getCuentaAporte() {
		return cuentaAporte;
	}

	public void setCuentaAporte(CuentaAporte cuentaAporte) {
		this.cuentaAporte = cuentaAporte;
	}

	@XmlElement(name = "numeroCuentaAporte")
	@Column(name = "NUMERO_CUENTA_APORTE", nullable = false, length = 14, columnDefinition = "nvarchar2")
	public String getNumeroCuentaAporte() {
		return numeroCuentaAporte;
	}

	public void setNumeroCuentaAporte(String numeroCuentaAporte) {
		this.numeroCuentaAporte = numeroCuentaAporte;
	}

	@XmlElement(name = "estadoCuentaAporte")
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO_CUENTA_APORTE", nullable = false, length = 20, columnDefinition = "nvarchar2")
	public EstadoCuentaAporte getEstadoCuentaAporte() {
		return estadoCuentaAporte;
	}

	public void setEstadoCuentaAporte(EstadoCuentaAporte estadoCuentaAporte) {
		this.estadoCuentaAporte = estadoCuentaAporte;
	}

}