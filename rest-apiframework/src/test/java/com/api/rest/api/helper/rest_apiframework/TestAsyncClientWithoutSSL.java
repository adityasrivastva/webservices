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

public class TestAsyncClientWithoutSSL {
	
	
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
				.performPostAsync("http://localhost:9090/laptop-bag/webapi/api/add", jsonBody, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		response = HttpsAsyncClientHelper
				.performGetRequestAsync("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response= HttpsAsyncClientHelper
				.performPutAsync("http://localhost:9090/laptop-bag/webapi/api/update", jsonPutBody, ContentType.APPLICATION_JSON, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode() );
		
		response = HttpsAsyncClientHelper
				.performGetRequestAsync("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
			
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.serializeNulls().setPrettyPrinting().create();
		ResponseBody body = gson.fromJson(response.getResponseBody(), ResponseBody.class);
		Assert.assertEquals(id, body.getId());
		Assert.assertEquals("Mac-Pro", body.getLaptopName());
		
		response = HttpsAsyncClientHelper
				.performDeleteAsync("http://localhost:9090/laptop-bag/webapi/api/delete/" + id, null);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		
		response = HttpsAsyncClientHelper
				.performGetRequestAsync("http://localhost:9090/laptop-bag/webapi/api/find/" + id, headers);
		Assert.assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusCode());
		
		

	}
}
