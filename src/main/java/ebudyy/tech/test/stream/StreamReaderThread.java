package ebudyy.tech.test.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebudyy.tech.test.stream.HttpHelper.ResponseHandler;
import ebudyy.tech.test.stream.StreamReaderController.StreamEntryListener;

/**
 * Thread class to read data from stream, which is received from
 * {@link HttpHelper}
 */
@Component
public class StreamReaderThread extends Thread implements ResponseHandler {

	@Autowired
	private StreamEntryListener entryListener;

	@Autowired
	private HttpHelper httpHelper;

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
				entryListener.onEntry(line);
			}
			reader.close();
			httpHelper.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void onErrorStatus(String errorMessage) {
		System.err.println(errorMessage);
		interrupt();
	}

	public void setKeyWords(String keyWords) {
		httpHelper.setKeyWords(keyWords);
	}

}