package it.unisa;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class UserModelDM implements UserModel{

private static DataSource ds;
	
	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			
			ds = (DataSource) envCtx.lookup("jdbc/storage");

		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	
	private static final String TABLE_NAME = "Utenti";

	@Override
	public synchronized void doSave(UserBean user) throws SQLException {
		 
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UserModelDM.TABLE_NAME
				+ " (email, nome, cognome, indirizzo, citta, provincia, cap, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			//connection = DriverManagerConnectionPool.getConnection();
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getNome());
			preparedStatement.setString(3, user.getCognome());
			preparedStatement.setString(4, user.getIndirizzo());
			preparedStatement.setString(5, user.getCitta());
			preparedStatement.setString(6, user.getProvincia());
			preparedStatement.setInt(7, user.getCap());
			preparedStatement.setString(8, user.getPassword());

			preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if(connection != null)
					connection.close();
				//DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public synchronized UserBean doRetrieveByKey(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		UserBean bean = new UserBean();

		String selectSQL = "SELECT * FROM " + UserModelDM.TABLE_NAME + " WHERE email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean.setCap(rs.getInt("cap"));
				bean.setCitta(rs.getString("citta"));
				bean.setCognome(rs.getString("cognome"));
				bean.setEmail(rs.getString("email"));
				bean.setIndirizzo(rs.getString("indirizzo"));
				bean.setNome(rs.getString("nome"));
				bean.setPassword(rs.getString("password"));
				bean.setProvincia(rs.getString("provincia"));

			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return bean;
	}

	@Override
	public synchronized boolean doDelete(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		int result = 0;

		String deleteSQL = "DELETE FROM " + UserModelDM.TABLE_NAME + " WHERE email = ?";

		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSQL);
			preparedStatement.setString(1, email);

			result = preparedStatement.executeUpdate();

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
		return (result != 0);
	}
	
	
}
