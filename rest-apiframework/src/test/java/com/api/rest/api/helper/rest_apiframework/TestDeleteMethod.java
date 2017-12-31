package com.api.rest.api.helper.rest_apiframework;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.api.rest.api.helper.model.RestResponse;

public class TestDeleteMethod {
	
	/*
	 * Step 1. I will Post the data and validate status code 200 ok.
	 * Step 2. Call delete end point to delete the above post data, validate status coke 200 ok.
	 * Step 3. Call the get end point, it should reyurn 404 not found
	 * Step 4. Call delete end point with same id, expected will be 404 not found.
	 * */
	
	@Test
	public void testDelete() {

		String id = (int) (1000 * (Math.random())) + "";

		String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" + "\"Feature\": [\"8GB RAM\","
				+ "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";

		Map<String, String> headers = new LinkedHashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		RestResponse response = RestApiHelper.performPostRequest("http://localhost:9090/laptop-bag/webapi/api/add",
				jsonBody, ContentType.APPLICATION_JSON, headers);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		
		//Step 2
		response= RestApiHelper.performDeleteRequest("http://localhost:9090/laptop-bag/webapi/api/delete/"+id, null);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		//Step 3
		response = RestApiHelper.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);
		assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());
		
		
		
	}

}
