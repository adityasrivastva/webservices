package com.aditya.rest.webservices.helper;

import java.net.URI;
import java.util.Map;

import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.aditya.rest.webservices.model.RestResponse;

public class RestApiHelper {
	
	public static RestResponse performGetRequest(String url, Map<String, String> headers) {
		try {
			return performGetRequest(new URI(url), headers);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
public static RestResponse performGetRequest(URI uri,Map<String, String> headers) {
	
		HttpGet get= new HttpGet(uri);
		if (headers !=null) {
			
			for (String key : headers.keySet()) {
				get.setHeader(key, headers.get(key));
			}
		}
		CloseableHttpResponse response=null;
		try(CloseableHttpClient client= HttpClientBuilder.create().build()) {
			response= client.execute(get);
			ResponseHandler<String> body= new BasicResponseHandler();
			/*RestResponse restResponse= new RestResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));
			return restResponse;*/
			return new RestResponse(response.getStatusLine().getStatusCode(), body.handleResponse(response));
			
		} catch (Exception e) {
			//e.printStackTrace();
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}
			throw new RuntimeException(e.getMessage(), e);
		}
		
	}

}
