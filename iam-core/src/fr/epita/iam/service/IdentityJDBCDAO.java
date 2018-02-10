/**
 * Ce fichier est la propriété de Thomas BROUSSARD Code application : Composant :
 */
package fr.epita.iam.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import fr.epita.iam.datamodel.Identity;

import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.logger.Logger;

/**
 * <h3>Description</h3>
 * <p>
 * This class allows to ...
 * </p>
 *
 * <h3>Usage</h3>
 * <p>
 * This class should be used as follows:
 *
 * <pre>
 * <code>${type_name} instance = new ${type_name}();</code>
 * </pre>
 * </p>
 *
 * @since $${version}
 * @see See also $${link}
 * @author ${user}
 *
 *         ${tags}
 */
public class IdentityJDBCDAO implements IdentityDAO {

	private static final Logger LOGGER = new Logger(IdentityJDBCDAO.class);

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
					.prepareStatement("select UID, DISPLAY_NAME, EMAIL FROM IDENTITIES WHERE DISPLAY_NAME = ? OR EMAIL = ? OR UID = ? ");
			//preparedStatement.setString(1, criteria.getUid());
			//preparedStatement.setString(3, criteria.getDisplayName());
			//preparedStatement.setString(2, criteria.getEmail());

			final ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Identity identity = new Identity();
				identity.setDisplayName(resultSet.getString(2));
				identity.setEmail(resultSet.getString(3));
				identity.setUid(resultSet.getString(1));
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
	@Override
	public void update(Identity identity) {
		
		Connection connection = null;
		        try {
		        	try {
						connection = getConnection();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            PreparedStatement preparedStatement = connection
		                    .prepareStatement("delete from IDENTITIES where UIDs=?");
		            // Parameters start with 1
		            preparedStatement.setString(1, identity.getUid());
		            preparedStatement.executeUpdate();

		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		

	

	
	
	
	
	
	
	/*
	 * (non-Javadoc)
	 * @see fr.epita.iam.service.IdentityDAO#delete(fr.epita.iam.datamodel.Identity)
	 */
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
                    .prepareStatement("delete from IDENTITIES where UID=?");
            // Parameters start with 1
            preparedStatement.setString(1, identity.getUid());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	public Identity search(String identity_id) {
		Identity identity = new Identity();
		Connection connection = null;
		 try {
			 connection = getConnection();
	            PreparedStatement preparedStatement = connection.
	                    prepareStatement("select * from IDENTITIES where UID=?");
	            preparedStatement.setString(1, identity_id);
	            ResultSet rs = preparedStatement.executeQuery();

	            if (rs.next()) {
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
	
	
	
	/**
	
	public Identity find(Object id) throws IdentityCreationException {
		Identity identity = null;
		Connection connection = null;
		try {
			String sql = "select * from IDENTITIES where UID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, Integer.parseInt(id.toString()));
			
			ResultSet result = statement.executeQuery();
			if (result.next()){
				String uid = result.getString("identity_id");
				String displayName = result.getString("display_name");
				String email = result.getString("email");
				identity = new Identity(uid, displayName, email);
			}
			
		} catch (SQLException e) {
			LOGGER.error("error in create method :" + e.getMessage());
			final IdentityCreationException businessException = new IdentityCreationException(identity, e);

		 
		
		
	}
		return identity;
	}
	
	**/

}
