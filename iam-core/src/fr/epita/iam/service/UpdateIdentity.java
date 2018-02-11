package fr.epita.iam.service;


import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.epita.iam.service.IdentityJDBCDAO;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;

/**
 * This class allows to update an Identity using a scanner method
 * 
 *
 */
public class UpdateIdentity {
	
	
	
	/**
	 * This method can user input and update an existing identity
	 * @param scanner: Get user inputs
	 * @throws IdentityCreationException 
	 */
	
	

	public static void update(Scanner scanner) throws IdentityCreationException{
		System.out.println("Identity Update");
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		List<Identity> identities = identityJDBCDAO.search();
		System.out.println("Please select the identity that you want to edit");
		for(Identity i : identities){
			System.out.println("ID: "+i.getId()+ " \n"+i);  //searches and prints out all identities prior to editing
			//print the .getId() / you are going to search the list of id's now.
		}
		String identity_id = scanner.nextLine();
		Identity foundIdentity = identityJDBCDAO.locate(identity_id);
		if (foundIdentity == null && !foundIdentity.equals("")){
			System.out.println("Did not find identity "+identity_id);
		}
		else{
			System.out.println("Do you want to update identity: "+identity_id+" reply with y/n");
			String answer = scanner.nextLine();
			if (answer.equalsIgnoreCase("y")){
				while(answer.equalsIgnoreCase("y")){
					System.out.println("Choose the identity field you want to edit");
					System.out.println("1, UID"); 
					System.out.println("2, DisplayName"); 
					System.out.println("3, EMAIL");
					System.out.println("4, Save and Quit");
					String options = scanner.nextLine();
					switch(options.toLowerCase()){
						case "1":
							System.out.println("Enter the new UID");
							String uid = scanner.nextLine();
							foundIdentity.setUid(uid);
							identityJDBCDAO.update(foundIdentity);
							break;
						case "2":
							System.out.println("Enter the new display name");
							String displayName = scanner.nextLine();
							foundIdentity.setDisplayName(displayName);
							identityJDBCDAO.update(foundIdentity);
							break;
						case "3":
							System.out.println("Enter the new email address");
							String email = scanner.nextLine();
							foundIdentity.setEmail(email);
							identityJDBCDAO.update(foundIdentity);
							break;
						case "4":
							answer = "n";
							break;
							
						case "5":
							
						default:
							answer = "y";
					}
				}
				
			}
			else{
				System.out.println("Update cancel");
			}
		}
		
		//persist the identity somewhere
		System.out.println("This is the identity you have updated: \n"+foundIdentity);
		identityJDBCDAO.update(foundIdentity);
		System.out.println("update completed successfully");
		
	}
	
	
}
