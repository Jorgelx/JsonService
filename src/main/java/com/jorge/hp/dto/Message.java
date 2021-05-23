package com.jorge.hp.dto;

/**
 * Class Message
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

public class Message {
	public String text;

	public Message(String text) {
		this.text = text;
	}

	public String text() {
		return text;
	}

	public void setMensaje(String text) {
		this.text = text;
	}

}
