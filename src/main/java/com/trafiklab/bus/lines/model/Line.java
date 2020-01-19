package com.trafiklab.bus.lines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "LineNumber",
        "LineDesignation",
        "DefaultTransportMode",
        "DefaultTransportModeCode",
        "LastModifiedUtcDateTime",
        "ExistsFromDate"
})
public class Line {

    @JsonProperty("LineNumber")
    private int lineNumber;
    @JsonProperty("LineDesignation")
    private String lineDesignation;
    @JsonProperty("DefaultTransportMode")
    private String defaultTransportMode;
    @JsonProperty("DefaultTransportModeCode")
    private String defaultTransportModeCode;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;

    @JsonProperty("LineNumber")
    public int getLineNumber() {
        return lineNumber;
    }

    @JsonProperty("LineNumber")
    public Line setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    @JsonProperty("LineDesignation")
    public String getLineDesignation() {
        return lineDesignation;
    }

    @JsonProperty("LineDesignation")
    public Line setLineDesignation(String lineDesignation) {
        this.lineDesignation = lineDesignation;
        return this;
    }

    @JsonProperty("DefaultTransportMode")
    public String getDefaultTransportMode() {
        return defaultTransportMode;
    }

    @JsonProperty("DefaultTransportMode")
    public void setDefaultTransportMode(String defaultTransportMode) {
        this.defaultTransportMode = defaultTransportMode;
    }

    @JsonProperty("DefaultTransportModeCode")
    public String getDefaultTransportModeCode() {
        return defaultTransportModeCode;
    }

    @JsonProperty("DefaultTransportModeCode")
    public void setDefaultTransportModeCode(String defaultTransportModeCode) {
        this.defaultTransportModeCode = defaultTransportModeCode;
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

        Line line = (Line) o;

        return new EqualsBuilder()
                .append(lineNumber, line.lineNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(lineNumber)
                .toHashCode();
    }
}
