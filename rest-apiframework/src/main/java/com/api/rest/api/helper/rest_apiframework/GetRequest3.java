package com.api.rest.api.helper.rest_apiframework;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class GetRequest3 {
	
	/*
	 * @param aggs Step 1:- Create the HTTP Get method 
	 * Step 2:- Create the HTTP Client 
	 * Step 3:- Execute the HTTP methods using the Client 
	 * Step 4:- Catch the response of Execution 
	 * Step 5:- Display the response at console.
	 * 
	 */

	public static void main(String[] args) {
		HttpGet get = new HttpGet("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya");

		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(get))
		{

			StatusLine status = response.getStatusLine();
			System.out.println(status);
			System.out.println(status.getStatusCode());
			System.out.println(status.getProtocolVersion());
			
			//How to read body from response
			
			ResponseHandler<String> body= new BasicResponseHandler();
			String getBody=body.handleResponse(response);
			System.out.println(getBody);
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
