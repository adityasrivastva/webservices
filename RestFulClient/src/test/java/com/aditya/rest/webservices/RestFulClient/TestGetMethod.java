package com.aditya.rest.webservices.RestFulClient;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.aditya.rest.webservices.helper.RestApiHelper;
import com.aditya.rest.webservices.model.ResponseBody;
import com.aditya.rest.webservices.model.RestResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;

public class TestGetMethod {
	@Test
	public void testGetPingAlive() {
		String url= "http://localhost:9090/laptop-bag/webapi/api/ping/Aditya";
		RestResponse response= RestApiHelper.performGetRequest(url,null);
		System.out.println(response);
		Assert.assertEquals(HttpStatus.SC_OK, response.getStatusCode());
		Assert.assertEquals("Hi! Aditya", response.getResponseBody());
	}
	@Test
	public void testGetAll() {
		String url= "http://localhost:9090/laptop-bag/webapi/api/all";
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		RestResponse response= RestApiHelper.performGetRequest(url,headers);
		System.out.println(response);
		Assert.assertTrue("Expected Status Code not preset",(HttpStatus.SC_OK==response.getStatusCode()) || (HttpStatus.SC_NO_CONTENT==response.getStatusCode()));
	}
	@Test
	public void testGetFindWithId() {
		String url= "http://localhost:9090/laptop-bag/webapi/api/find/132";
		Map<String, String> headers= new HashMap<>();
		headers.put("Accept", "application/json");
		RestResponse response= RestApiHelper.performGetRequest(url,headers);
		Assert.assertTrue("Expected Status Code not preset",(HttpStatus.SC_OK==response.getStatusCode()) || (HttpStatus.SC_NOT_FOUND==response.getStatusCode()));
		System.out.println(response.getResponseBody());
		/**
		 * Step 1 Use the GSON Builder class to get the instance of GSON class
		 * Step 2 Use the Gson object to deserialize the json
		 * 
		 * */
		GsonBuilder builder= new GsonBuilder();
		Gson gson=builder.serializeNulls().setPrettyPrinting().create();
		
		ResponseBody body=gson.fromJson(response.getResponseBody(), ResponseBody.class);
		System.out.println(body);
		Assert.assertEquals("Apple Phone",body.BrandName);
		Assert.assertEquals("132", body.Id);
		Assert.assertEquals("Mac-Book", body.LaptopName);
		
		
		
		
	}
}
