package ebudyy.tech.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

class HttpHelper {

	private static final String PATH = "/1/statuses/filter.json";
	private String user = "_tania0_0";
	private String password = "ebuddytest";
	private String keyWords;

	private final DefaultHttpClient httpclient = new DefaultHttpClient();
	private final HttpHost target;
	private HttpPost httpPost;
	private ResponseHandler responseHandler;

	public HttpHelper() {
		target = new HttpHost("stream.twitter.com", 443, "https");
		setCredetials();
	}

	private StringEntity buildPostRequest() throws UnsupportedEncodingException {
		StringEntity postEntity = new StringEntity("track=" + keyWords, "UTF-8");
		postEntity.setContentType("application/x-www-form-urlencoded");
		return postEntity;
	}

	private void setCredetials() {
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(target.getHostName(), target.getPort()),
				new UsernamePasswordCredentials(user, password));
	}

	public void connect() throws UnsupportedEncodingException,
			IOException, ClientProtocolException {
		if (responseHandler == null) {
			System.err.println("Respnse handler should be defined");
			return;
		}
		
		httpPost = new HttpPost(PATH);
		httpPost.setEntity(buildPostRequest());
		System.out.println("Connecting to Twitter...");
		HttpResponse response = httpclient.execute(target, httpPost);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			responseHandler.onErrorStatus(response);
		} else{
			responseHandler.onResponse(response.getEntity().getContent());
		}
	}

	public void disconnect() {
		if (httpPost != null) {
			httpPost.releaseConnection();
		}
	}

	public void setResponseHandler(ResponseHandler responseHandler) {
		this.responseHandler = responseHandler;
	}

	public interface ResponseHandler {
		void onErrorStatus(HttpResponse response);

		void onResponse(InputStream responseContent);

	}

}
