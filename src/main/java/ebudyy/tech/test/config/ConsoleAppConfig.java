package ebudyy.tech.test.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ebudyy.tech.test.ConsoleApplication;
import ebudyy.tech.test.stream.HttpHelper;
import ebudyy.tech.test.stream.StreamReaderController.StreamEntryListener;

/**
 * Spring configuration to initialize {@link ConsoleApplication} 
 */
@Configuration
public class ConsoleAppConfig {

	private String user = "_tania0_0";
	private String password = "ebuddytest";

	@Bean
	/**
	 * Simple stream message listener, rewrites entries to system output stream
	 */
	public StreamEntryListener getListener() {
		return new StreamEntryListener() {

			public void onEntry(String line) {
				System.out.println(line);
			}
		};
	}

	@Bean
	public HttpHelper getHttpHelper() {
		return new HttpHelper(getHttpTarget(), getHttpClient());
	}

	@Bean
	@Scope
	/**
	 * Initializes HTTP client, which use basic authentication to connect to twitter API 
	 */
	public HttpClient getHttpClient() {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpHost target = getHttpTarget();
		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(target.getHostName(), target.getPort()),
				new UsernamePasswordCredentials(user, password));
		return httpclient;
	}


    private HttpHost getHttpTarget() {
    	return new HttpHost("stream.twitter.com", 443, "https");
    }

}
