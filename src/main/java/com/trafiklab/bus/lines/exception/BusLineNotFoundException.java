package com.trafiklab.bus.lines.exception;

public class BusLineNotFoundException extends RuntimeException {

    public BusLineNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
