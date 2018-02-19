
package fr.epita.iam.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.logger.Logger;


public class IdentityJDBCDAO implements IdentityDAO {

	private static final Logger LOGGER = new Logger(IdentityJDBCDAO.class);
	
	private static final String EXCEPTION = "EXCEPTION"; //constants are caps

	/**Inserts new record in Identity table as you pass new identity instance**/
	@Override
	public void create(Identity identity) throws IdentityCreationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection
					.prepareStatement("INSERT INTO IDENTITIES(UID, DISPLAY_NAME, EMAIL) values (?,?,?) ");
			preparedStatement.setString(1, identity.getUid());
			preparedStatement.setString(2, identity.getDisplayName());
			preparedStatement.setString(3, identity.getEmail());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			//LOGGER.error("error in create method :" + e.getMessage()); //nope
			LOGGER.error(EXCEPTION, e); 
			final IdentityCreationException businessException = new IdentityCreationException(identity, e);

			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				
				if(preparedStatement != null) {
					preparedStatement.close();
				}
			} catch (final SQLException e) {
				//LOGGER.error(e.getMessage()); //should i still leave this??
				
				LOGGER.error(EXCEPTION, e);  //same, oui
			}
		}
	}

	 /**Returns all records from table as a List of Identity objects**/
	@Override
	public List<Identity> search() {
		final List<Identity> identities = new ArrayList<>();
		
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			 preparedStatement = connection
					.prepareStatement("select UID, DISPLAY_NAME, EMAIL, IDENTITY_ID FROM IDENTITIES");
			

			final ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Identity identity = new Identity();
				identity.setDisplayName(resultSet.getString(2));
				identity.setEmail(resultSet.getString(3));
				identity.setUid(resultSet.getString(1));
				identity.setId(resultSet.getInt(4));
				identities.add(identity);
			}
		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error while searching", e);
		} finally {
			
				try {
					if (connection != null)
						
					connection.close();
				
				} catch (SQLException e1) {
					LOGGER.error(EXCEPTION, e1); 
				}
				try {
					if (preparedStatement != null)
					preparedStatement.close();
				} catch (SQLException e) {
				
					
					LOGGER.error(EXCEPTION, e); 
				}
				
				
			
		}

		return identities;
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {

		final String connectionString = Configuration.getInstance().getProperty("db.host");
		final String userName = Configuration.getInstance().getProperty("db.username");
		final String password = Configuration.getInstance().getProperty("db.password");

		Class.forName("org.apache.derby.jdbc.ClientDriver"); // ""

		final Connection connection = DriverManager.getConnection(connectionString, userName, password);
		return connection;
	}

	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.service.IdentityDAO#update(fr.epita.iam.datamodel.Identity)
	 */
	
	  /**Updates a specific record depending on "id"
     * after you pass new identity object with new values**/
	@Override
	public void update(Identity identity) {
		
		Connection connection = null;
		
		try {
			connection = getConnection();
			
			PreparedStatement statement = connection.prepareStatement("update IDENTITIES set DISPLAY_NAME=?, EMAIL=?, UID=?" +
                    "where IDENTITY_ID=?");
			statement.setString(1, identity.getDisplayName());
			statement.setString(2, identity.getEmail());
			statement.setString(3, identity.getUid());
			statement.setInt(4, identity.getId());
			
			statement.executeUpdate();

		    }
		catch (SQLException | ClassNotFoundException e) {
			LOGGER.error(EXCEPTION, e); 
        }
		

	
	}
	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.service.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
	
	   /**Deletes single record depending on "id" you provide... this method is meant to be verbose**/
	@Override
	public void delete(Identity identity) throws IdentityCreationException {
		
		Connection connection = null;
		 PreparedStatement preparedStatement = null;
        try {
        	connection = getConnection();
            preparedStatement = connection
                    .prepareStatement("delete from IDENTITIES where IDENTITY_ID=?");
            // Parameters start with 1
            preparedStatement.setInt(1, identity.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
        	LOGGER.error(EXCEPTION, e); 
        }
        
        finally {
     	   try {
     		   preparedStatement.close();
     	      connection.close();
     	   } catch (Exception e) {
     	      // Do something
     	   }
     	}
	}

	/**Returns specific record (single Identity object) from table depending on "id" you provide**/
	public Identity locate(String identityID) {
		Identity identity = new Identity();
		Connection connection = null;
		 try {
			 connection = getConnection();
	            PreparedStatement preparedStatement = connection.
	                    prepareStatement("select * from IDENTITIES where IDENTITY_ID = ?"); 
	            preparedStatement.setString(1, identityID);
	            
	            ResultSet rs = preparedStatement.executeQuery();

	            if (rs.next()) {
	            	identity.setId(rs.getInt("IDENTITY_ID"));
	                identity.setUid(rs.getString("UID"));
	                identity.setDisplayName(rs.getString("DISPLAY_NAME"));
	                identity.setEmail(rs.getString("EMAIL"));
	                
	            }
	        } catch (SQLException | ClassNotFoundException e) {
	        	LOGGER.error(EXCEPTION, e); 
	        } 
		 

	        return identity;
	    }
		

	public List<Identity> search(Identity criteria) {
		
		return null;
	}
	
	/**
	 * 
	 * @param aString
	 * @return a hexstring composed of md5 encryption
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	
	public String md5(String aString) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        String hex;
        StringBuffer hexString;
        byte[] bytesOfMessage;
        byte[] theDigest;
 
        hexString = new StringBuffer();
        bytesOfMessage = aString.getBytes("UTF-8");
        md = MessageDigest.getInstance("MD5");
        theDigest = md.digest(bytesOfMessage);
 
        for (int i = 0; i < theDigest.length; i++) {
            hex = Integer.toHexString(0xff & theDigest[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
 
        return hexString.toString();
    }
 
    
	/**
	 * 
	 *public method to create a user within the database
	 *  paramenters needed include a username and a password
	 *  
	 *  This method creates a salt of the given password for security purposes
	 *  It is our own way of implementing md5 encryption using the java standard
	 */
	
    public boolean createUser(String user, String password) throws ClassNotFoundException {
    	
    	System.out.println("Let us create an administrator account" + "\n");
        SecureRandom random;
        String salt;
 
        random = new SecureRandom();
        salt =  new BigInteger(130, random).toString(16);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
      
 
        try {
        
        	
        	connection = getConnection();
        	 preparedStatement = connection
					.prepareStatement("INSERT INTO USERS (USERNAME, PASS_SALT, PASS_MD5) values (?, ?, ?)");
        	
         	
        	preparedStatement.setString(1, user);
        	preparedStatement.setString(2, salt);
        	preparedStatement.setString(3, this.md5(salt + password));
        	preparedStatement.executeUpdate();
 
            return true;
        } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
            return false;
        }
        
       
        finally {
        	   try {
        		   preparedStatement.close();
        	      connection.close();
        	   } catch (Exception e) {
        	      // Do something
        	   }
        	} 
        
    }
 
    
    /**
	 * 
	 *This method is used to authenticate a user
	 *
	 *Given a set of parameters, the method checks the database for the corresponding username and password
	 *this is accomplished by using a hashing out the md5 stored password before comparing if the passwords match
	 */
    public boolean authenticateUser(String user, String password) throws ClassNotFoundException {
        String passmd5;
        String passsalt;
        ResultSet res;

        res = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        
 
        try {
        	connection = getConnection();
        	preparedStatement = connection
					.prepareStatement("SELECT PASS_SALT, PASS_MD5 FROM USERS WHERE USERNAME = ?");
        	preparedStatement.setString(1, user);
        	
            res = preparedStatement.executeQuery();
 
            res.next(); // We assume that username is unique
 
            passsalt = res.getString(1);
            passmd5 = res.getString(2);
 
            if(passmd5.equals(this.md5(passsalt + password))) {
                return true;
            } else {
                return false;
            }
 
        } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
            return false;
        } finally {
            try {
                    preparedStatement.close();
                    connection.close();
                    res.close();
                
            } catch(SQLException ex) {
            	LOGGER.error(EXCEPTION, ex); 
            }
        }
    }
    
    /**
     * 
     * @param username 
     * @param adminUsername
     * 
     * verify if an admin user exist already within the database
     * 
     * this ensures only a single admin account can exist within the database 
     * @return 
     */
    
    public boolean userexist(String adminUser) {   //with an integer before
		Connection connection = null;
		 try {
			 connection = getConnection();
	            PreparedStatement preparedStatement = connection.
	                    prepareStatement("select * from USERS where USERNAME = ?"); 
	            preparedStatement.setString(1, adminUser); 
	            ResultSet rs = preparedStatement.executeQuery();

	            if (rs.next()) {
	            	return true;
	            } 
	        } catch (SQLException | ClassNotFoundException e) {
	        	LOGGER.error(EXCEPTION, e); 
	           
	        } 
		return false; //wrong.

	       
	    }
 
   
	

}
