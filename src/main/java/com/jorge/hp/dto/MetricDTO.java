package com.jorge.hp.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jorge.hp.model.Json;

/**
 * Class MetricDTO
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

public class MetricDTO {

	public List<Json> json;
	public int missing_fields;
	public int blank_content;
	public int error;
	public Map<String, Integer> countries_calls;
	public StatusDTO status_relation;
	public HashMap<String, Double> average_call_duration_groped_by_country;
	public HashMap<String, Integer> word_ocurrence;

	public List<Json> getJson() {
		return json;
	}

	public void setJson(List<Json> json) {
		this.json = json;
	}

	public int getMissing_fields() {
		return missing_fields;
	}

	public void setMissing_fields(int missing_fields) {
		this.missing_fields = missing_fields;
	}

	public int getBlank_content() {
		return blank_content;
	}

	public void setBlank_content(int blank_content) {
		this.blank_content = blank_content;
	}

	public Map<String, Integer> getCountries_calls() {
		return countries_calls;
	}

	public void setCountries_calls(Map<String, Integer> countries_calls) {
		this.countries_calls = countries_calls;
	}

	public StatusDTO getStatus_relation() {
		return status_relation;
	}

	public void setStatus_relation(StatusDTO status_relation) {
		this.status_relation = status_relation;
	}

	public HashMap<String, Double> getAverage_call_duration_groped_by_country() {
		return average_call_duration_groped_by_country;
	}

	public void setAverage_call_duration_groped_by_country(
			HashMap<String, Double> average_call_duration_groped_by_country) {
		this.average_call_duration_groped_by_country = average_call_duration_groped_by_country;
	}

	public HashMap<String, Integer> getWord_ocurrence() {
		return word_ocurrence;
	}

	public void setWord_ocurrence(HashMap<String, Integer> word_ocurrence) {
		this.word_ocurrence = word_ocurrence;
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "MetricDTO [json=" + json + ", missing_fields=" + missing_fields + ", blank_content=" + blank_content
				+ ", error=" + error + ", countries_calls=" + countries_calls + ", status_relation=" + status_relation
				+ ", average_call_duration_groped_by_country=" + average_call_duration_groped_by_country
				+ ", word_ocurrence=" + word_ocurrence + "]";
	}

}
