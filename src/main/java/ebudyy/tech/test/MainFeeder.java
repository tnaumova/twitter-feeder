package ebudyy.tech.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Tool entry point
 */
public class MainFeeder {

	public static void main(String[] args) {
		System.out.println("Welcome to Twitter Feeder!");
		ApplicationContext ctx = new AnnotationConfigApplicationContext("ebudyy.tech.test");
		ConsoleApplication app = ctx.getBean(ConsoleApplication.class);
		app.start();
	}
}
