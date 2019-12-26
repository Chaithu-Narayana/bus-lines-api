/**
 * 
 */
package com.trafiklab.bus.lines.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 *
 */
public interface BusLinesService {

	String findById();
	
	List<Integer> findTop10BusLines() throws JsonParseException, JsonMappingException, IOException;
	
	public List<Integer> findStopsOnLine(int lineNumber) throws JsonParseException, JsonMappingException, IOException;
}
