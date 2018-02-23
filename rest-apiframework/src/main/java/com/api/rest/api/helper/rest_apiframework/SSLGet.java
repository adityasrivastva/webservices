package com.api.rest.api.helper.rest_apiframework;

import java.util.HashMap;
import java.util.Map;

import com.api.rest.api.helper.model.RestResponse;

/**
 * Hello world!
 *
 */
public class SSLGet {
	public static void main(String[] args) {
		/*RestResponse performGetRequest = RestApiHelper
				.performGetRequest("http://localhost:9090/laptop-bag/webapi/sslres/all", null);
		System.out.println(performGetRequest);*/
		
		/*RestResponse performGetRequest = HttpsClientHelper.
				performGetRequestWithSSL("http://localhost:9090/laptop-bag/webapi/sslres/all", null);
		System.out.println(performGetRequest);*/
		
		Map<String, String> headers= new HashMap<>();
		
		headers.put("Accept", "application/xml");
		RestResponse response = HttpsAsyncClientHelper.performGetRequestAsync("http://localhost:9090/laptop-bag/webapi/api/all", headers);
		System.out.println(response);
		response = HttpsAsyncClientHelper.performGetSSLRequestAsync("http://localhost:9090/laptop-bag/webapi/sslres/all", headers);
		System.out.println(response);
		
		
		
	
}
}
/*
 String s= "JAVA is a wonderful Language";
		int x= s.length()- s.toUpperCase().replaceAll("A", "").length();
		System.out.println(x);
 
 
 */
