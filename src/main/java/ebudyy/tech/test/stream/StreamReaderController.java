package ebudyy.tech.test.stream;

import java.lang.Thread.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreamReaderController {

	@Autowired
	private StreamReaderThread readerThread;

	public void connect() {
		readerThread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void pause() {
		if (readerThread == null) {
			return;
		}

		try {
			if (readerThread.isAlive()) {
				if (readerThread.getState() == State.WAITING) {
					readerThread.wait();
				} else {
					readerThread.notify();
				}

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public void disconnect() {
		readerThread.interrupt();
	}

	public void setKeyWords(String keyWords) {
		readerThread.setKeyWords(keyWords);
	}

	public interface StreamEntryListener {
		public void onEntry(String line);
	}

}
