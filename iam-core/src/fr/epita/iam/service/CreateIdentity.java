package fr.epita.iam.service;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.service.IdentityJDBCDAO;


/**
 * CreateIdentity is a class that allows users to create user accounts
 * input from this section is gotten from the user using the scanner method
 * 
 * enjoy creating user identities
 * 
 *
 */

/**After running this, you will -> Insert a new record. Create a new identity instance**/
public class CreateIdentity {
	
	
	
	/**
	 * This method creates an Identity from user inputs
	 * @param scanner: Used to collect user input
	 */
	public static void CreateIdentity(Scanner create){
		System.out.println("Welcom to Identity Creation");
		System.out.println("Enter the userID");
		String uid = create.nextLine();
		System.out.println("Enter display name");
		String displayName = create.nextLine();
		System.out.println("Enter email address");
		String email = create.nextLine();
		
		
		Identity identity = new Identity(uid, displayName, email);
		
	
		//We persist and write out the identity created into our database
		System.out.println("This is the identity you want to create\n"+identity);
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		try {
			identityJDBCDAO.create(identity);
		} catch (IdentityCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Identity successfully created");
		
	}
}
