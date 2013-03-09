package ebudyy.tech.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

import ebudyy.tech.test.HttpHelper.ResponseHandler;
import ebudyy.tech.test.StreamReaderController.StreamEntryListener;

final class StreamReaderThread extends Thread implements
		ResponseHandler {

	private StreamEntryListener listener;

	private HttpHelper httpHelper = new HttpHelper();

	public void run() {
		try {
			if (!isInterrupted()) {
				httpHelper.setResponseHandler(this);
				httpHelper.connect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onResponse(InputStream responseContent) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				responseContent));
		String line = null;
		try {
			while (!this.isInterrupted() && (line = reader.readLine()) != null) {
				listener.onEntry(line);
			}
			reader.close();
			httpHelper.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onErrorStatus(HttpResponse response) {
		System.err.println(response.getStatusLine().getReasonPhrase());
		interrupt();
	}

	public void setListener(StreamEntryListener listener) {
		if (listener == null) {
			System.err.println("Stream Listener should be defined");
			return;
		}

		this.listener = listener;
	}
}