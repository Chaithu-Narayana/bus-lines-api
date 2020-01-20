package com.trafiklab.bus.lines.service;

import com.trafiklab.bus.lines.exception.BusLineNotFoundException;
import com.trafiklab.bus.lines.model.JourneyPatternPointOnLine;
import com.trafiklab.bus.lines.model.Line;
import com.trafiklab.bus.lines.model.StopPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service component that services requests from the controller.
 */
@Service
public class BusLinesServiceImpl implements BusLinesService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Comparator<Map.Entry<Integer, List<JourneyPatternPointOnLine>>> byNumberOfStoppingPoints =
            Map.Entry.comparingByValue(Comparator.comparingInt(List::size));

    private final TrafiklabHelper trafiklabHelper;

    @Autowired
    public BusLinesServiceImpl(TrafiklabHelper trafiklabHelper) {
        this.trafiklabHelper = trafiklabHelper;
    }

    /**
     * The results from this method may be cached as it always returns static data.
     *
     * {@inheritDoc}
     * {@link BusLinesService#findLinesWithMostStops(int)}
     */
    @Override
    public List<Line> findLinesWithMostStops(int numberOfBusLines) {
        logger.info("Finding out the top " + numberOfBusLines + " lines with most number of stops.");

        return trafiklabHelper.findJourneyPatternsByLine()
                .entrySet()
                .stream()
                .sorted(byNumberOfStoppingPoints.reversed())
                .limit(numberOfBusLines)
                .map(Map.Entry::getKey)
                .map(this::getLineDetailsFor)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     * {@link BusLinesService#findStopsOnLine(int)}
     */
    @Override
    public List<StopPoint> findStopsOnLine(int lineNumber) {
        logger.info("Finding out stops on line: " + lineNumber);

        Map<Integer, List<JourneyPatternPointOnLine>> journeyPatternsByLine =
                trafiklabHelper.findJourneyPatternsByLine();

        List<JourneyPatternPointOnLine> journeyPatternsOnRequestedLine =
                Optional.ofNullable(journeyPatternsByLine.get(lineNumber))
                        .orElseThrow(() -> new BusLineNotFoundException("No bus line exists for number: " + lineNumber));

        return journeyPatternsOnRequestedLine.stream()
                .map(JourneyPatternPointOnLine::getJourneyPatternPointNumber)
                .map(this::getStopPointDetailsFor)
                .collect(Collectors.toList());
    }

    private Line getLineDetailsFor(int lineNumber) {
        return trafiklabHelper.findAllBusLines()
                .stream()
                .filter(line -> line.getLineNumber() == lineNumber)
                .findFirst()
                .orElse(null);
    }

    private StopPoint getStopPointDetailsFor(int pointNumber) {
        return trafiklabHelper.findAllBusStopPoints()
                .stream()
                .filter(stopPoint -> stopPoint.getStopPointNumber() == pointNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No stop point details could be found for point number: " + pointNumber));
    }

}
