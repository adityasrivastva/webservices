package com.api.rest.api.helper.rest_apiframework;

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
		
		RestResponse performGetRequest = HttpsClientHelper.
				performGetRequestWithSSL("http://localhost:9090/laptop-bag/webapi/sslres/all", null);
		System.out.println(performGetRequest);
		
		
		
		
	
}
}
/*
 String s= "JAVA is a wonderful Language";
		int x= s.length()- s.toUpperCase().replaceAll("A", "").length();
		System.out.println(x);
 
 
 */
