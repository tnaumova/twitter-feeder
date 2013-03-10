package ebudyy.tech.test.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 * Helper class to make HTTP request via HTTPClient library 
 */
public class HttpHelper {

	private static final String PATH = "/1/statuses/filter.json";
	private String keyWords;

	private HttpClient httpclient;
	private HttpHost target;
	
	private HttpPost httpPost;
	private ResponseHandler responseHandler;

	public HttpHelper(HttpHost target, HttpClient httpclient) {
		this.httpclient = httpclient;
		this.target = target;
	}

	public void connect() throws UnsupportedEncodingException,
			IOException, ClientProtocolException {
		if (responseHandler == null) {
			System.err.println("Response handler should be defined");
			return;
		}
		
		httpPost = new HttpPost(PATH);
		httpPost.setEntity(buildPostRequest());
		HttpResponse response = httpclient.execute(target, httpPost);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			responseHandler.onErrorStatus(response.getStatusLine().getReasonPhrase());
		} else{
			responseHandler.onResponse(response.getEntity().getContent());
		}
	}

	public void disconnect() {
		if (httpPost != null) {
			httpPost.abort();
			httpPost = null;
		}
	}

	
	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	private StringEntity buildPostRequest() throws UnsupportedEncodingException {
    	StringEntity postEntity = new StringEntity("track=" + keyWords, "UTF-8");
    	postEntity.setContentType("application/x-www-form-urlencoded");
    	return postEntity;
    }

    public interface ResponseHandler {
		
		void onErrorStatus(String errorMessage);

		void onResponse(InputStream responseContent);
	}

}
