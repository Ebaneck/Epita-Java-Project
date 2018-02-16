
package fr.epita.iam.service;

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
	

}
