package org.sistemafinanciero.entity;

// Generated 02-may-2014 11:48:28 by Hibernate Tools 4.0.0

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * TipoDocumento generated by hbm2java
 */
@Entity
@Table(name = "TIPO_DOCUMENTO", schema = "BDSISTEMAFINANCIERO")
@XmlRootElement(name = "tipodocumento")
@XmlAccessorType(XmlAccessType.NONE)
@NamedQueries({ @NamedQuery(name = TipoDocumento.findByTipopersona, query = "SELECT t FROM TipoDocumento t WHERE t.tipoPersona = :tipopersona") })
public class TipoDocumento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String findByTipopersona = "TipoDocumento.findByTipopersona";

	private BigInteger idTipoDocumento;
	private String denominacion;
	private String abreviatura;
	private String tipoPersona;
	private BigInteger numeroCaracteres;
	private BigInteger estado;
	private Set personaNaturals = new HashSet(0);
	private Set personaJuridicas = new HashSet(0);

	public TipoDocumento() {
	}

	public TipoDocumento(BigInteger idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public TipoDocumento(BigInteger idTipoDocumento, String denominacion,
			String abreviatura, String tipoPersona,
			BigInteger numeroCaracteres, BigInteger estado,
			Set personaNaturals, Set personaJuridicas) {
		this.idTipoDocumento = idTipoDocumento;
		this.denominacion = denominacion;
		this.abreviatura = abreviatura;
		this.tipoPersona = tipoPersona;
		this.numeroCaracteres = numeroCaracteres;
		this.estado = estado;
		this.personaNaturals = personaNaturals;
		this.personaJuridicas = personaJuridicas;
	}

	@XmlElement(name="id")
	@Id
	@Column(name = "ID_TIPO_DOCUMENTO", unique = true, nullable = false, precision = 22, scale = 0)
	public BigInteger getIdTipoDocumento() {
		return this.idTipoDocumento;
	}

	public void setIdTipoDocumento(BigInteger idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	@XmlElement
	@Column(name = "DENOMINACION", length = 60, columnDefinition = "nvarchar2")
	public String getDenominacion() {
		return this.denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@XmlElement
	@Column(name = "ABREVIATURA", length = 40, columnDefinition = "nvarchar2")
	public String getAbreviatura() {
		return this.abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@XmlElement
	@Column(name = "TIPO_PERSONA", length = 40, columnDefinition = "nvarchar2")
	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	@XmlElement
	@Column(name = "NUMERO_CARACTERES", precision = 22, scale = 0)
	public BigInteger getNumeroCaracteres() {
		return this.numeroCaracteres;
	}

	public void setNumeroCaracteres(BigInteger numeroCaracteres) {
		this.numeroCaracteres = numeroCaracteres;
	}

	@Column(name = "ESTADO", precision = 22, scale = 0)
	public BigInteger getEstado() {
		return this.estado;
	}

	public void setEstado(BigInteger estado) {
		this.estado = estado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocumento")
	public Set<PersonaNatural> getPersonaNaturals() {
		return this.personaNaturals;
	}

	public void setPersonaNaturals(Set personaNaturals) {
		this.personaNaturals = personaNaturals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocumento")
	public Set<PersonaJuridica> getPersonaJuridicas() {
		return this.personaJuridicas;
	}

	public void setPersonaJuridicas(Set personaJuridicas) {
		this.personaJuridicas = personaJuridicas;
	}

	@Override
	public String toString() {
		return this.denominacion;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof TipoDocumento)) {
			return false;
		}
		final TipoDocumento other = (TipoDocumento) obj;
		if(this.abreviatura != null)
			return other.getAbreviatura().equalsIgnoreCase(this.abreviatura);
		if(this.idTipoDocumento != null)
			return other.getIdTipoDocumento().equals(this.idTipoDocumento);
		return false;
	}

	@Override
	public int hashCode() {
		if(this.abreviatura != null)
			return this.abreviatura.hashCode();
		if(this.idTipoDocumento != null)
			return this.idTipoDocumento.hashCode();
		return 0;
	}
	
}