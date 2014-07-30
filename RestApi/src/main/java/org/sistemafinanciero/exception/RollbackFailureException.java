package org.sistemafinanciero.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class RollbackFailureException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public RollbackFailureException(String message, Throwable cause) {
        super(message, cause);
    }
    public RollbackFailureException(String message) {
        super(message);
    }
}
