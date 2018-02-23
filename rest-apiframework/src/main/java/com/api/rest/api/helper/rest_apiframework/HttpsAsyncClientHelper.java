package com.api.rest.api.helper.rest_apiframework;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.api.rest.api.helper.model.RequestStatus;
import com.api.rest.api.helper.model.RestResponse;

public class HttpsAsyncClientHelper {

	// Genric method to support different HttpEntity Begin
	private static HttpEntity getHttpEntity(Object content, ContentType type) {
		if (content instanceof String) {
			return new StringEntity((String) content, type);
		} else if (content instanceof File) {
			return new FileEntity((File) content, type);
		} else
			throw new RuntimeException("Entity Type not found");
	}

	// Genric method for headers
	private static Header[] getCustomHeaders(Map<String, String> headers) {

		Header[] customHeaders = new Header[headers.size()];
		int i = 0;
		for (String key : headers.keySet()) {
			customHeaders[i++] = new BasicHeader(key, headers.get(key));
		}

		return customHeaders;
	}

	private static SSLContext getSSLContext()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy trustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};
		return SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build();

	}

	private static CloseableHttpAsyncClient getHttpAsyncClient(SSLContext context) {
		
		if (context== null) 
			return HttpAsyncClientBuilder.create().build();
		return HttpAsyncClientBuilder.create().setSSLContext(context).build();
	}

	private static RestResponse performRequest(HttpUriRequest method, SSLContext context) throws InterruptedException, ExecutionException {

		Future<HttpResponse> response= null;
		try (CloseableHttpAsyncClient client = getHttpAsyncClient(context)) {
			client.start();
			response=client.execute(method, new RequestStatus());
			ResponseHandler<String> handler= new BasicResponseHandler();
			return new RestResponse(response.get().getStatusLine().getStatusCode(), handler.handleResponse(response.get()));
			
		} catch (Exception e) {
			if (e instanceof HttpResponseException) 
				return new RestResponse(response.get().getStatusLine().getStatusCode(), e.getMessage());
			
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static RestResponse performGetRequestAsync(String uri, Map<String, String> headers) {

		try {
			return performGetRequestAsync(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public static RestResponse performGetRequestAsync(URI uri, Map<String, String> headers) {
		HttpGet get = new HttpGet(uri);
		if (headers != null)
			get.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(get,null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		} 

	}

	public static RestResponse performPostAsync(URI uri, Object content, ContentType type,
			Map<String, String> headers) {
		HttpUriRequest post = RequestBuilder.post(uri).setEntity(getHttpEntity(content, type)).build();
		if (headers != null)
			post.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(post,null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestResponse performPostAsync(String uri, Object content, ContentType type,
			Map<String, String> headers) {
		try {
			return performPostAsync(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestResponse performPutAsync(URI uri, Object content, ContentType type,
			Map<String, String> headers) {
		HttpUriRequest put = RequestBuilder.put(uri).setEntity(getHttpEntity(content, type)).build();
		if (headers != null)
			put.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(put,null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestResponse performPutAsync(String uri, Object content, ContentType type,
			Map<String, String> headers) {
		try {
			return performPutAsync(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestResponse performDeleteAsync(URI uri, Map<String, String> headers) {
		HttpUriRequest delete = RequestBuilder.delete(uri).build();
		if (headers != null)
			delete.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(delete,null);
		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestResponse performDeleteAsync(String uri,Map<String, String> headers) {
		try {
			return performDeleteAsync(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	
//----------------------------------------------------------------------
	public static RestResponse performGetSSLRequestAsync(String uri, Map<String, String> headers) {

		try {
			return performGetSSLRequestAsync(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	public static RestResponse performGetSSLRequestAsync(URI uri, Map<String, String> headers) {
		HttpGet get = new HttpGet(uri);
		if (headers != null)
			get.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(get,getSSLContext());
		} catch (InterruptedException | ExecutionException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		} 

	}

	public static RestResponse performPostSSLAsync(URI uri, Object content, ContentType type,
			Map<String, String> headers) {
		HttpUriRequest post = RequestBuilder.post(uri).setEntity(getHttpEntity(content, type)).build();
		if (headers != null)
			post.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(post,getSSLContext());
		} catch (InterruptedException | ExecutionException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestResponse performPostSSLAsync(String uri, Object content, ContentType type,
			Map<String, String> headers) {
		try {
			return performPostSSLAsync(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestResponse performPutSSLAsync(URI uri, Object content, ContentType type,
			Map<String, String> headers) {
		HttpUriRequest put = RequestBuilder.put(uri).setEntity(getHttpEntity(content, type)).build();
		if (headers != null)
			put.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(put,getSSLContext());
		} catch (InterruptedException | ExecutionException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestResponse performPutSSLAsync(String uri, Object content, ContentType type,
			Map<String, String> headers) {
		try {
			return performPutSSLAsync(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestResponse performDeleteSSLAsync(URI uri, Map<String, String> headers) {
		HttpUriRequest delete = RequestBuilder.delete(uri).build();
		if (headers != null)
			delete.setHeaders(getCustomHeaders(headers));
		try {
			return performRequest(delete,getSSLContext());
		} catch (InterruptedException | ExecutionException | KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	public static RestResponse performDeleteSSLAsync(String uri,Map<String, String> headers) {
		try {
			return performDeleteSSLAsync(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	
	/*
	public static RestResponse performPutWithSSL(URI uri, Object content, ContentType type,
			Map<String, String> headers) {
		HttpUriRequest put = RequestBuilder.put(uri).setEntity(getHttpEntity(content, type)).build();
		if (headers != null)
			put.setHeaders(getCustomHeaders(headers));
		return performRequest(put);
	}
	public static RestResponse performPutWithSSL(String uri, Object content, ContentType type,
			Map<String, String> headers) {
		try {
			return performPutWithSSL(new URI(uri), content, type, headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static RestResponse performDeleteRequestWithSSL(URI uri, Map<String, String> headers) {
		HttpUriRequest delete = RequestBuilder.delete(uri).build();
		if (headers != null)
			delete.setHeaders(getCustomHeaders(headers));
		return performRequest(delete);

	}
	
	public static RestResponse performDeleteRequestWithSSL(String uri, Map<String, String> headers) {

		try {
			return performDeleteRequestWithSSL(new URI(uri), headers);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}*/
	
	
	
	
}
