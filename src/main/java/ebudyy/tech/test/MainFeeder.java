package ebudyy.tech.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ebudyy.tech.test.config.ConsoleAppConfig;

/**
 * Tool entry point
 */
public class MainFeeder {

	public static void main(String[] args) {
		System.out.println("Welcome to Twitter Feeder!");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				ConsoleAppConfig.class);
		ConsoleApplication app = ctx.getBean(ConsoleApplication.class);
		app.start();
	}
}
