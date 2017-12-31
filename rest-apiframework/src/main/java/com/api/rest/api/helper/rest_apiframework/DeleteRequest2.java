package com.api.rest.api.helper.rest_apiframework;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.api.rest.api.helper.model.RestResponse;

public class DeleteRequest2 {
	/*
	 * @param aggs
	 * Step 1:- Create the HTTP Get/Post/Delete/Put method
	 * Step 2:- Create the HTTP Client
	 * Step 3:- Execute the HTTP methods using the Client
	 * Step 4:- Catch the response of Execution
	 * Step 5:- Display the response at console.
	 * 
	 * */
	
	public static void main(String[] args) {
		
		RestResponse response= RestApiHelper.performDeleteRequest("http://localhost:9090/laptop-bag/webapi/api/delete/366", null);
		System.out.println(response.toString());
	}
}
