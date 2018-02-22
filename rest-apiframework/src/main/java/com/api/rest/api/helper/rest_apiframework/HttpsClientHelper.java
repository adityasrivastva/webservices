package com.api.rest.api.helper.rest_apiframework;

import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

import com.api.rest.api.helper.model.RestResponse;

public class HttpsClientHelper {
	
	public static SSLContext getSSLContext()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy trustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		};
		return SSLContextBuilder.create().loadTrustMaterial(trustStrategy).build();

	}

	public static CloseableHttpClient getHttpClient(SSLContext sslContext) {
		CloseableHttpClient build = HttpClientBuilder.create().setSSLContext(sslContext).build();

		return build;

	}

	public static RestResponse performGetRequestWithSSL(String uri, Map<String, String> headers) {
		HttpGet get = new HttpGet(uri);
		if (headers != null) {
			for (String key : headers.keySet()) {
				get.addHeader(key, headers.get(key));
			}
		}
		CloseableHttpResponse response = null;
		try (CloseableHttpClient client = getHttpClient(getSSLContext())) {
			response = client.execute(get);

			ResponseHandler<String> handler = new BasicResponseHandler();
			return new RestResponse(response.getStatusLine().getStatusCode(), handler.handleResponse(response));
		} catch (Exception e) {
			if (e instanceof HttpResponseException) {
				return new RestResponse(response.getStatusLine().getStatusCode(), e.getMessage());
			}
			throw new RuntimeException(e.getMessage(), e);
		}

	}
	
	

}
