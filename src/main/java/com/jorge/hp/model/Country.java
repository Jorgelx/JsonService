package com.jorge.hp.model;

/**
 * Class Country
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

public class Country {

	public String code;
	public String name;

	public Country() {

	}

	public Country(String code, String name) {

		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Country [code=" + code + ", name=" + name + "]";
	}

}
