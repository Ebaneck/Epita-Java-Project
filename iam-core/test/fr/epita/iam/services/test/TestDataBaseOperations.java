
package fr.epita.iam.services.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.epita.iam.datamodel.Identity;
import fr.epita.iam.exceptions.IdentityCreationException;
import fr.epita.iam.service.Configuration;
import fr.epita.iam.service.IdentityJDBCDAO;


public class TestDataBaseOperations {

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		// testConnectAndSelect();

		// createTest();

		testCreateSearchFromDAO();
	}

	
	private static void createTest() throws ClassNotFoundException, SQLException {
		final Connection connection = getConnection();

		final PreparedStatement preparedStatement = connection
				.prepareStatement("INSERT INTO IDENTITIES (DISPLAY_NAME, EMAIL, UID) VALUES (?, ?, ?)");

		preparedStatement.setString(1, "Clément");
		preparedStatement.setString(2, "cserr@natsystem.fr");
		preparedStatement.setString(3, "4567");
		preparedStatement.execute();

		connection.close();
	}

	
	private static void testConnectAndSelect() throws ClassNotFoundException, SQLException {
		final Connection connection = getConnection();

		final String sqlQuery = "select * from IDENTITIES";

		final PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
		final ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			System.out.println(resultSet.getString(1));
			System.out.println(resultSet.getString(2));
			System.out.println(resultSet.getString(3));
			System.out.println(resultSet.getInt(4));
		}
		connection.close();
	}

	private static void testCreateSearchFromDAO() {
		// given
		final IdentityJDBCDAO dao = new IdentityJDBCDAO();
		final Identity identity = new Identity();
		identity.setDisplayName("Thomas Broussard");
		identity.setUid("1234");
		identity.setEmail("tbr@tbr.com");

		try {
			dao.create(identity);
		} catch (final IdentityCreationException e) {
			System.out.println(e.getMessage());
		}

		// when
		final Identity criteria = new Identity();
		criteria.setDisplayName("Thomas Broussard");
		final List<Identity> identities = dao.search(criteria);

		// then
		if (identities == null || identities.isEmpty()) {
			System.out.println("failure");
		} else {
			System.out.println("success");
		}

	}

	
	private static Connection getConnection() throws ClassNotFoundException, SQLException {

		final Configuration configuration = Configuration.getInstance();
		final String connectionString = configuration.getProperty("db.host");
		final String userName = "root";
		final String password = "root";

		Class.forName("org.apache.derby.jdbc.ClientDriver");

		final Connection connection = DriverManager.getConnection(connectionString, userName, password);
		return connection;
	}

	private static void differenceBetweenPreparedStatementAndStatement() throws ClassNotFoundException, SQLException {
		final Connection connection = getConnection();
		final PreparedStatement prepareStatement = connection.prepareStatement("select * from IDENTITIES where DISPLAY_NAME = ?");
		// Don't do what follows, this will not be protected against SQL injections.
		// Statement statement = connection.createStatement("select * from IDENTITIES where DISPLAY_NAME = ?");
		final String parameter = " 'toto' and 1 = 1; DROP TABLE IDENTIES";
		prepareStatement.setString(1, parameter);
	}
}
