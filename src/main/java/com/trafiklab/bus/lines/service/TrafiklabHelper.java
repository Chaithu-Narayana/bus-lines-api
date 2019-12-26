package com.trafiklab.bus.lines.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trafiklab.bus.lines.model.BaseModel;
import com.trafiklab.bus.lines.model.JourneyPatternPointOnLine;
import com.trafiklab.bus.lines.model.Quote;

@Component
public class TrafiklabHelper {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final RestTemplate restTemplate;

	@Autowired
	public TrafiklabHelper(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String findQuote() {
		Quote quote = restTemplate.getForObject("https://gturnquist-quoters.cfapps.io/api/random", Quote.class);
		return quote.toString();
	}

	@Cacheable("allJourneyPatternsForBuses")
	public Map<Integer, List<JourneyPatternPointOnLine>> findJourneyPatternsByLine()
			throws IOException, JsonParseException, JsonMappingException {
		List<JourneyPatternPointOnLine> results = findAllJourneyPatternsForBuses();

		Map<Integer, List<JourneyPatternPointOnLine>> journeyPatternsByLine = results.parallelStream()
				.collect(Collectors.groupingBy(JourneyPatternPointOnLine::getLineNumber));
		
		return journeyPatternsByLine;
	}

	private List<JourneyPatternPointOnLine> findAllJourneyPatternsForBuses()
			throws IOException, JsonParseException, JsonMappingException {

		BaseModel apiResponse = invokeTrafiklabApi();

		List<JourneyPatternPointOnLine> results = apiResponse.getResponseData().getResult();

		/*
		 * List<JourneyPatternPointOnLine> pointsOnLine = results.parallelStream()
		 * .map(result -> (JourneyPatternPointOnLine) result)
		 * .collect(Collectors.toList());
		 */

		return results;
	}

	private BaseModel invokeTrafiklabApi() throws IOException, JsonParseException, JsonMappingException {
		logger.info("Invoking trafiklab api for 'jour' models..");

		ObjectMapper objectMapper = new ObjectMapper();
		File file = new File("src/main/resources/lines-bus-jour.json");

		BaseModel apiResponse = objectMapper.readValue(file, BaseModel.class);

		return apiResponse;
	}

}
