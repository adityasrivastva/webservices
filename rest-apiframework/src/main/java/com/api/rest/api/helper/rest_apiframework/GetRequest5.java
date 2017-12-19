package com.api.rest.api.helper.rest_apiframework;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.api.rest.api.helper.model.RestResponse;

public class GetRequest5 {
	
	/*
	 * @param aggs Step 1:- Create the HTTP Get method 
	 * Step 2:- Create the HTTP Client 
	 * Step 3:- Execute the HTTP methods using the Client 
	 * Step 4:- Catch the response of Execution 
	 * Step 5:- Display the response at console.
	 * 
	 */

	public static void main(String[] args) {
		/*HttpGet get = new HttpGet("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya");
		 * 
		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(get))
		{
			StatusLine status = response.getStatusLine();
			System.out.println(status);
			System.out.println(status.getStatusCode());
			System.out.println(status.getProtocolVersion());
			
			//How to read body from response
			
			ResponseHandler<String> body= new BasicResponseHandler();
			RestResponse restResponse= new RestResponse(response.getStatusLine().getStatusCode(), 
					body.handleResponse(response));
			
			String getBody=body.handleResponse(response);
			System.out.println(getBody);
			
			System.out.println(restResponse);
			

		} catch (Exception e) {
			e.printStackTrace();
		}*/

		
		RestResponse response=RestApiHelper1.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya",null);
		System.out.println(response);
		System.out.println(response.getStatusCode());
		System.out.println(response.getResponseBody());
		String url= "http://localhost:9090/laptop-bag/webapi/api/all";
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		response= RestApiHelper1.performGetRequest(url,headers);
		System.out.println(response);
		
	}


}
