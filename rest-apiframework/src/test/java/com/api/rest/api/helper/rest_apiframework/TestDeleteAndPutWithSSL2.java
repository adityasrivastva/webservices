package com.api.rest.api.helper.rest_apiframework;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Test;

import com.api.rest.api.helper.model.ResponseBody;
import com.api.rest.api.helper.model.RestResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TestDeleteAndPutWithSSL2 {
	
	
	@Test
	public void testGetnPost() {
		String id = (int) (1000 * (Math.random())) + "";

		String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" + "\"Feature\": [\"8GB RAM\","
				+ "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";
		
		Map<String, String> headers= new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		RestResponse response = HttpsClientHelper
				.performPostWithSSL("https://localhost:8443/laptop-bag/webapi/sslres/add", jsonBody, 
				ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response
				.getStatusCode());
		response= HttpsClientHelper
				.performGetRequestWithSSL("https://localhost:8443/laptop-bag/webapi/sslres/find/"+id, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response
				.getStatusCode());
		
		GsonBuilder builder= new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();
		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		Assert.assertEquals(id, body.getId());
		Assert.assertEquals("Mac-Pro", body.getLaptopName());
		
		
	}

}
