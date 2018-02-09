/**
 * 
 * Code application :
 * Composant : 
 */
package fr.epita.iam.launcher;

import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import fr.epita.iam.service.IdentityJDBCDAO;
import fr.epita.iam.service.CreateIdentity;
import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.service.Configuration;



public class Main {

	
	public static void main(String[] args) {
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

					System.out.println("Access Granted! Welcome!");

					Scanner scanner = new Scanner(System.in);
					//prompt the user to select an option  which can be create, update, delete, modify or exit
					System.out.println("Please select an action: ");
					System.out.println("1, Create an identity");
					System.out.println("2, Modify an identity");
					System.out.println("3, Delete an identity");
					System.out.println("4, Exit the application");
					String choice = scanner.nextLine();

					switch (choice) {
					case "1":
						//create
						CreateIdentity.CreateIdentity(scanner);

						break;
					case "2":
						//modify

						break;
					case "3":
						//delete

						break;
					case "4":
						System.out.println("Do You really want to quit : yes/no");
						confirm = scanner.nextLine();
						if (!confirm.equalsIgnoreCase("yes")) {
							break;
						}

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
				
				LOGGER.info("Logging an INFO-level message during program execution");
				LOGGER.info("Stack traces are important here");
				// TODO: handle exception
			}

		    
		    
		    
		    
		}	
	

}


	
