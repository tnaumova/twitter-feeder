package ebudyy.tech.test.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebudyy.tech.test.stream.HttpHelper.ResponseHandler;
import ebudyy.tech.test.stream.StreamReaderController.StreamEntryListener;

/**
 * Thread class to read data from response stream, which is received from
 * {@link HttpHelper}
 */
@Component
public class StreamReaderThread extends Thread implements ResponseHandler {

    @Autowired
    private StreamEntryListener entryListener;

    @Autowired
    private HttpHelper httpHelper;

    private AtomicBoolean paused = new AtomicBoolean();

    /** Shows whether thread is connected to response stream. */
    private AtomicBoolean connected = new AtomicBoolean(false);
    
    private AtomicBoolean running = new AtomicBoolean(true);

    public void run() {
        try {
            httpHelper.setResponseHandler(this);
            while (running.get()) {
                if (!connected.getAndSet(false)) {
                    connected.set(true);
                    System.out.println("Connecting to Twitter...");
                    httpHelper.connect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    /**
     *  Handles response received from {@link HttpHelper}
     */
    public void onResponse(InputStream responseContent) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(responseContent));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sleepIfRequested();
                if (connected.get()) {
                    entryListener.onEntry(line);
                } else {
                    httpHelper.disconnect();
                    return;
                }
            }
            reader.close();
            httpHelper.disconnect();
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }

    public void onErrorStatus(String errorMessage) {
        System.err.println("Response returned unsuccessful status :" + errorMessage);
        System.out.println("Execution terminated.");
        System.exit(0);
    }

    public void setKeyWords(String keyWords) {
        httpHelper.setKeyWords(keyWords);
    }

    /**
     * Pauses stream handling
     */
    public void pause(boolean pause) {
        paused.set(pause);
    }

    /**
     * Disconnects from current response stream, new response will be requested. 
     */
    public void disconnect() {
        connected.set(false);
    }

    @Override
    public void interrupt() {
        super.interrupt();
        running.set(false);
        disconnect();
    }

    private void sleepIfRequested() {
        while (paused.get() && connected.get()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // Don't need to do anything if interrupted
                return;
            }
        }
        paused.set(false);  
    }
}