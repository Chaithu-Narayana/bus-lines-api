package com.trafiklab.bus.lines.controller;

import com.trafiklab.bus.lines.service.BusLinesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BusLinesControllerTest {

    @Mock
    private BusLinesService busLinesService;

    @InjectMocks
    private BusLinesController busLinesController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(busLinesController)
                .build();
    }

    @Test
    void findLinesWithMostStops() throws Exception {
        when(busLinesService.findLinesWithMostStops(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bus-lines/top-10-lines"))
                .andExpect(status().isOk());

        verify(busLinesService).findLinesWithMostStops(10);
    }

    @Test
    void findStopsOnLine() throws Exception {
        when(busLinesService.findStopsOnLine(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/bus-lines/stops-on-line/1"))
                .andExpect(status().isOk());

        verify(busLinesService).findStopsOnLine(1);
    }

    @Test
    void findStopsOnLine_invalidLineNumber() {
        Exception exception = assertThrows(NestedServletException.class,
                () -> mockMvc.perform(get("/bus-lines/stops-on-line/-235")));

        assertThat("exception message: ", exception.getMessage(), containsString("line number should be a positive integer"));
        verify(busLinesService, never()).findStopsOnLine(-235);
    }
}