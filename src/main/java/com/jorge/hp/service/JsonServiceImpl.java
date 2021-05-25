package com.jorge.hp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jorge.hp.HpApplication;
import com.jorge.hp.dto.CallsDTO;
import com.jorge.hp.dto.KpiDTO;
import com.jorge.hp.dto.MetricDTO;
import com.jorge.hp.dto.StatusDTO;
import com.jorge.hp.model.Country;
import com.jorge.hp.model.Json;

/**
 * Class JsonServiceImpl
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */
@Service
public class JsonServiceImpl implements JsonService{
	ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(HpApplication.class);
	List<Json> jsonList = new ArrayList<Json>();
	int error;
	HashMap<String, Double> averageCountryDuration = new HashMap<String, Double>();

	/**
	 * Method getJson(String date)
	 * 
	 * Method to return full JsonList received
	 * 
	 * @param String date
	 * @return List<Json>
	 * 
	 *         21/05/2021
	 * 
	 */
	public List<Json> getJson(String date) {
		jsonList = new ArrayList<Json>();
		ObjectMapper mapper = new ObjectMapper();
		error = 0;
		try {
			URL url = new URL("https://raw.githubusercontent.com/vas-test/test1/master/logs/MCP_" + date + ".json");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"KO   ---   Error getting logs - https://raw.githubusercontent.com/vas-test/test1/master/logs/   ------"
								+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {
				Json json = mapper.readValue(line, Json.class);
				jsonList.add(json);
			}
			logger.info("OK   ---   JsonService.getJson(List<JsonEntity>)   ---");
		} catch (Exception e) {
			error = 1;
			logger.info("KO   ---   JsonService.getJson(List<JsonEntity>)   ---" + e);
		}
		return jsonList;
	}

	/**
	 * Method kpis()
	 * 
	 * Method to get full Json received and kpis
	 * 
	 * @param
	 * @return KpiDTO
	 * 
	 *         21/05/2021
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	public KpiDTO kpis() throws IllegalArgumentException, IllegalAccessException {
		KpiDTO kpi = new KpiDTO();
		kpi.setJson(jsonList);
		kpi.setNumber_of_jsons(countJsonFiles());
		kpi.setNumber_of_rows(countRows());
		kpi.setCalls(totalNumberOfCalls());
		kpi.setMessages(totalNumberOfMessages());
		kpi.setDuration_of_each_json_process_and_average(durationOfEachJsonProcess());
		kpi.setTotal_number_of_different_origin_country(originsCall());
		kpi.setTotal_number_of_different_destination_country(destinationCall());

		return kpi;
	}

	/**
	 * Method MetricDTO
	 * 
	 * Method to get full Json received and metrics
	 * 
	 * @param
	 * @return MetricDTO
	 * 
	 *         21/05/2021
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws JsonProcessingException
	 * 
	 */
	public MetricDTO metrics() throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
		MetricDTO metric = new MetricDTO();
		metric.setJson(jsonList);
		metric.setMissing_fields(countNulls());
		metric.setBlank_content(countBlanks());
		metric.setError(error);
		metric.setCountries_calls(countryRepeats());
		metric.setStatus_relation(relationOkKo());
		metric.setAverage_call_duration_groped_by_country(averageCountryDuration);
		metric.setWord_ocurrence(sortByValue(countWords()));

		return metric;
	}

	/**
	 * Method
	 * 
	 * Method to get all countries and how many times repeats
	 * 
	 * @param
	 * @return Map<String, Integer>
	 * 
	 *         21/05/2021
	 * @throws JsonProcessingException
	 * 
	 */
	public Map<String, Integer> countryRepeats() throws JsonProcessingException {
		Map<String, Integer> mapDest = ckeckCountry().getMapDest();
		Map<String, Integer> mapOrig = ckeckCountry().getMapDest();
		Map<String, Integer> mapFinal = new HashMap<>(mapDest);
		mapOrig.forEach((key, value) -> mapFinal.merge(key, value, (v1, v2) -> (v1 + v2)));
		logger.info("OK   ---   JsonService.countryRepeats(String) --- " + mapFinal.toString());
		return mapFinal;
	}

	/**
	 * Método ckeckCountry()
	 * 
	 * Method to check all country codes in Json and his repeats
	 * 
	 * @param
	 * @return CallsDTO
	 * 
	 *         21/05/2021
	 * 
	 */
	public CallsDTO ckeckCountry() {
		List<Json> list = jsonList;
		List<Country> countries = getCountries();
		Map<String, Integer> mapDest = new HashMap<String, Integer>();
		Map<String, Integer> mapOrig = new HashMap<String, Integer>();
		averageCountryDuration.clear();
		double duration = 0;
		int div = 0;
		CallsDTO calls;
		for (Json j : list) {
			String prefDest = "0";
			String prefOrig = "0";
			for (Country c : countries) {
				String prefix = c.code.replace("+", "");
				if (j.destination != null)
					prefDest = j.destination.toString().substring(0, prefix.length());
				if (j.origin != null)
					prefOrig = j.origin.toString().substring(0, prefix.length());
				boolean isPrefDest = prefix.equals(prefDest);
				boolean isPrefOrig = prefix.equals(prefOrig);

				if (isPrefDest) {
					if (!mapDest.containsKey(c.name))
						mapDest.put(c.name, 0);
					mapDest.put(c.name, mapDest.get(c.name) + 1);
					if (j.getDuration() != null) {
						duration = duration + j.getDuration();
						div = div + 1;
						averageCountryDuration.put(c.name, duration / div);

					}
				}
				if (isPrefOrig) {
					if (!mapOrig.containsKey(c.name))
						mapOrig.put(c.name, 0);
					mapOrig.put(c.name, mapOrig.get(c.name) + 1);
				}
			}

		}
		calls = new CallsDTO(mapOrig, mapDest);
		return calls;
	}

	/**
	 * Method getCountries()
	 * 
	 * Method to get all countries code in other Json uploeaded in my Github https://raw.githubusercontent.com/Jorgelx/json/main/PrefixNumbersJson"
	 * 
	 * @param
	 * @return List<Country>
	 * 
	 *         21/05/2021
	 * 
	 */
	public List<Country> getCountries() {
		StringBuilder sb = new StringBuilder();
		List<Country> countries = new ArrayList<Country>();
		try {
			URL url = new URL("https://raw.githubusercontent.com/Jorgelx/JsonService/master/Countries");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException(
						"Error getting prefix numbers - https://raw.githubusercontent.com/Jorgelx/json/main/PrefixNumbersJson ------  : "
								+ conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			String result = sb.toString();
			JsonNode arr = new ObjectMapper().readTree(result).get("countries");
			if (arr.isArray()) {
				for (JsonNode obj : arr) {
					String name = obj.get("name").asText();
					String code = obj.get("code").asText().replace("+", "");
					Country country = new Country(code, name);
					countries.add(country);
				}
				return countries;
			}
		} catch (Exception e2) {
			logger.info("KO   ---   JsonService.getCountries(List<JsonEntity>)   --- ");
			e2.printStackTrace();
		}
		return countries;
	}

	/**
	 * Method countJsonFiles()
	 * 
	 * Method to count all Json objects receibed
	 * 
	 * @param int
	 * @return
	 * 
	 *         21/05/2021
	 * 
	 */
	public int countJsonFiles() {
		List<Json> list = jsonList;
		logger.info("OK   ---   JsonService.countJsonFiles(List<JsonEntity>)   --- " + list.size());
		return list.size();
	}

	/**
	 * Method countNulls()
	 * 
	 * Method to count all null rows in Json
	 * 
	 * @param
	 * @return
	 * 
	 *         21/05/2021
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	public int countNulls() throws IllegalArgumentException, IllegalAccessException {
		List<Json> list = jsonList;
		int counter = 0;
		for (Json l : list) {
			for (Field field : l.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object attribute = field.get(l);
				if (attribute == null) {
					counter++;
				}
			}
		}
		logger.info("OK   ---   JsonService.countNulls(List<JsonEntity>)   --- " + counter);
		return counter;
	}

	/**
	 * Method countBlanks()
	 * 
	 * Method to count all blanks rows in Json
	 * 
	 * @param
	 * @return int
	 * 
	 *         21/05/2021
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	public int countBlanks() throws IllegalArgumentException, IllegalAccessException {
		List<Json> list = jsonList;
		int counter = 0;
		for (Json l : list) {
			for (Field field : l.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object attribute = field.get(l);
				if (attribute == "") {
					counter++;
				}
			}
		}
		logger.info("OK   ---   JsonService.countBlanks(List<JsonEntity>)   --- " + counter);
		return counter;
	}

	/**
	 * Method countRows()
	 * 
	 * Method to count all rows in Json
	 * 
	 * @param
	 * @return int
	 * 
	 *         21/05/2021
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	public int countRows() throws IllegalArgumentException, IllegalAccessException {
		List<Json> list = jsonList;
		int counter = 0;
		for (Json l : list) {
			for (Field field : l.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				counter++;
			}

		}
		logger.info("OK   ---   JsonService.countWords(List<JsonEntity>)   --- " + counter);

		return counter;
	}

	/**
	 * Method totalNumberOfCalls()
	 * 
	 * Method to count total number of calls in Json
	 * 
	 * @param
	 * @return int
	 * 
	 *         21/05/2021
	 * 
	 */
	public int totalNumberOfCalls() {
		List<Json> listJ = jsonList;
		int calls = 0;
		for (Json j : listJ) {
			if (j.getMessage_type() != null)
				if (j.getMessage_type().equals("CALL"))
					calls++;
		}
		return calls;
	}

	/**
	 * Method totalNumberOfMessages()
	 * 
	 * Method to count total number of messages in Json
	 * 
	 * @param
	 * @return int
	 * 
	 *         21/05/2021
	 * 
	 */
	public int totalNumberOfMessages() {
		List<Json> listJ = jsonList;
		int msg = 0;
		for (Json j : listJ) {
			if (j.getMessage_type() != null)
				if (j.getMessage_type().equals("MSG"))
					msg++;
		}
		return msg;
	}

	/**
	 * Method durationOfEachJSonProcess()
	 * 
	 * Method to get duration of each json process and average
	 * 
	 * @param
	 * @return Map<List<Integer>, Double>
	 * 
	 *         21/05/2021
	 * 
	 */
	public Map<List<Integer>, Double> durationOfEachJsonProcess() {
		List<Json> listJ = jsonList;
		List<Integer> listDuration = new ArrayList<Integer>();
		Map<List<Integer>, Double> result = new HashMap<List<Integer>, Double>();
		double average = 0;
		int total = 0;
		int dividend;
		for (Json j : listJ) {
			if (j.getDuration() != null) {
				listDuration.add(j.getDuration());
			}
		}
		dividend = listDuration.size();
		for (int t : listDuration) {
			total = total + t;
			average = total / dividend;
		}
		result.put(listDuration, average);
		return result;
	}

	/**
	 * Method originsCall()
	 * 
	 * Method to get all country origins calls
	 * 
	 * @param
	 * @return Map<String, Integer>
	 * 
	 *         22/05/2021
	 * 
	 */
	public Map<String, Integer> originsCall() {
		return ckeckCountry().getMapOrig();

	}
	
	/**
	 * Method destinationCall()
	 * 
	 * Method to get all country destination calls
	 * 
	 * @param
	 * @return Map<String, Integer>
	 * 
	 *         22/05/2021
	 * 
	 */
	public Map<String, Integer> destinationCall() {
		return ckeckCountry().getMapDest();

	}

	/**
	 * Method relationOkKo()
	 * 
	 * Método to get relation of KO - OK - null Jsons
	 * 
	 * @param
	 * @return StatusDTO
	 * 
	 *         21/05/2021
	 * 
	 */
	public StatusDTO relationOkKo() {
		List<Json> listJ = jsonList;
		int okCount = 0;
		int koCount = 0;
		int nullCount = 0;
		for (Json j : listJ) {
			if (j.status_code != null) {
				if (j.status_code.equals("OK")) {
					okCount = okCount + 1;
				} else {
					koCount = koCount + 1;
				}
			} else {
				nullCount = nullCount + 1;
			}
		}
		StatusDTO status = new StatusDTO(okCount, koCount, nullCount);
		return status;
	}

	/**
	 * Method countWords()
	 * 
	 * Method to count all words in messages and many times his repeat
	 * 
	 * @param
	 * @return <String, Integer>
	 * 
	 *         21/05/2021
	 * 
	 */
	public HashMap<String, Integer> countWords() {
		List<Json> listJ = jsonList;
		HashMap<String, Integer> wordsMap = new LinkedHashMap<String, Integer>();
		int count = 0;
		for (Json j : listJ) {
			if (j.getMessage_content() != null) {

				String[] words = j.getMessage_content().split(" ");
				for (int i = 0; i < words.length; i++) {
					if (wordsMap.containsKey(words[i])) {
						count = wordsMap.get(words[i]) + 1;
						wordsMap.put(words[i], count);
					}
					if (!wordsMap.containsKey(words[i])) {
						wordsMap.put(words[i], 1);
					}
				}
			}
		}
		logger.info("OK   ---   JsonService.countWords(HashMap<String, Integer>)   --- ");
		return wordsMap;
	}

	/**
	 * Method sortByValue()
	 * 
	 * Method to sort map with word and his repeats in order desc
	 * 
	 * @param HashMap<String, Integer>
	 * @return HashMap<String, Integer> 
	 * 
	 *         21/05/2021
	 * 
	 */
	public HashMap<String, Integer> sortByValue(HashMap<String, Integer> map) {
		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
		return reverseSortedMap;
	}

}
