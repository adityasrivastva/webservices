package com.api.rest.api.helper.rest_apiframework;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import com.api.rest.api.helper.model.ResponseBody;
import com.api.rest.api.helper.model.RestResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestPostMethod {

	@Test
	public void testPost() {

		String id = (int) (1000 * (Math.random())) + "";

		String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" + "\"Feature\": [\"8GB RAM\","
				+ "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";

		Map<String, String> headers = new LinkedHashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		RestResponse response = RestApiHelper.performPostRequest("http://localhost:9090/laptop-bag/webapi/api/add",
				jsonBody, ContentType.APPLICATION_JSON, headers);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		response = RestApiHelper.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();

		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		Assert.assertEquals("Apple", body.BrandName);
		Assert.assertEquals("Mac-Pro", body.LaptopName);
		Assert.assertEquals(id, body.Id);
	}

	@Test
	public void testPostWithXml() throws JsonParseException, JsonMappingException, IOException {

		String id = (int) (1000 * (Math.random())) + "";

		String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" + "\"Feature\": [\"8GB RAM\","
				+ "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";

		Map<String, String> headers = new LinkedHashMap<>();

		/*
		 * Remove headers Accept to get Data into xml format
		 */
		// headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		RestResponse response = RestApiHelper.performPostRequest("http://localhost:9090/laptop-bag/webapi/api/add",
				jsonBody, ContentType.APPLICATION_JSON, headers);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		response = RestApiHelper.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);

		/**
		 * Step 1. Create object of class XmlMapper 
		 * Step 2. call configure method to handle list while  de-serialize of Json.
		 * Step 3. And use this object for the deserialization process.
		 */

		XmlMapper xml = new XmlMapper();
		xml.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

		ResponseBody body = xml.readValue(response.getResponseBody(), ResponseBody.class);
		System.out.println(body);
		
		Assert.assertEquals("Apple", body.BrandName);
		Assert.assertEquals("Mac-Pro", body.LaptopName);
		Assert.assertEquals(id, body.Id);

		
	}

}
