package fr.epita.iam.service;


import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.service.IdentityJDBCDAO;
import fr.epita.logger.Logger;


/**
 * CreateIdentity is a class that allows users to create user accounts
 * input from this section is gotten from the user using the scanner method
 * 
 * enjoy creating user identities
 * 
 *
 */

/** Create a new identity instance**/
public class CreateIdentity {
	
private static final Logger LOGGER = new Logger(CreateIdentity.class);
	
	private static final String EXCEPTION = "EXCEPTION";
	private CreateIdentity() {}
	
	
	
	/**
	 * 
	 * @param create: Used to collect user input
	 * 
	 * This method creates an Identity from user inputs
	 */
	public static void makeIdentity(Scanner create){
		System.out.println("Identity Creation takes place here:");
		System.out.println("Enter a userID");
		String uid = create.nextLine();
		System.out.println("Enter a display name");
		String displayName = create.nextLine();
		System.out.println("Enter an email address");
		String email = create.nextLine();
		
		
		Identity identity = new Identity(displayName, email, uid);
		
	
		//We persist and write out the identity created into our database
		System.out.println("This is the identity you want to create\n"+identity);
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		try {
			identityJDBCDAO.create(identity);
		} catch (IdentityCreationException e) {
			
			LOGGER.error(EXCEPTION, e); 
		}
		System.out.println("Identity successfully created");
		
	}
}
