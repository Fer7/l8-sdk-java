package es.develappers.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class RESTfulClient {

	protected String baseURL;
	protected HttpClient client;
	protected ResponseHandler<Response> handler;
	
	public RESTfulClient(String baseURL) 
	{
		this.baseURL = baseURL;
		this.client = new DefaultHttpClient();
		this.handler = new ResponseHandler<Response>() {
			@Override
			public Response handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
				int responseCode = httpResponse.getStatusLine().getStatusCode();
				
				Object responseJSON = null;
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					try{ 
						String responseBody = RESTfulClient.readResponseBody(httpEntity.getContent());
						JSONParser parser = new JSONParser();
						responseJSON = parser.parse(responseBody);
					} catch (Exception e) {
						throw new ClientProtocolException(e);
					}
				}
				
				Response response = new Response();
				response.setCode(responseCode);
				response.setJSON(responseJSON);
				return response;
			}
		};
	}
	
	protected static String readResponseBody(InputStream is) throws Exception
	{
	    String line = "";
	    StringBuilder total = new StringBuilder();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	    while ((line = rd.readLine()) != null) { 
	        total.append(line); 
	    }
	    return total.toString();
	}	

	public Response post(String endPoint) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		HttpPost httpPost = new HttpPost(endPointURL);
		Response response = this.client.execute(httpPost, this.handler);
		return response;
	}
	
	public Response post(String endPoint, JSONObject json) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		HttpPost httpPost = new HttpPost(endPointURL);

		StringEntity input = new StringEntity(json.toJSONString());
		input.setContentType("application/json");
		httpPost.setEntity(input);		
		Response response = this.client.execute(httpPost, this.handler);
		return response;
	}
	
	public Response put(String endPoint, JSONObject json) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		
		HttpPut httpPut = new HttpPut(endPointURL);
		
		StringEntity input = new StringEntity(json.toJSONString());
		input.setContentType("application/json");
		httpPut.setEntity(input);		
		Response response = this.client.execute(httpPut, this.handler);
		
		return response;
	}
	
	public Response get(String endPoint) throws Exception
	{
		String endPointURL = this.baseURL + endPoint;
		
		HttpGet httpGet = new HttpGet(endPointURL);
		Response response = this.client.execute(httpGet, this.handler);
		
		return response;
	}
	
}