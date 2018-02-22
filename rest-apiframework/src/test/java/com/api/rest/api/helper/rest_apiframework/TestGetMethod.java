package com.api.rest.api.helper.rest_apiframework;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.api.rest.api.helper.model.ResponseBody;
import com.api.rest.api.helper.model.RestResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class TestGetMethod {
	
	@Test
	public void testGetPingAlive() {
		
		String url= "http://localhost:9090/laptop-bag/webapi/api/ping/Aditya";
		
		RestResponse response= RestApiHelper.performGetRequest(url,null);
		
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		Assert.assertEquals("Hi! Aditya", response.getResponseBody());
		System.out.println(response);
	}
	
	@Test
	public void testGetAll() {
		String url= "http://localhost:9090/laptop-bag/webapi/api/all";
		
		//Adding Headers to the Request
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		
		RestResponse response= RestApiHelper.performGetRequest(url,headers);
		System.out.println(response);
		Assert.assertTrue("Expected Status Code not preset",(HttpStatus.SC_OK==response.getStatusCode()) || (HttpStatus.SC_NO_CONTENT==response.getStatusCode()));
	}
	
	@Test
	public void testGetFindWithId() {
		String url= "http://localhost:9090/laptop-bag/webapi/api/find/132";
		
		//Adding Headers to the Request
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		
		RestResponse response= RestApiHelper.performGetRequest(url,headers);
		Assert.assertTrue("Expected Status Code not preset",(HttpStatus.SC_OK==response.getStatusCode()) || (HttpStatus.SC_NOT_FOUND==response.getStatusCode()));
		//System.out.println(response.getResponseBody());
		
		
		/**
		 * Step 1 Use the GSON Builder class to get the instance of GSON class
		 * Step 2 Use the Gson object to deserialize the json
		 * 
		 * */
		
		GsonBuilder builder= new GsonBuilder();
		Gson gson=builder.serializeNulls().setPrettyPrinting().create();
		
		ResponseBody body= gson.fromJson(response.getResponseBody(), ResponseBody.class);
		System.out.println(body);
		Assert.assertEquals("Apple Phone", body.BrandName);
		Assert.assertEquals("Mac-Book", body.LaptopName);
		Assert.assertEquals("132", body.Id);
		
	}
	
	@Test
	public void testSecureGet() {
		//Adding Headers to the Request
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		//headers.put("Authorization", "Basic YWRtaW46d2VsY29tZQ==");
		headers.put("Authorization", Base64.encodeBase64String("admin:welcome".getBytes()));
		RestResponse response=RestApiHelper.performGetRequest("http://localhost:9090/laptop-bag/webapi/secure/all", headers);
		System.out.println(response.toString());
		
	}

}
