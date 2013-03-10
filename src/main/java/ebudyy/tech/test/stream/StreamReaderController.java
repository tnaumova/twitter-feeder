package ebudyy.tech.test.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mediator between external caller ant response thread reader.
 */
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

    /**
     * @see StreamReaderThread#pause()
     */
    public void pause(boolean paused) {
        readerThread.pause(paused);
    }

    /**
     * @see StreamReaderThread#disconnect()
     */
    public void reconnect() {
        readerThread.disconnect();
    }

    /**
     * Used to finish stream thread execution.
     */
    public void close() {
        if (!readerThread.isInterrupted()) {
            readerThread.interrupt();
        }
        readerThread = null;
    }

    public void setKeyWords(String keyWords) {
        readerThread.setKeyWords(keyWords);
    }

    public interface StreamEntryListener {
        public void onEntry(String line);
    }

}
