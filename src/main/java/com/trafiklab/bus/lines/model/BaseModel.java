package com.trafiklab.bus.lines.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "StatusCode", "Message", "ExecutionTime", "ResponseData" })
public class BaseModel {

	@JsonProperty("StatusCode")
	private int statusCode;
	@JsonProperty("Message")
	private Object message;
	@JsonProperty("ExecutionTime")
	private int executionTime;
	@JsonProperty("ResponseData")
	private ResponseData responseData;

	@JsonProperty("StatusCode")
	public int getStatusCode() {
		return statusCode;
	}

	@JsonProperty("StatusCode")
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("Message")
	public Object getMessage() {
		return message;
	}

	@JsonProperty("Message")
	public void setMessage(Object message) {
		this.message = message;
	}

	@JsonProperty("ExecutionTime")
	public int getExecutionTime() {
		return executionTime;
	}

	@JsonProperty("ExecutionTime")
	public void setExecutionTime(int executionTime) {
		this.executionTime = executionTime;
	}

	@JsonProperty("ResponseData")
	public ResponseData getResponseData() {
		return responseData;
	}

	@JsonProperty("ResponseData")
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

}