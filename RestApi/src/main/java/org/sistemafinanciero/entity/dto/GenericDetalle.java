package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class GenericDetalle implements java.io.Serializable, Comparable<GenericDetalle>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private BigDecimal valor;

	@XmlElement
	private BigInteger cantidad;

	public GenericDetalle() {
		// TODO Auto-generated constructor stub
	}

	public GenericDetalle(BigDecimal valor, BigInteger cantidad) {
		this.valor = valor;
		this.cantidad = cantidad;
	}
	
	public BigDecimal getSubtotal() {
		return this.valor.multiply(new BigDecimal(cantidad));
	}
	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigInteger getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigInteger cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "[" + this.valor + ":" + this.cantidad + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof GenericDetalle)) {
			return false;
		}
		final GenericDetalle other = (GenericDetalle) obj;
		return other.valor.equals(this.valor);
	}

	@Override
	public int hashCode() {
		return this.valor.hashCode();
	}

	@Override
	public int compareTo(GenericDetalle o) {		
		return this.valor.compareTo(o.valor);
	}
}
