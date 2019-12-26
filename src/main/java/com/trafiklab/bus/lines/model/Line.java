package com.trafiklab.bus.lines.model;

import java.time.LocalDateTime;

/**
 *
 */
public class Line {
	private int LineNumber;
	private String LineDesignation;
	private String DefaultTransportMode;
	private String DefaultTransportModeCode;
	private LocalDateTime LastModifiedUtcDateTime;
	private LocalDateTime ExistsFromDate;

	// Getter Methods
	public int getLineNumber() {
		return LineNumber;
	}

	public String getLineDesignation() {
		return LineDesignation;
	}

	public String getDefaultTransportMode() {
		return DefaultTransportMode;
	}

	public String getDefaultTransportModeCode() {
		return DefaultTransportModeCode;
	}

	public LocalDateTime getLastModifiedUtcDateTime() {
		return LastModifiedUtcDateTime;
	}

	public LocalDateTime getExistsFromDate() {
		return ExistsFromDate;
	}

}
