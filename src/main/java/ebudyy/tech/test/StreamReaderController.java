package ebudyy.tech.test;

import java.lang.Thread.State;



class StreamReaderController {

	private static StreamReaderController INSTANCE;
	private StreamReaderThread readerThread;

	private StreamReaderController() {
		readerThread = new StreamReaderThread();
	}

	public static StreamReaderController getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new StreamReaderController();
		}
		return INSTANCE;
	}

	public void connect(StreamEntryListener listener) {
		readerThread.setListener(listener);
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

	public interface StreamEntryListener {
		public void onEntry(String line);
	}

}
