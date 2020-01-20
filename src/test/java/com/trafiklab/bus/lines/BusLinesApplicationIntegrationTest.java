package com.trafiklab.bus.lines;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trafiklab.bus.lines.service.CacheRefresher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BusLinesApplicationIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private CacheRefresher cacheRefresher;

    @org.springframework.beans.factory.annotation.Value("${journey.patterns.url:URL_NOT_SET}")
    private String journeyPatternsEndPointUrl;

    @org.springframework.beans.factory.annotation.Value("${line.url:URL_NOT_SET}")
    private String lineEndPointUrl;

    @org.springframework.beans.factory.annotation.Value("${stop.points.url:URL_NOT_SET}")
    private String stopPointsEndPointUrl;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @AfterEach
    void tearDown() {
        cacheRefresher.refreshAllCaches();
    }

    @Test
    void findLinesWithMostStops() throws URISyntaxException, IOException {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(journeyPatternsEndPointUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Files.readString(Paths.get("src/test/resources/bus-jour-sample.json"), StandardCharsets.UTF_8))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(lineEndPointUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Files.readString(Paths.get("src/test/resources/bus-line-sample.json"), StandardCharsets.UTF_8))
                );

        this.webClient.get().uri("/bus-lines/top-10-lines")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody()
                .jsonPath("$.status").isEqualTo("Success")
                .jsonPath("$.resultData").isNotEmpty();

        mockServer.verify();
    }

    @Test
    void findStopsOnLine() throws URISyntaxException, IOException {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(journeyPatternsEndPointUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Files.readString(Paths.get("src/test/resources/bus-jour-sample.json"), StandardCharsets.UTF_8))
                );

        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(stopPointsEndPointUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Files.readString(Paths.get("src/test/resources/bus-stop-sample.json"), StandardCharsets.UTF_8))
                );

        this.webClient.get().uri("/bus-lines/stops-on-line/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody()
                .jsonPath("$.status").isEqualTo("Success")
                .jsonPath("$.resultData").isNotEmpty();

        mockServer.verify();
    }

    @Test
    void findLinesWithMostStops_serverUnavailable() throws URISyntaxException {
        mockServer.expect(ExpectedCount.once(),
                requestTo(new URI(journeyPatternsEndPointUrl)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));

        this.webClient.get().uri("/bus-lines/top-10-lines")
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().jsonPath("$.status").isEqualTo("Failure")
                .jsonPath("$.errorMessage").isNotEmpty();

        mockServer.verify();
    }
}
