package com.trafiklab.bus.lines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "StopPointNumber",
        "StopPointName",
        "StopAreaNumber",
        "LocationNorthingCoordinate",
        "LocationEastingCoordinate",
        "ZoneShortName",
        "StopAreaTypeCode",
        "LastModifiedUtcDateTime",
        "ExistsFromDate"
})
public class StopPoint {

    @JsonProperty("StopPointNumber")
    private int stopPointNumber;
    @JsonProperty("StopPointName")
    private String stopPointName;
    @JsonProperty("StopAreaNumber")
    private int stopAreaNumber;
    @JsonProperty("LocationNorthingCoordinate")
    private String locationNorthingCoordinate;
    @JsonProperty("LocationEastingCoordinate")
    private String locationEastingCoordinate;
    @JsonProperty("ZoneShortName")
    private String zoneShortName;
    @JsonProperty("StopAreaTypeCode")
    private String stopAreaTypeCode;
    @JsonProperty("LastModifiedUtcDateTime")
    private String lastModifiedUtcDateTime;
    @JsonProperty("ExistsFromDate")
    private String existsFromDate;

    @JsonProperty("StopPointNumber")
    public int getStopPointNumber() {
        return stopPointNumber;
    }

    @JsonProperty("StopPointNumber")
    public StopPoint setStopPointNumber(int stopPointNumber) {
        this.stopPointNumber = stopPointNumber;
        return this;
    }

    @JsonProperty("StopPointName")
    public String getStopPointName() {
        return stopPointName;
    }

    @JsonProperty("StopPointName")
    public StopPoint setStopPointName(String stopPointName) {
        this.stopPointName = stopPointName;
        return this;
    }

    @JsonProperty("StopAreaNumber")
    public int getStopAreaNumber() {
        return stopAreaNumber;
    }

    @JsonProperty("StopAreaNumber")
    public StopPoint setStopAreaNumber(int stopAreaNumber) {
        this.stopAreaNumber = stopAreaNumber;
        return this;
    }

    @JsonProperty("LocationNorthingCoordinate")
    public String getLocationNorthingCoordinate() {
        return locationNorthingCoordinate;
    }

    @JsonProperty("LocationNorthingCoordinate")
    public void setLocationNorthingCoordinate(String locationNorthingCoordinate) {
        this.locationNorthingCoordinate = locationNorthingCoordinate;
    }

    @JsonProperty("LocationEastingCoordinate")
    public String getLocationEastingCoordinate() {
        return locationEastingCoordinate;
    }

    @JsonProperty("LocationEastingCoordinate")
    public void setLocationEastingCoordinate(String locationEastingCoordinate) {
        this.locationEastingCoordinate = locationEastingCoordinate;
    }

    @JsonProperty("ZoneShortName")
    public String getZoneShortName() {
        return zoneShortName;
    }

    @JsonProperty("ZoneShortName")
    public void setZoneShortName(String zoneShortName) {
        this.zoneShortName = zoneShortName;
    }

    @JsonProperty("StopAreaTypeCode")
    public String getStopAreaTypeCode() {
        return stopAreaTypeCode;
    }

    @JsonProperty("StopAreaTypeCode")
    public StopPoint setStopAreaTypeCode(String stopAreaTypeCode) {
        this.stopAreaTypeCode = stopAreaTypeCode;
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

        StopPoint stopPoint = (StopPoint) o;

        return new EqualsBuilder()
                .append(stopPointNumber, stopPoint.stopPointNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(stopPointNumber)
                .toHashCode();
    }
}
