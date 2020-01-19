package com.trafiklab.bus.lines.service;

import com.trafiklab.bus.lines.model.Line;
import com.trafiklab.bus.lines.model.StopPoint;

import java.util.List;

/**
 *
 */
public interface BusLinesService {

    /**
     * Finds the lines with most no. of stops
     *
     * @param numberOfResults indicates the number of lines to output
     * @return n lines (with details) with most no. of stops; where n indicates the number given as input
     */
    List<Line> findLinesWithMostStops(int numberOfResults);

    /**
     * Finds all the stops (with details) served by the given linenumber
     *
     * @param lineNumber unique identification of a line
     * @return stops served by the given line
     */
    List<StopPoint> findStopsOnLine(int lineNumber);
}
