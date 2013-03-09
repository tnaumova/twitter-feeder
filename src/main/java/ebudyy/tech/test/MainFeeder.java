package ebudyy.tech.test;

import java.io.Console;

import ebudyy.tech.test.StreamReaderController.StreamEntryListener;

/**
 * Hello world!
 *
 */
public class MainFeeder {
    public static void main( String[] args )
    {
        System.out.println( "Welcome to Twitter Feeder!" );
        Console console = System.console();
        if (console == null) {
            System.err.println("Cannot proceed. No console object found.");
            System.exit(1);
        }
        
        StreamReaderController reader = StreamReaderController.getInstance();
        reader.connect(new StreamEntryListener() {
			
			public void onEntry(String line) {
				System.out.println(line);
			}
		});
        
        console.readLine("");
        reader.disconnect();
        System.exit(1);
    }
}
