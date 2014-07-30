package org.sistemafinanciero.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jsend implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private enum STATUS_VALUE {
		success, fail, error
	}

	private STATUS_VALUE status;
	private Object data;
	private List<String> message;

	public static Jsend getErrorJSend() {
		Jsend jsend = new Jsend(STATUS_VALUE.fail);
		return jsend;
	}

	public static Jsend getErrorJSend(String message) {
		Jsend jsend = new Jsend(STATUS_VALUE.fail);
		jsend.addMessage(message);
		return jsend;
	}

	public Jsend addMessage(String message) {
		this.message.add(message);
		return this;
	}

	private Jsend() {
		// TODO Auto-generated constructor stub
	}

	private Jsend(STATUS_VALUE status, Object data) {
		this.status = status;
		this.data = data;
		this.message = new ArrayList<String>();
	}

	private Jsend(STATUS_VALUE status) {
		this.status = status;
		this.data = null;
		this.message = new ArrayList<String>();
	}

	public STATUS_VALUE getStatus() {
		return status;
	}

	public void setStatus(STATUS_VALUE status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}

}
