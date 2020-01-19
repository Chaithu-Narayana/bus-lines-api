package com.trafiklab.bus.lines.service;

import com.trafiklab.bus.lines.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrafiklabHelperTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private TrafiklabHelper trafiklabHelper;

    @Test
    void findJourneyPatternsByLine() {
        String journeyPatternsUrl = "https://www.dummy.com/journey-patterns&mode=bus";
        trafiklabHelper.setJourneyPatternsEndPointUrl(journeyPatternsUrl);

        JourneyPatternPointOnLine journeyPatternPointOne = createJourneyPatternPoint(1, 10006);
        JourneyPatternPointOnLine journeyPatternPointTwo = createJourneyPatternPoint(2, 10012);
        BaseModel<JourneyPatternPointOnLine> journeyPatternPointOnLineBaseModel = createBaseModelContaining(journeyPatternPointOne, journeyPatternPointTwo);

        when(restTemplate.exchange(eq(journeyPatternsUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<BaseModel<JourneyPatternPointOnLine>>() {
        }))).thenReturn(new ResponseEntity<>(journeyPatternPointOnLineBaseModel, HttpStatus.OK));

        Map<Integer, List<JourneyPatternPointOnLine>> journeyPatternsByLine = trafiklabHelper.findJourneyPatternsByLine();

        assertThat("journeyPatternsByLine: ", journeyPatternsByLine, hasEntry(1, List.of(journeyPatternPointOne)));
        assertThat("journeyPatternsByLine: ", journeyPatternsByLine, hasEntry(2, List.of(journeyPatternPointTwo)));

        verify(restTemplate).exchange(eq(journeyPatternsUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<BaseModel<JourneyPatternPointOnLine>>() {
        }));
    }

    @Test
    void findAllBusLines() {
        String linesUrl = "https://www.dummy.com/lines&mode=bus";
        trafiklabHelper.setLineEndPointUrl(linesUrl);

        Line lineOne = createLine(1);
        Line lineTwo = createLine(2);
        BaseModel<Line> linesBaseModel = createBaseModelContaining(lineOne, lineTwo);

        when(restTemplate.exchange(eq(linesUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<BaseModel<Line>>() {
        }))).thenReturn(new ResponseEntity<>(linesBaseModel, HttpStatus.OK));

        List<Line> allBusLines = trafiklabHelper.findAllBusLines();

        assertThat("allBusLines: ", allBusLines, hasSize(2));
        assertThat("allBusLines: ", allBusLines, hasItems(lineOne, lineTwo));

        verify(restTemplate).exchange(eq(linesUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<BaseModel<Line>>() {
        }));
    }

    @Test
    void findAllBusStopPoints() {
        String stopPointsUrl = "https://www.dummy.com/stop-points";
        trafiklabHelper.setStopPointsEndPointUrl(stopPointsUrl);

        StopPoint stopPointOne = createStopPoint(10006, "Arbetargatan", "BUSTERM");
        StopPoint stopPointTwo = createStopPoint(10012, "Frihamnsporten", "METROSTN");
        StopPoint stopPointThree = createStopPoint(10024, "Stadshagsplan", "BUSTERM");
        BaseModel<StopPoint> stopPointsBaseModel = createBaseModelContaining(stopPointOne, stopPointTwo, stopPointThree);

        when(restTemplate.exchange(eq(stopPointsUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<BaseModel<StopPoint>>() {
        }))).thenReturn(new ResponseEntity<>(stopPointsBaseModel, HttpStatus.OK));

        List<StopPoint> allBusStopPoints = trafiklabHelper.findAllBusStopPoints();

        assertThat("allBusStopPoints: ", allBusStopPoints, hasSize(2));
        assertThat("allBusStopPoints: ", allBusStopPoints, hasItems(stopPointOne, stopPointThree));

        verify(restTemplate).exchange(eq(stopPointsUrl), eq(HttpMethod.GET), any(HttpEntity.class), eq(new ParameterizedTypeReference<BaseModel<StopPoint>>() {
        }));
    }

    @SafeVarargs
    private <T> BaseModel<T> createBaseModelContaining(T... results) {
        BaseModel<T> lineBaseModel = new BaseModel<>();

        ResponseData<T> responseData = new ResponseData<>();
        responseData.setResult(List.of(results));
        lineBaseModel.setResponseData(responseData);

        return lineBaseModel;
    }

    private JourneyPatternPointOnLine createJourneyPatternPoint(int lineNumber, int stopPointNumber) {
        return new JourneyPatternPointOnLine()
                .setLineNumber(lineNumber)
                .setDirectionCode("1")
                .setJourneyPatternPointNumber(stopPointNumber);
    }

    private Line createLine(int lineNumber) {
        return new Line()
                .setLineNumber(lineNumber)
                .setLineDesignation(String.valueOf(lineNumber));
    }

    private StopPoint createStopPoint(int number, String name, String usage) {
        return new StopPoint()
                .setStopPointNumber(number)
                .setStopPointName(name)
                .setStopAreaTypeCode(usage);
    }
}

