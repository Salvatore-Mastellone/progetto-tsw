package it.unisa.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import it.unisa.DriverManagerConnectionPool;

public class ProductModelDS implements ProductModel {
	private static final String TABLE_NAME = "Prodotto";
	
	public synchronized void doSave(ProductBean product) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO " + ProductModelDS.TABLE_NAME
				+ " (nome, categoria, descrizione, stato, lingua, iva, prezzo, stock, linkAccesso) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, product.getNome());
			preparedStatement.setString(2, product.getCategoria());
			preparedStatement.setString(3, product.getDescrizione());
			preparedStatement.setBoolean(4, product.getStato());
			preparedStatement.setString(5, product.getLingua());
			preparedStatement.setInt(6, product.getIva());
			preparedStatement.setFloat(7, product.getPrezzo());
			preparedStatement.setInt(8, product.getStock());
			preparedStatement.setString(9, product.getLinkAccesso());
			
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			} finally {
				if(connection != null)
					connection.close();
			}
		}
		}

	public synchronized boolean doDelete(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		int result = 0;
		
		String deleteSql = "DELETE FROM "  + ProductModelDS.TABLE_NAME + " WHERE idProdotto = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(deleteSql);
			preparedStatement.setInt(1, code);
			
			result = preparedStatement.executeUpdate();
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		
		return (result != 0);
	}
	
	public synchronized ProductBean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ProductBean bean = new ProductBean();
		
		String selectSQL = "SELECT * FROM " + ProductModelDS.TABLE_NAME + " WHERE idProdotto = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {				
				bean.setIdProdotto(rs.getInt("idProdotto"));
				bean.setNome(rs.getString("nome"));
				bean.setCategoria(rs.getString("categoria"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setStato(rs.getBoolean("stato"));
				bean.setLingua(rs.getString("lingua"));
				bean.setIva(rs.getInt("iva"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setStock(rs.getInt("stock"));
				bean.setLinkAccesso(rs.getString("linkAccesso"));
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return bean;
	}
	
	
	public synchronized Collection<ProductBean> doRetrieveAll(String order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<ProductBean> products = new LinkedList<ProductBean>();
		
		String selectSql = "SELECT * FROM " + ProductModelDS.TABLE_NAME;
		
		if (order != null && !order.equals("")) {
			selectSql += " ORDER BY " + order;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSql);

			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				ProductBean bean = new ProductBean();
				
				bean.setIdProdotto(rs.getInt("idProdotto"));
				bean.setNome(rs.getString("nome"));
				bean.setCategoria(rs.getString("categoria"));
				bean.setDescrizione(rs.getString("descrizione"));
				bean.setStato(rs.getBoolean("stato"));
				bean.setLingua(rs.getString("lingua"));
				bean.setIva(rs.getInt("iva"));
				bean.setPrezzo(rs.getFloat("prezzo"));
				bean.setStock(rs.getInt("stock"));
				bean.setLinkAccesso(rs.getString("linkAccesso"));
				
				products.add(bean);
			}
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return products;
	}
}
