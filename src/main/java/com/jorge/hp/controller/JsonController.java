package com.jorge.hp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jorge.hp.HpApplication;
import com.jorge.hp.dto.KpiDTO;
import com.jorge.hp.dto.Message;
import com.jorge.hp.dto.MetricDTO;
import com.jorge.hp.model.Json;
import com.jorge.hp.service.JsonService;

/**
 * Clase JsonController
 * 
 * REST Controller
 * 
 * @version 1.0
 * @author Jorge
 * 
 *         21/05/2021
 * 
 */
@RestController
public class JsonController {
	private static final Logger logger = LoggerFactory.getLogger(HpApplication.class);
	@Autowired
	private JsonService service;

	/**
	 * Method getJson(String date)
	 * 
	 * Call JsonService to return full JSON List received
	 * 
	 * @param String date
	 * @return ResponseEntity<List<Json>,HpptStatus>
	 * 
	 *         21/05/2054
	 * 
	 */
	@GetMapping({ "/{date}", "/" })
	public ResponseEntity<?> getJson(@PathVariable(required = false) String date)
			throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
		logger.info("---   START : date " + date);
		if (date == null || date.isBlank()) {
			return new ResponseEntity<Message>(new Message(
					"Date is empty, please, enter date beetween: 20180131 20180201 (20180202 not's full process,  can't fix 'OK  )"),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Json>>(service.getJson(date), HttpStatus.OK);
		}
	}

	/**
	 * Method kpis(String date)
	 * 
	 * Call JsonService to return full JSON List received, and kpis
	 * 
	 * @param String date
	 * @return ResponseEntity<KpiDTO,HpptStatus>
	 * 
	 *         21/05/2054
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * 
	 */
	@GetMapping({ "kpis/{date}", "kpi/" })
	public ResponseEntity<?> kpis(@PathVariable(required = false) String date)
			throws IllegalArgumentException, IllegalAccessException {
		logger.info("---   START : date " + date);
		service.getJson(date);
		if (date == null || date.equals("")) {
			return new ResponseEntity<Message>(new Message(
					"Date is empty, please, enter date beetween: 20180131 20180201 (20180202 not's full process,  can't fix 'OK  )"),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<KpiDTO>(service.kpis(), HttpStatus.OK);
		}
	}

	/**
	 * Method metric(String date)
	 * 
	 * Call JsonService to return full JSON List received and metrics
	 * 
	 * @param String date
	 * @return ResponseEntity<MetricDTO,HpptStatus>
	 * 
	 *         21/05/2054
	 * 
	 */
	@GetMapping({ "metrics/{date}", "metric/" })
	public ResponseEntity<?> metric(@PathVariable(required = false) String date)
			throws IllegalArgumentException, IllegalAccessException, JsonProcessingException {
		logger.info("---   START : date " + date);
		service.getJson(date);
		if (date == null || date.isBlank()) {
			return new ResponseEntity<Message>(new Message(
					"Date is empty, please, enter date beetween: 20180131 20180201 (20180202 not's full process,  can't fix 'OK  )"),
					HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<MetricDTO>(service.metrics(), HttpStatus.OK);
		}
	}
}
