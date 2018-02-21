
package fr.epita.logger.tests;

import fr.epita.logger.Logger;


public class TestLogger {

	private static final Logger LOGGER = new Logger(TestLogger.class);

	public static void main(String[] args) {
		LOGGER.info("info test from logger");
	}
}
