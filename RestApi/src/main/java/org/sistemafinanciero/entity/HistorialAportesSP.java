package org.sistemafinanciero.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * The persistent class for the CUENTA_BANCARIA_VIEW database table.
 * 
 */
@NamedStoredProcedureQuery(
	name = "prueba", 
	resultClasses = HistorialAportesSP.class, 
	procedureName = "HISTORIAL_APORTES_SP", 
	parameters = { 
		@StoredProcedureParameter(
			mode = ParameterMode.IN,
			type = BigInteger.class, 			
			name="ID_SOCIO_ENVIADO"
		),
		@StoredProcedureParameter(
				mode = ParameterMode.IN,
				type = Date.class, 			
				name="DESDE"
		),
		@StoredProcedureParameter(
				mode = ParameterMode.IN,
				type = Date.class, 			
				name="HASTA"
		),
		@StoredProcedureParameter(
				mode = ParameterMode.REF_CURSOR, 
				name = "CURSOR_", 
				type = void.class)
	}
)
//@Entity
public class HistorialAportesSP implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String findAll = "HistorialAportesSP.findAll";
	
	private BigInteger idSocio;
	private Date fecha;
	private int anio;
	private int mes;
	private BigDecimal monto;

	public HistorialAportesSP() {
	}

	@XmlElement(name = "id")
	@Id
	@Column(name = "ID_SOCIO", unique = true, nullable = false)
	public BigInteger getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(BigInteger idSocio) {
		this.idSocio = idSocio;
	}

	@XmlElement
	@Column(name = "ANIO", precision = 22, scale = 0)
	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	@XmlElement
	@Column(name = "MES", precision = 22, scale = 0)
	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	@XmlTransient
	@Column(name = "MONTO", precision = 22, scale = 0)
	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	@XmlElement
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", nullable = false, length = 7)
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}