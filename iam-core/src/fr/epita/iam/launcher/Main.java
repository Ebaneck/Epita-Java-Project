
package fr.epita.iam.launcher;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Logger;
import fr.epita.iam.service.SearchIdentity;
import fr.epita.iam.service.UpdateIdentity;
import fr.epita.iam.service.CreateIdentity;
import fr.epita.iam.service.DeleteIdentity;
import fr.epita.iam.service.IdentityJDBCDAO;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.math.BigInteger;
import fr.epita.iam.datamodel.Authenticate;


public class Main {

	
	public static void main(String[] args) throws SQLException {
		

		IdentityJDBCDAO jdbcdao = new IdentityJDBCDAO();
		 boolean quit = false;
		 boolean isauthenticated = false;
		 
		 System.out.print("This is the beginning of Iam Core Program....." + "\n");
		 System.out.print("Let us create an administrator account" + "\n");
		 
		 Scanner input1 = new Scanner(System.in);
		    System.out.println("Enter admin Username : ");
		    String username = input1.next();

		    Scanner input2 = new Scanner(System.in);
		    System.out.println("Enter admin Password : ");
		    String password = input2.next();
		
		
		try {
			if (jdbcdao.createUser(username, password)) {
			    System.out.println("User created");
			}
		

        if (jdbcdao.authenticateUser(username, password)) {
            System.out.println("User authenticated");
            isauthenticated = true;
        }
        
        	
		}
        catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        		    
		 

		    try {
				if (isauthenticated) {

					System.out.println("Access Granted! Welcome To!");

					Scanner scanner = new Scanner(System.in);
					//prompt the user to select an option  which can be create, update, delete, modify or exit
					
					do {
					
					// Display Identity manager menu in a nice way
			        System.out.println("*****************************************");
			        System.out.println("|   IDENTITY MANAGER APPLICATION          |");
			        System.out.println("*****************************************");
			        System.out.println("| Select Options:                         |");
			        System.out.println("|        1. Create Identity     |");
			        System.out.println("|        2. Update Identity     |");
			        System.out.println("|        3. Delete Identity     |");
			        System.out.println("|        4. List/View Database  |");
			        System.out.println("|        5. Exit                |");
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
						
						//search and print current list of identities found withing the database
						
						SearchIdentity.search(scanner);
						break;
						
					case "5":
						 quit = true;
						System.out.println("Goodbye  " + username);
						 System.exit(0); 
			              break;
						

						//Quit

					default:
						System.out.println("Your choice is not recognized");
					}

					 } while (!quit);
				}

				 
			} catch (Exception e) {
				
			}

		    
		    
		    
		    
		}	
	

}


	

