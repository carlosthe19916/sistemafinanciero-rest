package org.sistemafinanciero.rest.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "titular")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ApoderadoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	private BigInteger id;
	private BigInteger idTipoDocumento;
	private String numeroDocumento;	

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public BigInteger getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(BigInteger idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}	

}
