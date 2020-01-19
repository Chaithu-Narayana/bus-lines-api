package com.trafiklab.bus.lines.controller.advice;

import com.trafiklab.bus.lines.exception.BusLineNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BusLinesExceptionHandler
        extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final BusLinesErrorResponse handleBadRequests(IllegalArgumentException ex) {
        return errorResponse("Invalid client request: ", ex);
    }

    @ExceptionHandler(BusLineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final BusLinesErrorResponse handleBusLineNotFoundException(BusLineNotFoundException ex) {
        return errorResponse("Bus line doesn't exist: ", ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final BusLinesErrorResponse handleAllExceptions(Exception ex) {
        return errorResponse("An unexpected error occurred: ", ex);
    }

    private BusLinesErrorResponse errorResponse(String errorMessage, Exception ex) {
        logger.error(errorMessage, ex);
        return new BusLinesErrorResponse(ex.getMessage());
    }

    static class BusLinesErrorResponse {

        private String status = "Failure";
        private LocalDateTime timestamp = LocalDateTime.now().withNano(0);
        private String errorMessage;

        public BusLinesErrorResponse(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getStatus() {
            return status;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
