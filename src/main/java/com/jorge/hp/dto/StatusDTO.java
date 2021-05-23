package com.jorge.hp.dto;

/**
 * Class StatusDTO
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */
public class StatusDTO {

	public int ko;
	public int ok;
	public int nulls;

	public StatusDTO(int ko, int ok, int nulls) {
		this.ko = ko;
		this.ok = ok;
		this.nulls = nulls;
	}

	public int getKo() {
		return ko;
	}

	public void setKo(int ko) {
		this.ko = ko;
	}

	public int getOk() {
		return ok;
	}

	public void setOk(int ok) {
		this.ok = ok;
	}

	public int getNulls() {
		return nulls;
	}

	public void setNulls(int nulls) {
		this.nulls = nulls;
	}

	@Override
	public String toString() {
		return "StatusDTO [ko=" + ko + ", ok=" + ok + ", nulls=" + nulls + "]";
	}

}
