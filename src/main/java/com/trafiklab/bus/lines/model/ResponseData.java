package com.trafiklab.bus.lines.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({ "Version", "Type", "Result" })
public class ResponseData {

	@JsonProperty("Version")
	private String version;
	@JsonProperty("Type")
	private String type;
	@JsonProperty("Result")
	private List<JourneyPatternPointOnLine> result = null;

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
	public List<JourneyPatternPointOnLine> getResult() {
		return result;
	}

	@JsonProperty("Result")
	public void setResult(List<JourneyPatternPointOnLine> result) {
		this.result = result;
	}

}