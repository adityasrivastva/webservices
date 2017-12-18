package com.aditya.rest.webservices.helper;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.aditya.rest.webservices.model.RestResponse;

public class GetRequest {
	/*
	 * @param aggs
	 * Step 1:- Create the HTTP Get method
	 * Step 2:- Create the HTTP Client
	 * Step 3:- Execute the HTTP methods using the Client
	 * Step 4:- Catch the response of Execution
	 * Step 5:- Display the response at console.
	 * 
	 * */

	public static void main(String[] args) {
		// Method 1
		/*		
  		try {
			HttpGet get= new HttpGet("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya");
			CloseableHttpClient client= HttpClientBuilder.create().build();
			CloseableHttpResponse response=client.execute(get);
			StatusLine status=response.getStatusLine();
			System.out.println(status);
			System.out.println(status.getStatusCode());
			System.out.println(status.getProtocolVersion());
			client.close();
			response.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		//Method 2 Auto-closeable Concept
		/*
			HttpGet get= new HttpGet("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya");
		
		try(CloseableHttpClient client= HttpClientBuilder.create().build();
				CloseableHttpResponse response=client.execute(get)) {
			
			StatusLine status=response.getStatusLine();
			System.out.println(status);
			System.out.println(status.getStatusCode());
			System.out.println(status.getProtocolVersion());
			
			
			//Read Body from Response of Request.
			ResponseHandler<String>  body= new BasicResponseHandler();
			String getBody=body.handleResponse(response);
			System.out.println(getBody);
			
			RestResponse restResponse= new RestResponse(response.getStatusLine().getStatusCode(),
					body.handleResponse(response));
			System.out.println(restResponse);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		
	RestResponse response=	RestApiHelper.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya",null);
	System.out.println(response);
	}
}
