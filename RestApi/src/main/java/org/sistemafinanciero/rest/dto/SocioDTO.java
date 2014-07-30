package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.math.BigInteger;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.type.TipoPersona;

@XmlRootElement(name = "socioDTO")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class SocioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TipoPersona tipoPersona;
	private BigInteger idTipoDocumentoSocio;
	private String numeroDocumentoSocio;
	private BigInteger idTipoDocumentoApoderado;
	private String numeroDocumentoApoderado;

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public BigInteger getIdTipoDocumentoSocio() {
		return idTipoDocumentoSocio;
	}

	public void setIdTipoDocumentoSocio(BigInteger idTipoDocumentoSocio) {
		this.idTipoDocumentoSocio = idTipoDocumentoSocio;
	}

	public String getNumeroDocumentoSocio() {
		return numeroDocumentoSocio;
	}

	public void setNumeroDocumentoSocio(String numeroDocumentoSocio) {
		this.numeroDocumentoSocio = numeroDocumentoSocio;
	}

	public BigInteger getIdTipoDocumentoApoderado() {
		return idTipoDocumentoApoderado;
	}

	public void setIdTipoDocumentoApoderado(BigInteger idTipoDocumentoApoderado) {
		this.idTipoDocumentoApoderado = idTipoDocumentoApoderado;
	}

	public String getNumeroDocumentoApoderado() {
		return numeroDocumentoApoderado;
	}

	public void setNumeroDocumentoApoderado(String numeroDocumentoApoderado) {
		this.numeroDocumentoApoderado = numeroDocumentoApoderado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
