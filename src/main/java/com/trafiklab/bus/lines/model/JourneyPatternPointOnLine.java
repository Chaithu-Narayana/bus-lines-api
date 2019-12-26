package com.trafiklab.bus.lines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "LineNumber", "DirectionCode", "JourneyPatternPointNumber", "LastModifiedUtcDateTime",
		"ExistsFromDate" })
public class JourneyPatternPointOnLine {

	@JsonProperty("LineNumber")
	private int lineNumber;
	@JsonProperty("DirectionCode")
	private String directionCode;
	@JsonProperty("JourneyPatternPointNumber")
	private int journeyPatternPointNumber;
	@JsonProperty("LastModifiedUtcDateTime")
	private String lastModifiedUtcDateTime;
	@JsonProperty("ExistsFromDate")
	private String existsFromDate;

	@JsonProperty("LineNumber")
	public int getLineNumber() {
		return lineNumber;
	}

	@JsonProperty("LineNumber")
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	@JsonProperty("DirectionCode")
	public String getDirectionCode() {
		return directionCode;
	}

	@JsonProperty("DirectionCode")
	public void setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
	}

	@JsonProperty("JourneyPatternPointNumber")
	public int getJourneyPatternPointNumber() {
		return journeyPatternPointNumber;
	}

	@JsonProperty("JourneyPatternPointNumber")
	public void setJourneyPatternPointNumber(int journeyPatternPointNumber) {
		this.journeyPatternPointNumber = journeyPatternPointNumber;
	}

	@JsonProperty("LastModifiedUtcDateTime")
	public String getLastModifiedUtcDateTime() {
		return lastModifiedUtcDateTime;
	}

	@JsonProperty("LastModifiedUtcDateTime")
	public void setLastModifiedUtcDateTime(String lastModifiedUtcDateTime) {
		this.lastModifiedUtcDateTime = lastModifiedUtcDateTime;
	}

	@JsonProperty("ExistsFromDate")
	public String getExistsFromDate() {
		return existsFromDate;
	}

	@JsonProperty("ExistsFromDate")
	public void setExistsFromDate(String existsFromDate) {
		this.existsFromDate = existsFromDate;
	}
}