package com.trafiklab.bus.lines.controller;

import com.trafiklab.bus.lines.model.Line;
import com.trafiklab.bus.lines.model.StopPoint;
import com.trafiklab.bus.lines.service.BusLinesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller orchestrating the requests for endpoints of 'bus-lines' api
 */
@RestController
@RequestMapping(path = "/bus-lines")
public class BusLinesController {

    private final BusLinesService busLinesService;

    @Autowired
    public BusLinesController(BusLinesService busLinesService) {
        this.busLinesService = busLinesService;
    }

    @GetMapping(value = "/top-10-lines", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Line> findLinesWithMostStops() {
        return busLinesService.findLinesWithMostStops(10);
    }

    @GetMapping(value = "/stops-on-line/{lineNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StopPoint> findStopsOnLine(@PathVariable int lineNumber) {

        if (lineNumber < 1) {
            throw new IllegalArgumentException("line number should be a positive integer");
        }

        return busLinesService.findStopsOnLine(lineNumber);
    }
}
