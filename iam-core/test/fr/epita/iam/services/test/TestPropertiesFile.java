
package fr.epita.iam.services.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class TestPropertiesFile {

	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		final Properties properties = new Properties();

		properties.load(new FileInputStream("testConfiguration.properties"));
		final String property = properties.getProperty("db.host");
		if (!"jdbc:derby://localhost:1527/iam-b;create=true".equals(property)) {
			System.out.println("failure");
		} else {
			System.out.println("success");
		}
		System.out.println(property);
	}

}
