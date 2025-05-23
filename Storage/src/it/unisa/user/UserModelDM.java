package it.unisa.user;

import java.sql.*;

import it.unisa.DriverManagerConnectionPool;

public class UserModelDM implements UserModel{
	private static final String TABLE_NAME = "Utenti";

	@Override
	public synchronized void doSave(UserBean user) throws SQLException {
		 
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + UserModelDM.TABLE_NAME
				+ " (email, nome, cognome, indirizzo, citta, provincia, cap, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connection = DriverManagerConnectionPool.getConnection();
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
			connection.commit();
		}catch (SQLException e) {
			if (connection != null) {
				connection.rollback();
			}
			e.printStackTrace();
			throw e;
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if(connection != null)
					connection.close();
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public synchronized UserBean doRetrieveByKey(String email, int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		
		UserBean bean = null;
		String selectSQL = "SELECT * FROM " + UserModelDM.TABLE_NAME + " WHERE ";
		if(email != null && email !="") {
			selectSQL += "email = ?";
		} else if(id != 0)
			selectSQL += "id = ?";
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			if(email != null && email !="") {
				preparedStatement.setString(1, email);
			} else if(id != 0)
				preparedStatement.setString(1, email);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bean = new UserBean();
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
	
	
	@Override
	public void doUpdate(String email, UserBean user) throws SQLException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;

	    // Costruzione dinamica della query SQL
	    StringBuilder updateSql = new StringBuilder("UPDATE " + UserModelDM.TABLE_NAME + " SET ");
	    updateSql.append("email = ?, ");
	    updateSql.append("nome = ?, ");
	    updateSql.append("cognome = ?, ");
	    updateSql.append("indirizzo = ?, ");
	    updateSql.append("citta = ?, ");
	    updateSql.append("provincia = ?, ");
	    updateSql.append("cap = ?");

	    // Aggiungi la password solo se non è null
	    if (user.getPassword() != null) {
	        updateSql.append(", password = ?");
	    }

	    updateSql.append(" WHERE email = ?");

	    try {
	        connection = DriverManagerConnectionPool.getConnection();
	        preparedStatement = connection.prepareStatement(updateSql.toString());

	        // Parametri fissi
	        preparedStatement.setString(1, user.getEmail());
	        preparedStatement.setString(2, user.getNome());
	        preparedStatement.setString(3, user.getCognome());
	        preparedStatement.setString(4, user.getIndirizzo());
	        preparedStatement.setString(5, user.getCitta());
	        preparedStatement.setString(6, user.getProvincia());
	        preparedStatement.setInt(7, user.getCap());
	        
	        int paramIndex = 8;
	        
	        // Se la password va aggiornata, la mettiamo qui
	        if (user.getPassword() != null) {
	            preparedStatement.setString(paramIndex, user.getPassword());
	            paramIndex++;
	        }

	        // Ultimo parametro: WHERE email = ?
	        preparedStatement.setString(paramIndex, email);

	        preparedStatement.executeUpdate();
	        connection.commit();
	        
	    } finally {
	        try {
	            if (preparedStatement != null)
	                preparedStatement.close();
	        } finally {
	            DriverManagerConnectionPool.releaseConnection(connection);
	        }
	    }
	}
	
}
