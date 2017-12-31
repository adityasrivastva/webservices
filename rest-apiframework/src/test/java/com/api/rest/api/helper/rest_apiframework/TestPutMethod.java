package com.api.rest.api.helper.rest_apiframework;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Test;

import com.api.rest.api.helper.model.ResponseBody;
import com.api.rest.api.helper.model.RestResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestPutMethod {
	/*
	 * Step 1. I will Post the data and validate status code 200 ok. Step 2. Call
	 * put method which will update the content Step 3. Call the get end point to
	 * validate the output, content validation
	 */

	@Test
	public void testPut() throws JsonParseException, JsonMappingException, IOException {

		String id = (int) (1000 * (Math.random())) + "";

		String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" + "\"Feature\": [\"8GB RAM\","
				+ "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";

		String xmlBody = "<Laptop>"

				+ "<BrandName>Dell</BrandName>" + "<Features>" + "<Feature>8GB RAM</Feature>"
				+ "<Feature>5TB Hard Drive</Feature>" + "<Feature>4 GB NVIDIA</Feature>" + "</Features>" + "<Id>" + id
				+ "</Id>" + "<LaptopName>Latitude</LaptopName>" + "</Laptop>";

		Map<String, String> headers = new LinkedHashMap<String, String>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");

		RestResponse response = RestApiHelper.performPostRequest("http://localhost:9090/laptop-bag/webapi/api/add",
				jsonBody, ContentType.APPLICATION_JSON, headers);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		headers.clear();
		headers.put("Accept", "application/xml");
		headers.put("Content-Type", "application/xml");
		response = RestApiHelper.performPutRequest("http://localhost:9090/laptop-bag/webapi/api/update", xmlBody,
				ContentType.APPLICATION_XML, headers);
		assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		response = RestApiHelper.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);

		XmlMapper mapper = new XmlMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		ResponseBody body = mapper.readValue(response.getResponseBody(), ResponseBody.class);
		assertEquals("Latitude", body.LaptopName);

	}

	@Test
	public void testPutNotFound() throws JsonParseException, JsonMappingException, IOException {

		/*
		 * String id = (int) (1000 * (Math.random())) + "";
		 * 
		 * String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" +
		 * "\"Feature\": [\"8GB RAM\"," + "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id
		 * + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";
		 */
		String xmlBody = "<Laptop>"

				+ "<BrandName>Dell</BrandName>" + "<Features>" + "<Feature>8GB RAM</Feature>"
				+ "<Feature>5TB Hard Drive</Feature>" + "<Feature>4 GB NVIDIA</Feature>" + "</Features>"
				+ "<Id>11111</Id>" + "<LaptopName>Latitude</LaptopName>" + "</Laptop>";

		Map<String, String> headers = new LinkedHashMap<String, String>();

		headers.put("Accept", "application/xml");
		headers.put("Content-Type", "application/xml");
		RestResponse response = RestApiHelper.performPutRequest("http://localhost:9090/laptop-bag/webapi/api/update",
				xmlBody, ContentType.APPLICATION_XML, headers);
		assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());

		/*
		 * response= RestApiHelper.performGetRequest(
		 * "http://localhost:9090/laptop-bag/webapi/api/find/"+id, headers);
		 * 
		 * XmlMapper mapper= new XmlMapper();
		 * mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		 * ResponseBody body=mapper.readValue(response.getResponseBody(),
		 * ResponseBody.class); assertEquals("Latitude", body.LaptopName);
		 */

	}
}
