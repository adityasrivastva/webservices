package com.api.rest.api.helper.rest_apiframework;

import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import com.api.rest.api.helper.model.RestResponse;

public class PutRequest1 {
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
		
		String xmlBody= "<Laptop>"
			    
			   + "<BrandName>Dell</BrandName>"
			   + "<Features>"
			   +   	"<Feature>8GB RAM</Feature>"
			   +  	"<Feature>1TB Hard Drive</Feature>"
			   + "</Features>"
			   + "<Id>132</Id>"
			   + "<LaptopName>Latitude</LaptopName>"
			+"</Laptop>";
		
		
		
		//HttpPut put= new HttpPut("http://localhost:9090/laptop-bag/webapi/api/delete/{id}");
		RequestBuilder buildPut=RequestBuilder.put("http://localhost:9090/laptop-bag/webapi/api/update")
				.setHeader("Content-Type", "application/xml")
				.setHeader("Accept", "application/xml");
		
		HttpUriRequest put=buildPut.setEntity(new StringEntity(xmlBody, ContentType.APPLICATION_XML)).build();
		
		try (CloseableHttpClient client = HttpClientBuilder.create().build();
				CloseableHttpResponse response = client.execute(put)) {

			//How to read body from response
			
			ResponseHandler<String> body= new BasicResponseHandler();
			RestResponse restResponse= new RestResponse(
					response.getStatusLine().getStatusCode(), 
					body.handleResponse(response));
			
			System.out.println(restResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		HttpPost post = new HttpPost("http://localhost:9090/laptop-bag/webapi/api/add");
		 
		try(CloseableHttpClient client= HttpClientBuilder.create().build()) {
			post.addHeader("Content-Type", "application/json");
			post.addHeader("Accept", "application/json");
			StringEntity data=new StringEntity(jsonBody, ContentType.APPLICATION_JSON);
			post.setEntity(data);
			CloseableHttpResponse response=client.execute(post);
			ResponseHandler<String> handler= new BasicResponseHandler();
			
			RestResponse restResponse= new RestResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response) );
			System.out.println(restResponse.toString());
			client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	*/
	}
}
