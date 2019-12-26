/**
 * 
 */
package com.trafiklab.bus.lines.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.trafiklab.bus.lines.service.BusLinesService;

/**
 *
 */
@RestController
@RequestMapping(path = "/bus-lines")
public class BusLinesController {

	private final BusLinesService busLinesService;

	@Autowired
	public BusLinesController(BusLinesService busLinesService) {
		this.busLinesService = busLinesService;
	}

	@GetMapping(value = "/random-quote", produces = MediaType.APPLICATION_JSON_VALUE)
	public String findRandomQuote() {
		return busLinesService.findById();
	}

	@GetMapping(value = "/top-lines", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> findTop10Lines() throws JsonParseException, JsonMappingException, IOException {
		return busLinesService.findTop10BusLines();
	}

	@GetMapping(value = "/stops/{lineNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Integer> findStopsOnLine(@PathVariable int lineNumber)
			throws JsonParseException, JsonMappingException, IOException {
		return busLinesService.findStopsOnLine(lineNumber);
	}
}
