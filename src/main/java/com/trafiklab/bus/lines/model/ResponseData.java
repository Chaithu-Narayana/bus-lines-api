package com.trafiklab.bus.lines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

import static java.util.Collections.EMPTY_LIST;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"Version", "Type", "Result"})
public class ResponseData<T> {

    @JsonProperty("Version")
    private String version;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Result")
    private List<T> result = EMPTY_LIST;

    @JsonProperty("Version")
    public String getVersion() {
        return version;
    }

    @JsonProperty("Version")
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty("Type")
    public String getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("Result")
    public List<T> getResult() {
        return result;
    }

    @JsonProperty("Result")
    public void setResult(List<T> result) {
        this.result = result;
    }
}