package ebudyy.tech.test;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import ebudyy.tech.test.stream.StreamReaderController;

public class ConsoleApplication {

	StreamReaderController reader;

	public ConsoleApplication(StreamReaderController reader) {
		this.reader = reader;
	}

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
