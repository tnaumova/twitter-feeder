package ebudyy.tech.test;

import java.io.Console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ebudyy.tech.test.stream.StreamReaderController;

@Component
public class ConsoleApplication {

	@Autowired
	StreamReaderController reader;

	public void start() {
		Console console = System.console();
		if (console == null) {
			System.err.println("Cannot proceed. No console object found.");
			System.exit(1);
		}

		connectToStream(console);
		console.readLine("");
		stop();
	}

	private void connectToStream(Console console) {
		String keyWords = console
				.readLine("Enter key words, as comma separated string:");
		reader.setKeyWords(keyWords);
		reader.connect();
	}
	
	public void stop() {
		reader.disconnect();
		System.exit(1);		
	}

}
