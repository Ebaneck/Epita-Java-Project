/**
 * 
 * Code application :
 * Composant : 
 */
package fr.epita.iam.launcher;

import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.epita.iam.service.IdentityJDBCDAO;
import fr.epita.iam.service.UpdateIdentity;
import fr.epita.iam.service.CreateIdentity;
import fr.epita.iam.service.DeleteIdentity;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.service.Configuration;



public class Main {

	
	public static void main(String[] args) throws SQLException {
		/*
		Identity id1 = new Identity();
		id1.setDisplayName("Thomas");
		id1.setUid("123");
		id1.setEmail("tbr@tbr.com");
		
		System.out.println(id1);
		
		*/

		  Logger LOGGER = Logger.getLogger("InfoLogging");

		    String Username;
		    String Password;

		    Password = "123";
		    Username = "wisdom";
		    
		    String confirm = "yes";

		    Scanner input1 = new Scanner(System.in);
		    System.out.println("Enter Username : ");
		    String username = input1.next();

		    Scanner input2 = new Scanner(System.in);
		    System.out.println("Enter Password : ");
		    String password = input2.next();
		    
		 

		    try {
				if (username.equals(Username) && password.equals(Password)) {

					System.out.println("Access Granted! Welcome To!");

					Scanner scanner = new Scanner(System.in);
					//prompt the user to select an option  which can be create, update, delete, modify or exit
					
					
					// Display Identity manager menu in a nice way
			        System.out.println("*****************************************");
			        System.out.println("|   IDENTITY MANAGER APPLICATION          |");
			        System.out.println("*****************************************");
			        System.out.println("| Options:                              |");
			        System.out.println("|        1. Create Identity     |");
			        System.out.println("|        2. Update Identity     |");
			        System.out.println("|        3. Delete Identity     |");
			        //System.out.println("|        4. Delete Database Records     |");
			        System.out.println("|        4. Exit                |");
			        System.out.println("*****************************************");
					String choice = scanner.nextLine();

					switch (choice) {
					case "1":
						//create
						/**method to invoke the creation of a new identity**/
						CreateIdentity.CreateIdentity(scanner);

						break;
					case "2":
						//modify
						/**Update identity**/

						UpdateIdentity.update(scanner);

						break;
					case "3":
						//delete
						DeleteIdentity.deletion(scanner);
						break;
					case "4":
						System.out.println("Goodbye  " + Username);
						 System.exit(0);
			                break;
						

						//Quit

					default:
						System.out.println("Your choice is not recognized");
					}

				}

				else if (username.equals(Username)) {
					System.out.println("Invalid Password!");
				} else if (password.equals(Password)) {
					System.out.println("Invalid Username!");
				} else {
					System.out.println("Invalid Username & Password!");
				} 
			} catch (Exception e) {
				// TODO: handle exception
			}

		    
		    
		    
		    
		}	
	

}


	

