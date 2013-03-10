package ebudyy.tech.test;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebudyy.tech.test.stream.StreamReaderController;

@Component
public class ConsoleApplication {

    @Autowired
    StreamReaderController reader;

    /** Console to interact with client */
    private Console console = getConsole();

    public void start() {
        System.out.println("Welcome to Twitter Feeder!");
        requestKeyWords(console);
        reader.connect();
        
        while (true) {
            // Waiting for input to interact
            console.readLine("");
            reader.pause(true);
            if (isPositiveAnswerFor("Would you like to change key words?(y|n)")) {
                requestKeyWords(console);
                reader.reconnect();
                continue;
            }
            if (isPositiveAnswerFor("Disconnect from Twitter stream?(y|n)")) {
                reader.close();
                break;
            } else {
                reader.pause(false);
            }
        }

    }

    private Console getConsole() {
        Console console = System.console();
        if (console == null) {
            System.err.println("Cannot proceed. No console object found.");
            System.exit(1);
        }
        return console;
    }

    private boolean isPositiveAnswerFor(String question) {
        String answer = console.readLine(question);
        return "y".equalsIgnoreCase(answer);
    }

    private void requestKeyWords(Console console) {
        String keyWords = console.readLine("Enter key words separated by commas:");
        System.out.println("Press Enter to pause execution");
        reader.setKeyWords(keyWords);
    }

}
