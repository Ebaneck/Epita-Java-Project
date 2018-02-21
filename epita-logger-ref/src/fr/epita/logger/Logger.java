
package fr.epita.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows the creation of Business custom exceptions
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows: by calling Logger in your class
 *
 */
public class Logger {

	private static PrintWriter pw;

	static {
		try {
			pw = new PrintWriter("application.log");
		} catch (final FileNotFoundException e) {
			System.out.println("error while initializing the log file");
		}
	}
	private final Class cls;

	public Logger(Class loggingClass) {
		cls = loggingClass;
	}

	public void info(String message) {
		printMessage(message, "INFO");
	}

	public void error(String message) {
		printMessage(message, "ERROR");
	}

	public void error(String message, Exception e) {
		printMessage(message, "ERROR");
		e.printStackTrace(pw);
		pw.flush();
	}

	public void debug(String message) {
		printMessage(message, "DEBUG");
	}


	private void printMessage(String message, String level) {
		final Date date = new Date();
		final String timestamp = new SimpleDateFormat("yyyyMMdd_HH:mm:ss.SSS").format(date);
		pw.println(timestamp + " - " + level + " - " + message);

		pw.flush();
	}



}
