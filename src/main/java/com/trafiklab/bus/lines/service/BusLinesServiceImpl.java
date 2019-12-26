/**
 * 
 */
package com.trafiklab.bus.lines.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.trafiklab.bus.lines.model.JourneyPatternPointOnLine;

/**
 *
 */
@Service
public class BusLinesServiceImpl implements BusLinesService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final TrafiklabHelper trafiklabHelper;

	@Autowired
	public BusLinesServiceImpl(TrafiklabHelper trafiklabHelper) {
		this.trafiklabHelper = trafiklabHelper;
	}

	@Override
	public String findById() {
		return trafiklabHelper.findQuote();
	}

	@Override
	public List<Integer> findTop10BusLines() throws JsonParseException, JsonMappingException, IOException {

		Map<Integer, List<JourneyPatternPointOnLine>> journeyPatternsByLine = trafiklabHelper.findJourneyPatternsByLine();

		List<Integer> topLines = journeyPatternsByLine.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.comparingInt(List::size))).limit(10)
				.map(Map.Entry::getKey).collect(Collectors.toList());

		return topLines;
	}

	@Override
	public List<Integer> findStopsOnLine(int lineNumber) throws JsonParseException, JsonMappingException, IOException {

		Map<Integer, List<JourneyPatternPointOnLine>> journeyPatternsByLine = trafiklabHelper.findJourneyPatternsByLine();

		List<JourneyPatternPointOnLine> journeyPatternsOnLine = journeyPatternsByLine.entrySet().stream()
				.filter(entry -> lineNumber == entry.getKey()).map(map -> map.getValue()).flatMap(Collection::stream)
				.collect(Collectors.toList());

		return journeyPatternsOnLine.stream().map(journeyPattern -> journeyPattern.getJourneyPatternPointNumber())
				.collect(Collectors.toList());
	}
	
}
