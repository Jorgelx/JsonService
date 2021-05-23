package com.jorge.hp.dto;

import java.util.Map;

/**
 * Class CallsDTO
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

public class CallsDTO {

	public Map<String, Integer> mapOrig;
	public Map<String, Integer> mapDest;

	public CallsDTO() {
	}

	public CallsDTO(Map<String, Integer> mapOrig, Map<String, Integer> mapDest) {
		this.mapOrig = mapOrig;
		this.mapDest = mapDest;
	}

	public Map<String, Integer> getMapOrig() {
		return mapOrig;
	}

	public void setMapOrig(Map<String, Integer> mapOrig) {
		this.mapOrig = mapOrig;
	}

	public Map<String, Integer> getMapDest() {
		return mapDest;
	}

	public void setMapDest(Map<String, Integer> mapDest) {
		this.mapDest = mapDest;
	}

	@Override
	public String toString() {
		return "CallsDTO [mapOrig=" + mapOrig + ", mapDest=" + mapDest + "]";
	}

}
