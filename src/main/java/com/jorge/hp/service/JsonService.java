package com.jorge.hp.service;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jorge.hp.dto.CallsDTO;
import com.jorge.hp.dto.KpiDTO;
import com.jorge.hp.dto.MetricDTO;
import com.jorge.hp.dto.StatusDTO;
import com.jorge.hp.model.Country;
import com.jorge.hp.model.Json;





/**
 * Interface JsonService 
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */

@Service
public interface JsonService {

	public List<Json> getJson(String date);
	public KpiDTO kpis() throws IllegalArgumentException, IllegalAccessException;
	public MetricDTO metrics() throws IllegalArgumentException, IllegalAccessException, JsonProcessingException ;
	public Map<String, Integer> countryRepeats() throws JsonProcessingException ;
	public CallsDTO ckeckCountry();
	public List<Country> getCountries();
	public int countJsonFiles();
	public int countNulls() throws IllegalArgumentException, IllegalAccessException ;
	public int countBlanks() throws IllegalArgumentException, IllegalAccessException ;
	public int countRows() throws IllegalArgumentException, IllegalAccessException;
	public int totalNumberOfCalls();
	public int totalNumberOfMessages() ;
	public Map<List<Integer>, Double> durationOfEachJsonProcess();
	public Map<String, Integer> originsCall();
	public StatusDTO relationOkKo();
	public HashMap<String, Integer> countWords();
	public HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) ;
}
