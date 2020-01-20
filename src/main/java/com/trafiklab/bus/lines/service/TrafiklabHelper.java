package com.trafiklab.bus.lines.service;

import com.trafiklab.bus.lines.model.BaseModel;
import com.trafiklab.bus.lines.model.JourneyPatternPointOnLine;
import com.trafiklab.bus.lines.model.Line;
import com.trafiklab.bus.lines.model.StopPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Component that interacts with Trafiklab Api and packages the response for use in the application
 */
@Component
public class TrafiklabHelper {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final RestTemplate restTemplate;

    @Value("${journey.patterns.url:URL_NOT_SET}")
    private String journeyPatternsEndPointUrl;

    @Value("${line.url:URL_NOT_SET}")
    private String lineEndPointUrl;

    @Value("${stop.points.url:URL_NOT_SET}")
    private String stopPointsEndPointUrl;

    @Autowired
    public TrafiklabHelper(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    @Cacheable("allJourneyPatternsForBuses")
    public Map<Integer, List<JourneyPatternPointOnLine>> findJourneyPatternsByLine() {
        List<JourneyPatternPointOnLine> allJourneyPatternsForBuses = findAllJourneyPatternsForBuses();

        return allJourneyPatternsForBuses.parallelStream()
                .collect(Collectors.groupingBy(JourneyPatternPointOnLine::getLineNumber));
    }

    @PostConstruct
    @Cacheable("allBusLines")
    public List<Line> findAllBusLines() {
        BaseModel<Line> apiResponse = invokeTrafiklabApiForBusLines();

        return apiResponse.getResponseData().getResult();
    }

    @PostConstruct
    @Cacheable("allBusStopPoints")
    public List<StopPoint> findAllBusStopPoints() {
        BaseModel<StopPoint> apiResponse = invokeTrafiklabApiForBusStops();

        return apiResponse.getResponseData().getResult()
                .stream()
                .filter(stopPoint -> "BUSTERM".equalsIgnoreCase(stopPoint.getStopAreaTypeCode())) // filter bus stops
                .collect(Collectors.toList());
    }

    private List<JourneyPatternPointOnLine> findAllJourneyPatternsForBuses() {
        BaseModel<JourneyPatternPointOnLine> apiResponse = invokeTrafiklabApiForJourneyPatterns();

        return apiResponse.getResponseData().getResult();
    }

    private BaseModel<JourneyPatternPointOnLine> invokeTrafiklabApiForJourneyPatterns() {
        logger.info("Contacting trafiklab api for 'jour' models..");

        ResponseEntity<BaseModel<JourneyPatternPointOnLine>> journeyPatternsResponse =
                restTemplate.exchange(journeyPatternsEndPointUrl,
                        HttpMethod.GET,
                        requestEntityWithCompressionHeaders(),
                        new ParameterizedTypeReference<>() {
                        });

        return journeyPatternsResponse.getBody();
    }

    private BaseModel<Line> invokeTrafiklabApiForBusLines() {
        logger.info("Contacting trafiklab api for 'line' models..");

        ResponseEntity<BaseModel<Line>> lineResponse =
                restTemplate.exchange(lineEndPointUrl,
                        HttpMethod.GET,
                        requestEntityWithCompressionHeaders(),
                        new ParameterizedTypeReference<>() {
                        });

        return lineResponse.getBody();
    }

    private BaseModel<StopPoint> invokeTrafiklabApiForBusStops() {
        logger.info("Contacting trafiklab api for 'stop' models..");

        ResponseEntity<BaseModel<StopPoint>> stopPointsResponse =
                restTemplate.exchange(stopPointsEndPointUrl,
                        HttpMethod.GET,
                        requestEntityWithCompressionHeaders(),
                        new ParameterizedTypeReference<>() {
                        });

        return stopPointsResponse.getBody();
    }

    private HttpEntity<?> requestEntityWithCompressionHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.ACCEPT_ENCODING, "gzip");
        return new HttpEntity<>(requestHeaders);
    }

    void setJourneyPatternsEndPointUrl(String journeyPatternsEndPointUrl) {
        this.journeyPatternsEndPointUrl = journeyPatternsEndPointUrl;
    }

    void setLineEndPointUrl(String lineEndPointUrl) {
        this.lineEndPointUrl = lineEndPointUrl;
    }

    void setStopPointsEndPointUrl(String stopPointsEndPointUrl) {
        this.stopPointsEndPointUrl = stopPointsEndPointUrl;
    }
}
