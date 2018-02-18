package fr.epita.iam.service;
import java.util.List;
import java.util.Scanner;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.service.IdentityJDBCDAO;



/**
 * goal of this class is to obtain user input from the console
 * which is need to delete an identity already present in the database
 *
 *
 */

public class DeleteIdentity {
	
	private DeleteIdentity() {}

	/**
	 * This method deletes an existing Identity
	 * @param scanner: Get user selection
	 * @throws IdentityCreationException 
	 */
	public static void deletion(Scanner scanner) throws IdentityCreationException{
		System.out.println("Deleting an Identity");
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		List<Identity> identities = identityJDBCDAO.search();
	
		System.out.println("Select an Identity from the list below");
		
		/**To retrieve values, you will have to use "for each" loop
         * to iterate through list**/
		for(Identity i : identities){
			System.out.println("Unique ID : "+i.getId()+ " \n"+i);
		}
		String identity_id = scanner.nextLine();
		if (identity_id.isEmpty()) {
			System.out.println("Did not understand you answer ");
			return;
		}
		Identity foundIdentity = identityJDBCDAO.locate(identity_id);
		if (foundIdentity == null){
			System.out.println("Did not find identity: "+identity_id);
		}
		else{
			System.out.println("Do you really want to "
					+ "delete identity: " + identity_id+ " " + "Reply with: y/n");
			String answer = scanner.nextLine();
			if (answer.equalsIgnoreCase("y")){
				//persist the identity somewhere
				System.out.println("This is the identity you want to delete: \n"+foundIdentity);
				identityJDBCDAO.delete(foundIdentity);
				System.out.println("Identity is Gone for good");
			}
			else{
				System.out.println("Cancel button was pressed");
			}
		}
	}

}
