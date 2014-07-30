package org.sistemafinanciero.entity.dto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.sistemafinanciero.entity.Moneda;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class GenericMonedaDetalle implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement
	private Moneda moneda;

	@XmlElement
	private Set<GenericDetalle> detalle;

	public GenericMonedaDetalle() {
		this.detalle = new TreeSet<GenericDetalle>(); 
	}
	
	public GenericMonedaDetalle(Moneda moneda) {
		this.moneda = moneda;
		this.detalle = new TreeSet<GenericDetalle>();
	}
	
	public GenericMonedaDetalle(Moneda moneda, Set<GenericDetalle> detalle) {
		this.moneda = moneda;
		this.detalle = detalle;
	}
	
	public BigDecimal getTotal(){
		BigDecimal result = BigDecimal.ZERO;
		for (GenericDetalle detalle : this.detalle) {
			result = result.add(detalle.getSubtotal());
		}
		return result;
	}
	
	public boolean addElementDetalleIfNotExists(GenericDetalle detalle){		
		return this.detalle.add(detalle);
	}
	
	public boolean addElementDetalleReplacingIfExist(GenericDetalle detalle){
		if(this.detalle.contains(detalle)){
			this.detalle.remove(detalle);			
		}
		return this.detalle.add(detalle);
	}
	
	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Set<GenericDetalle> getDetalle() {
		return detalle;
	}

	public void setDetalle(Set<GenericDetalle> detalle) {
		this.detalle = detalle;
	}

	@Override
	public String toString() {
		return "[" + this.moneda.toString() + ":" + this.detalle.toString()
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof GenericMonedaDetalle)) {
			return false;
		}
		final GenericMonedaDetalle other = (GenericMonedaDetalle) obj;
		return other.moneda.equals(this.moneda);
	}

	@Override
	public int hashCode() {
		return this.moneda.hashCode();
	}
}
