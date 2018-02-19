package fr.epita.iam.service;

import java.util.List;
import java.util.Scanner;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;

public class SearchIdentity {
	private SearchIdentity() {}
	
	public static void search(Scanner scanner) throws IdentityCreationException{
		System.out.println("Current list of all Identities");
		IdentityJDBCDAO identityJDBCDAO = new IdentityJDBCDAO();
		List<Identity> identities = identityJDBCDAO.search();
		
		for(Identity i : identities){
			System.out.println("ID: "+i.getId()+ " \n"+i);  
			
		}

}
}
