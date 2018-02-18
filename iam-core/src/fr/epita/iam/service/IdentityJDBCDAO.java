
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import fr.epita.iam.datamodel.Identity;

import fr.epita.iam.exceptions.IdentityCreationException;

import fr.epita.logger.Logger;


public class IdentityJDBCDAO implements IdentityDAO {

	private static final Logger LOGGER = new Logger(IdentityJDBCDAO.class);

	/**Inserts new record in Identity table as you pass new identity instance**/
	@Override
	public void create(Identity identity) throws IdentityCreationException {
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO IDENTITIES(UID, DISPLAY_NAME, EMAIL) values (?,?,?) ");
			preparedStatement.setString(1, identity.getUid());
			preparedStatement.setString(2, identity.getDisplayName());
			preparedStatement.setString(3, identity.getEmail());
			preparedStatement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			LOGGER.error("error in create method :" + e.getMessage());
			final IdentityCreationException businessException = new IdentityCreationException(identity, e);

			throw businessException;
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	 /**Returns all records from table as a List of Identity objects**/
	@Override
	public List<Identity> search() {
		final List<Identity> identities = new ArrayList<>();
		
		// TODO reduce the number of lines to avoid repetition
		// the pattern is always the same, improve with your own ideas.
		// check lambda expressions
		Connection connection = null;
		try {
			connection = getConnection();
			final PreparedStatement preparedStatement = connection
					.prepareStatement("select UID, DISPLAY_NAME, EMAIL, IDENTITY_ID FROM IDENTITIES");
			//.prepareStatement("select * from IDENTITIES");
			//preparedStatement.setString(1, criteria.getUid());
			//preparedStatement.setString(3, criteria.getDisplayName());
			//preparedStatement.setString(2, criteria.getEmail());

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
				if (connection != null) {
					connection.close();
				}
			} catch (final SQLException e) {
				LOGGER.error("unresolved error", e);
			}
		}

		return identities;
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException {

		final String connectionString = Configuration.getInstance().getProperty("db.host");
		final String userName = "root";
		final String password = "password";

		Class.forName("org.apache.derby.jdbc.ClientDriver");

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
			//String sql = "update IDENTITIES  set UID=?, "+ "DISPLAY_NAME=?, "+ "EMAIL=? "+ " where IDENTITY_ID=?";
			PreparedStatement statement = connection.prepareStatement("update IDENTITIES set DISPLAY_NAME=?, EMAIL=?, UID=?" +
                    "where IDENTITY_ID=?");
			statement.setString(1, identity.getDisplayName());
			statement.setString(2, identity.getEmail());
			statement.setString(3, identity.getUid());
			statement.setInt(4, identity.getId());
			
			statement.executeUpdate();

		    }
		catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
        try {
        	try {
				connection = getConnection();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            PreparedStatement preparedStatement = connection
                    .prepareStatement("delete from IDENTITIES where IDENTITY_ID=?");
            // Parameters start with 1
            preparedStatement.setInt(1, identity.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	/**Returns specific record (single Identity object) from table depending on "id" you provide**/
	public Identity locate(String identity_id) {
		Identity identity = new Identity();
		Connection connection = null;
		 try {
			 connection = getConnection();
	            PreparedStatement preparedStatement = connection.
	                    prepareStatement("select * from IDENTITIES where IDENTITY_ID = ?"); //change it to check IDENTITY_ID
	            preparedStatement.setString(1, identity_id);
	            
	            ResultSet rs = preparedStatement.executeQuery();

	            if (rs.next()) {
	            	identity.setId(rs.getInt("IDENTITY_ID"));
	                identity.setUid(rs.getString("UID"));
	                identity.setDisplayName(rs.getString("DISPLAY_NAME"));
	                identity.setEmail(rs.getString("EMAIL"));
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        return identity;
	    }
		// TODO Auto-generated method stub

	public List<Identity> search(Identity criteria) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	private String md5(String aString) throws NoSuchAlgorithmException, UnsupportedEncodingException {
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
 
    
	/*
	 * 
	 *public method to create a user within the database
	 *  paramenters needed include a username and a password
	 *  
	 *  This method creates a salt of the given password for security purposes
	 *  It is our own way of implementing md5 encryption using the java standard
	 */
	
    public boolean createUser(String user, String password) throws ClassNotFoundException {
        SecureRandom random;
        String insert;
        String salt;
 
        random = new SecureRandom();
        salt =  new BigInteger(130, random).toString(16);
        Connection connection = null;
      
 
        try {
        
        	
        	connection = getConnection();
        	final PreparedStatement preparedStatement = connection
					.prepareStatement("INSERT INTO USERS (USERNAME, PASS_SALT, PASS_MD5) values (?, ?, ?)");
        	
         	
        	preparedStatement.setString(1, user);
        	preparedStatement.setString(2, salt);
        	preparedStatement.setString(3, this.md5(salt + password));
        	preparedStatement.executeUpdate();
 
            return true;
        } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
            return false;
        }
    }
 
    
    /*
	 * 
	 *This method is used to authenticate a user
	 *
	 *Given a set of parameters, the method checks the database for the corresponding username and password
	 *this is accomplished by using a hashing out the md5 stored password before comparing if the passwords match
	 */
    public boolean authenticateUser(String user, String password) throws ClassNotFoundException {
        String pass_md5;
        String pass_salt;
        ResultSet res;

        res = null;
        Connection connection = null;
 
        try {
        	connection = getConnection();
        	final PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT PASS_SALT, PASS_MD5 FROM USERS WHERE USERNAME = ?");
        	preparedStatement.setString(1, user);
        	
            res = preparedStatement.executeQuery();
 
            res.next(); // We assume that username is unique
 
            pass_salt = res.getString(1);
            pass_md5 = res.getString(2);
 
            if(pass_md5.equals(this.md5(pass_salt + password))) {
                return true;
            } else {
                return false;
            }
 
        } catch(NoSuchAlgorithmException | SQLException | UnsupportedEncodingException ex) {
            return false;
        } finally {
            try {
                if (res instanceof ResultSet && !res.isClosed()) {
                    res.close();
                }
            } catch(SQLException ex) {
            }
        }
    }
    
    /**
     * 
     * @param admin_id
     * 
     * verify if an admin user exist already within the database
     * 
     * this ensures only a single admin account can exist within the database 
     * @return 
     */
    
    public boolean userexist(String admin_id) {
		Connection connection = null;
		boolean isContain = false;
		 try {
			 connection = getConnection();
	            PreparedStatement preparedStatement = connection.
	                    prepareStatement("select * from USERS where USERNAME = ?"); //change it to check IDENTITY_ID
	            preparedStatement.setString(1, admin_id);
	            
	            ResultSet rs = preparedStatement.executeQuery();

	            if (rs.next()) {
	            	isContain = true;
	            	
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            System.out.println("No Data Found");  //data not exist
	        } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;

	       
	    }
		// TODO Auto-generated method stub
 
   
	

}
