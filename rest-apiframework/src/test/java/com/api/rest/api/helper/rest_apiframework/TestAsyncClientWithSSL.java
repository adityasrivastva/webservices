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

public class TestAsyncClientWithSSL {
	
	
	/*
	 * Step 1: Post the data, verify the 200 OK Step 2: Get the data using GET end
	 * points, verify the id also verify the status code. Step 3: Update the data in
	 * the container using PUT end-points, verify the status code. Step 4: Repeat
	 * Step 2 Step 5: Delete the data using DELETE end-point, verify the status code
	 * Step 6: Repeat Step 2
	 *
	 * 
	 */

	@Test
	public void testDeleteAndPut() {
		String id = (int) (1000 * (Math.random())) + "";

		String jsonBody = "{" + "\"BrandName\": \"Apple\"," + "\"Features\": {" + "\"Feature\": [\"8GB RAM\","
				+ "\"1TB Hard Drive\"]" + "}," + "\"Id\":" + id + "," + "\"LaptopName\": \"Mac-Pro\"" + "}";
		
		String jsonPutBody = "{" +
				 "\"BrandName\": \"Apple\","+
				 "\"Features\": {" + 
				  "\"Feature\": [\"8GB RAM\","+
				  "\"1TB Hard Drive\"," +
				  "\"16 GB of SSD\"," +
				  "\"14 .0 inch of display\"]" +
				 "},"+
				 "\"Id\":" + id+","+
				 "\"LaptopName\": \"Mac-Pro\"" +
				"}";

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");
		RestResponse response = HttpsAsyncClientHelper
				.performPostSSLAsync("https://localhost:8443/laptop-bag/webapi/sslres/add", jsonBody, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		response = HttpsAsyncClientHelper
				.performGetSSLRequestAsync("https://localhost:8443/laptop-bag/webapi/sslres/find/" + id, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response= HttpsAsyncClientHelper
				.performPutSSLAsync("https://localhost:8443/laptop-bag/webapi/sslres/update", jsonPutBody, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode() );
		
		response = HttpsAsyncClientHelper
				.performGetSSLRequestAsync("https://localhost:8443/laptop-bag/webapi/sslres/find/" + id, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
			
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();
		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		Assert.assertEquals(id, body.getId());
		Assert.assertEquals("Mac-Pro", body.getLaptopName());
		
		response = HttpsAsyncClientHelper
				.performDeleteSSLAsync("https://localhost:8443/laptop-bag/webapi/sslres/delete/" + id, null);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response = HttpsAsyncClientHelper
				.performGetSSLRequestAsync("https://localhost:8443/laptop-bag/webapi/sslres/find/" + id, headers);
		Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());
		
		

	}
}
