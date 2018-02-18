
package fr.epita.iam.services.test;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.service.IdentityDAO;
import fr.epita.iam.service.IdentityXMLDAO;


public class TestXml {

	
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

		// given
		final IdentityDAO dao = new IdentityXMLDAO();

		// when
		try {
			dao.create(new Identity("Clément", "cserr@cserr.com", "123456"));
		} catch (final IdentityCreationException e) {
			System.out.println("error while creating identity");
			return;
		}

		// then
		final List<Identity> identities = dao.search();
		if (identities.isEmpty()) {
			System.out.println("failure");
		} else {
			System.out.println("success");
		}

		System.out.println(identities);

	}

}
