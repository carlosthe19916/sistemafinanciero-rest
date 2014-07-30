package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.type.CustomType;
import org.sistemafinanciero.entity.TipoDocumento;
import org.sistemafinanciero.entity.type.TipoEmpresa;

@XmlRootElement(name = "personaJuridicaDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class PersonaJuridicaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger id;
	private TipoDocumento tipoDocumento;
	private String numeroDocumento;
	private String razonSocial;
	private String nombreComercial;
	private Date fechaConstitucion;
	private String actividadPrincipal;
	private String direccion;
	private String referencia;
	private String telefono;
	private String celular;
	private String email;
	private TipoEmpresa tipoEmpresa;
	private boolean finLucro;
	private String ubigeo;

	private BigInteger idRepresentanteLegal;

	private Set<Accionista> accionistas;

	public PersonaJuridicaDTO() {
		// TODO Auto-generated constructor stub
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public TipoDocumento getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(TipoDocumento idTipoDocumento) {
		this.tipoDocumento = idTipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Date getFechaConstitucion() {
		return fechaConstitucion;
	}

	public void setFechaConstitucion(Date fechaConstitucion) {
		this.fechaConstitucion = fechaConstitucion;
	}

	public String getActividadPrincipal() {
		return actividadPrincipal;
	}

	public void setActividadPrincipal(String actividadPrincipal) {
		this.actividadPrincipal = actividadPrincipal;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoEmpresa getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(TipoEmpresa tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public boolean isFinLucro() {
		return finLucro;
	}

	public void setFinLucro(boolean finLucro) {
		this.finLucro = finLucro;
	}

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public Set<Accionista> getAccionistas() {
		return accionistas;
	}

	public void setAccionistas(Set<Accionista> accionistas) {
		this.accionistas = accionistas;
	}

	@XmlRootElement(name = "accionistaDTO")
	@XmlAccessorType(XmlAccessType.PROPERTY)
	public static class Accionista implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		BigInteger idPersona;
		int porcentaje;

		public Accionista() {
			// TODO Auto-generated constructor stub
		}

		public Accionista(CustomType customType) {

		}

		public Accionista(BigInteger idPersona, int porcentaje) {
			this.idPersona = idPersona;
			this.porcentaje = porcentaje;
		}

		public BigInteger getIdPersona() {
			return idPersona;
		}

		public void setIdPersona(BigInteger idPersona) {
			this.idPersona = idPersona;
		}

		public int getPorcentaje() {
			return porcentaje;
		}

		public void setPorcentaje(int porcentaje) {
			this.porcentaje = porcentaje;
		}

	}

	public BigInteger getIdRepresentanteLegal() {
		return idRepresentanteLegal;
	}

	public void setIdRepresentanteLegal(BigInteger idRepresentanteLegal) {
		this.idRepresentanteLegal = idRepresentanteLegal;
	}

}
