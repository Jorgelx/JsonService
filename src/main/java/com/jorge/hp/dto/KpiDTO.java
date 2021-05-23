package com.jorge.hp.dto;

import java.util.List;
import java.util.Map;

/**
 * Clase 
 * 
 * Controlador REST
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

import com.jorge.hp.model.Json;

/**
 * Class KpiDTO
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */
public class KpiDTO {
	public List<Json> json;
	public int number_of_jsons;
	public int number_of_rows;
	public int messages;
	public int calls;
	public Map<String, Integer> total_number_of_different_origin_country;
	public Map<String, Integer> total_number_of_different_destination_country;
	public Map<List<Integer>, Double> duration_of_each_json_process_and_average;

	public List<Json> getJson() {
		return json;
	}

	public void setJson(List<Json> json) {
		this.json = json;
	}

	public int getNumber_of_rows() {
		return number_of_rows;
	}

	public void setNumber_of_rows(int number_of_rows) {
		this.number_of_rows = number_of_rows;
	}

	public int getMessages() {
		return messages;
	}

	public void setMessages(int messages) {
		this.messages = messages;
	}

	public int getCalls() {
		return calls;
	}

	public void setCalls(int calls) {
		this.calls = calls;
	}

	public int getNumber_of_jsons() {
		return number_of_jsons;
	}

	public void setNumber_of_jsons(int number_of_jsons) {
		this.number_of_jsons = number_of_jsons;
	}

	public Map<List<Integer>, Double> getDuration_of_each_json_process_and_average() {
		return duration_of_each_json_process_and_average;
	}

	public void setDuration_of_each_json_process_and_average(
			Map<List<Integer>, Double> duration_of_each_json_process_and_average) {
		this.duration_of_each_json_process_and_average = duration_of_each_json_process_and_average;
	}

	public Map<String, Integer> getTotal_number_of_different_origin_country() {
		return total_number_of_different_origin_country;
	}

	public void setTotal_number_of_different_origin_country(
			Map<String, Integer> total_number_of_different_origin_country) {
		this.total_number_of_different_origin_country = total_number_of_different_origin_country;
	}

	public Map<String, Integer> getTotal_number_of_different_destination_country() {
		return total_number_of_different_destination_country;
	}

	public void setTotal_number_of_different_destination_country(
			Map<String, Integer> total_number_of_different_destination_country) {
		this.total_number_of_different_destination_country = total_number_of_different_destination_country;
	}

	@Override
	public String toString() {
		return "KpiDTO [json=" + json + ", number_of_jsons=" + number_of_jsons + ", number_of_rows=" + number_of_rows
				+ ", messages=" + messages + ", calls=" + calls + ", total_number_of_different_origin_country="
				+ total_number_of_different_origin_country + ", total_number_of_different_destination_country="
				+ total_number_of_different_destination_country + ", duration_of_each_json_process_and_average="
				+ duration_of_each_json_process_and_average + "]";
	}

}
