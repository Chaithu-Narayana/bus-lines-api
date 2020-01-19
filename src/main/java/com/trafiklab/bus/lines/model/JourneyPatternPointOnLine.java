package com.trafiklab.bus.lines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	public JourneyPatternPointOnLine setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
		return this;
	}

	@JsonProperty("DirectionCode")
	public String getDirectionCode() {
		return directionCode;
	}

	@JsonProperty("DirectionCode")
	public JourneyPatternPointOnLine setDirectionCode(String directionCode) {
		this.directionCode = directionCode;
		return this;
	}

	@JsonProperty("JourneyPatternPointNumber")
	public int getJourneyPatternPointNumber() {
		return journeyPatternPointNumber;
	}

	@JsonProperty("JourneyPatternPointNumber")
	public JourneyPatternPointOnLine setJourneyPatternPointNumber(int journeyPatternPointNumber) {
		this.journeyPatternPointNumber = journeyPatternPointNumber;
		return this;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		JourneyPatternPointOnLine that = (JourneyPatternPointOnLine) o;

		return new EqualsBuilder()
				.append(lineNumber, that.lineNumber)
				.append(journeyPatternPointNumber, that.journeyPatternPointNumber)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(lineNumber)
				.append(journeyPatternPointNumber)
				.toHashCode();
	}
}