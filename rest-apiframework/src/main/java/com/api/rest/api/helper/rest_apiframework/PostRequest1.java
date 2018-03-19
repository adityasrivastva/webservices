package com.api.rest.api.helper.rest_apiframework;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.api.rest.api.helper.model.RestResponse;

public class PostRequest1 {
	
	/*
	 * @param aggs Step 1:- Create the HTTP Get/ Http POST method 
	 * Step 2:- Create the HTTP Client 
	 * Step 3:- Execute the HTTP methods using the Client 
	 * Step 4:- Catch the response of Execution 
	 * Step 5:- Display the response at console.
	 * 
	 */

	public static void main(String[] args) throws IOException {
		String jsonBody= "{" +
				 "\"BrandName\": \"Apple\","+
				 "\"Features\": {" + 
				  "\"Feature\": [\"8GB RAM\","+
				  "\"1TB Hard Drive\"]" +
				 "},"+
				 "\"Id\":" + (int)(1000 * (Math.random()))+","+
				 "\"LaptopName\": \"Mac-Pro\"" +
				"}";
		HttpPost post = new HttpPost("http://localhost:9090/laptop-bag/webapi/api/add");
		CloseableHttpResponse response=null;
		 
		try(CloseableHttpClient client= HttpClientBuilder.create().build()) {
			post.addHeader("Content-Type", "application/json");
			post.addHeader("Accept", "application/json");
			StringEntity data=new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
			post.setEntity(data);
			response=client.execute(post);
			System.out.println(response);
			ResponseHandler<String> handler= new BasicResponseHandler();
			
			RestResponse restResponse= new RestResponse(response.getStatusLine().getStatusCode(), 
					handler.handleResponse(response) );
			System.out.println(restResponse.toString());
			client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (response!=null) {
				response.close();
			}
		}

		/*
		RestResponse response=RestApiHelper1.performGetRequest("http://localhost:9090/laptop-bag/webapi/api/ping/Aditya",null);
		System.out.println(response);
		System.out.println(response.getStatusCode());
		System.out.println(response.getResponseBody());
		String url= "http://localhost:9090/laptop-bag/webapi/api/all";
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		response= RestApiHelper1.performGetRequest(url,headers);
		System.out.println(response);
		*/
	}


}
