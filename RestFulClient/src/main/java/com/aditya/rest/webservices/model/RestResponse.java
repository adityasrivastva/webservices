package com.aditya.rest.webservices.model;

public class RestResponse {
	
	private int statusCode;
	private String responseBody;
	
	
	public RestResponse(int statusCode, String responseBody) {
		super();
		this.statusCode = statusCode;
		this.responseBody = responseBody;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public String getResponseBody() {
		return responseBody;
	}
	@Override
	public String toString() {
		
		return String.format("Status Code : %1s Body : %2s", this.statusCode, this.responseBody);
		//return "RestResponse [statusCode=" + statusCode + ", responseBody=" + responseBody + "]";
	}
	
	
	

}
