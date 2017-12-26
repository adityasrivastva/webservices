package com.api.rest.api.helper.rest_apiframework;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.api.rest.api.helper.model.RestResponse;

public class PostRequest4 {
	
	/*
	 * @param aggs Step 1:- Create the HTTP Get/ Http POST method 
	 * Step 2:- Create the HTTP Client 
	 * Step 3:- Execute the HTTP methods using the Client 
	 * Step 4:- Catch the response of Execution 
	 * Step 5:- Display the response at console.
	 * 
	 */

	public static void main(String[] args) {
		String jsonBody= "{" +
				 "\"BrandName\": \"Dell\","+
				 "\"Features\": {" + 
				  "\"Feature\": [\"8GB RAM\","+
				  "\"1TB Hard Drive\"]" +
				 "},"+
				 "\"Id\":" + (int)(1000 * (Math.random()))+","+
				 "\"LaptopName\": \"Latitude\"" +
				"}";
		HttpPost post = new HttpPost("http://localhost:9090/laptop-bag/webapi/api/add");
		 
		try(CloseableHttpClient client= HttpClientBuilder.create().build()) {
			post.addHeader("Content-Type", "application/json");
			post.addHeader("Accept", "application/json");
			//StringEntity data=new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
			//post.setEntity(data);
			File file= new File("TestDataFile");
			FileEntity data= new FileEntity(file, ContentType.APPLICATION_JSON);
			post.setEntity(data);
			CloseableHttpResponse response=client.execute(post);
			ResponseHandler<String> handler= new BasicResponseHandler();
			
			RestResponse restResponse= new RestResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response) );
			System.out.println(restResponse.toString());
			client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
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
		
		
		//For FileEntity
		Map<String, String> headers= new LinkedHashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		RestResponse response=RestApiHelper.performPostRequest("http://localhost:9090/laptop-bag/webapi/api/add",jsonBody,ContentType.APPLICATION_JSON, headers);
		System.out.println(response);
		
		//For StringEntity
		/*Map<String, String> headers= new LinkedHashMap<>();
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");*/
		File file= new File("TestDataFile");
		
		
		RestResponse response1=RestApiHelper.performPostRequest("http://localhost:9090/laptop-bag/webapi/api/add",file,ContentType.APPLICATION_JSON, headers);
		System.out.println(response1);
		
		
		
		
	}
	
	


}
