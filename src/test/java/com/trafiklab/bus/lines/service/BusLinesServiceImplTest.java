package com.trafiklab.bus.lines.service;

import com.trafiklab.bus.lines.exception.BusLineNotFoundException;
import com.trafiklab.bus.lines.model.JourneyPatternPointOnLine;
import com.trafiklab.bus.lines.model.Line;
import com.trafiklab.bus.lines.model.StopPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BusLinesServiceImplTest {

    @Mock
    TrafiklabHelper trafiklabHelper;

    @InjectMocks
    BusLinesServiceImpl busLinesService;

    @BeforeEach
    void setUp() {
        when(trafiklabHelper.findJourneyPatternsByLine()).thenReturn(journeyPatternsByLine());
    }

    @Test
    @DisplayName("linesWithMostStops")
    void findLinesWithMostStops() {
        when(trafiklabHelper.findAllBusLines()).thenReturn(allBusLines());

        List<Line> lineWithMostStops = busLinesService.findLinesWithMostStops(1);

        assertThat("lines with max stops: ", lineWithMostStops, hasSize(1));
        assertThat("line number with max stops: ", lineWithMostStops.get(0), hasProperty("lineNumber", is(2)));
    }

    @Test
    @DisplayName("stopsOnGivenLine")
    void findStopsOnLine() {
        when(trafiklabHelper.findAllBusStopPoints()).thenReturn(allBusStopPoints());

        List<StopPoint> stopsOnLine = busLinesService.findStopsOnLine(1);

        assertThat("stops on given line: ", stopsOnLine, hasSize(3));
        assertThat("stop name on given line: ", stopsOnLine.get(0), hasProperty("stopPointName", is("Arbetargatan")));
    }

    @Test
    @DisplayName("stopsOnGivenLine - line doesn't exist")
    void findStopsOnLine_nonExistentLineNumber() {
        String expectedMessage = "No bus line exists for number: 3";
        BusLineNotFoundException exception =
                assertThrows(BusLineNotFoundException.class, () -> busLinesService.findStopsOnLine(3));

        assertThat("error message: ", exception.getMessage(), is(expectedMessage));
    }

    private Map<Integer, List<JourneyPatternPointOnLine>> journeyPatternsByLine() {
        List<JourneyPatternPointOnLine> journeyPatternPointsForLineOne = List.of(
                createJourneyPatternPoint(1, 10006),
                createJourneyPatternPoint(1, 10012),
                createJourneyPatternPoint(1, 10018));

        List<JourneyPatternPointOnLine> journeyPatternPointsForLineTwo = List.of(
                createJourneyPatternPoint(2, 10005),
                createJourneyPatternPoint(2, 10010),
                createJourneyPatternPoint(2, 10015),
                createJourneyPatternPoint(2, 10020));

        return Map.ofEntries(
                new AbstractMap.SimpleEntry<>(1, journeyPatternPointsForLineOne),
                new AbstractMap.SimpleEntry<>(2, journeyPatternPointsForLineTwo)
        );
    }

    private JourneyPatternPointOnLine createJourneyPatternPoint(int lineNumber, int stopPointNumber) {
        return new JourneyPatternPointOnLine()
                .setLineNumber(lineNumber)
                .setDirectionCode("1")
                .setJourneyPatternPointNumber(stopPointNumber);
    }

    private List<Line> allBusLines() {
        return List.of(createLine(1),
                createLine(2));
    }

    private Line createLine(int lineNumber) {
        return new Line()
                .setLineNumber(lineNumber)
                .setLineDesignation(String.valueOf(lineNumber));
    }

    private List<StopPoint> allBusStopPoints() {
        return List.of(
                createStopPoint(10006, "Arbetargatan"),
                createStopPoint(10012, "Frihamnsporten"),
                createStopPoint(10018, "Stadshagsplan"));
    }

    private StopPoint createStopPoint(int number, String name) {
        return new StopPoint()
                .setStopPointNumber(number)
                .setStopPointName(name);
    }
}