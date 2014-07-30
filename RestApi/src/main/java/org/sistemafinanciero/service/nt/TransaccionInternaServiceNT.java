package org.sistemafinanciero.service.nt;

import java.math.BigInteger;

import javax.ejb.Remote;

import org.sistemafinanciero.entity.dto.VoucherTransaccionBovedaCaja;
import org.sistemafinanciero.entity.dto.VoucherTransaccionCajaCaja;


@Remote
public interface TransaccionInternaServiceNT {
	
	public VoucherTransaccionBovedaCaja getVoucherTransaccionBovedaCaja(BigInteger idTransaccionBovedaCaja);
	
	public VoucherTransaccionCajaCaja getVoucherTransaccionCajaCaja(BigInteger idTransaccionCajaCaja);
	
}
