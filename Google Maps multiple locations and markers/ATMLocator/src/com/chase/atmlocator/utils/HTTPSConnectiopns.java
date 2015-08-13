package com.chase.atmlocator.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

public class HTTPSConnectiopns {

	public static String request;
	public static int TIMEOUTCONNECTION = 30000;
	public static int TIMEOUTSOCKET = 30000;

	public static String httpGet(String URL) {
	    String data = null;
	    try{
	           HttpClient httpclient = ExSSLSocketFactory
				.getHttpsClient(new DefaultHttpClient());
	           HttpGet request = new HttpGet(URL);
               ResponseHandler<String> responseHandler = new BasicResponseHandler();
               data = httpclient.execute(request, responseHandler);
	       }catch(Exception e){
	           Log.e("", e.toString());
	       }
	    return data;
	}

	public static String httpClientCall(HttpPost httppost,
			List<NameValuePair> nameValuePairs, int isHttp) {
		HttpClient httpClient = ExSSLSocketFactory
				.getHttpsClient(new DefaultHttpClient());
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams,
				TIMEOUTCONNECTION);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUTSOCKET);
		HttpContext localContext = new BasicHttpContext();
		String text = null;
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
			HttpResponse response = httpClient.execute(httppost, localContext);
			if (response != null
					&& response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				text = getASCIIContentFromEntity(entity);
			}
		}/*
		 * catch (IOException e){ e.printStackTrace(); }
		 */catch (Exception e) {
			text = "Exception";
			e.printStackTrace();
		}
		return text;
	}

	protected static String getASCIIContentFromEntity(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = entity.getContent();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in));
		StringBuffer out = new StringBuffer();
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			out.append(line);
		}
		if (in != null) {
			in.close();
			in = null;
		}
		return out.toString();
	}
}
