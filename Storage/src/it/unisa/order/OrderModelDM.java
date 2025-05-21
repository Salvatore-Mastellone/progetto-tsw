package it.unisa.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

import it.unisa.DriverManagerConnectionPool;
import it.unisa.product.ProductBean;
import it.unisa.product.ProductModelDS;

public class OrderModelDM {
	private static final String TABLE_NAME = "Fattura";
	
	public synchronized void doSave(OrderBean order) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + OrderModelDM.TABLE_NAME + " (dataOrdine, note, idUtente) VALUES (?, ?, ?, ?)";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, order.getData());
			preparedStatement.setString(2, order.getNote());
			preparedStatement.setInt(3, order.getIdUtente());
			
			preparedStatement.executeUpdate();
			connection.commit();
		} finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			}finally {
				if(connection != null)
					connection.close();
			}
		}
	}
	
	public synchronized OrderBean doRetrieveByKey(int code) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		OrderBean bean = new OrderBean();
		
		String selectSQL = "SELECT * FROM " + OrderModelDM.TABLE_NAME + " WHERE idFattura = ?";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, code);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {	
				bean.setIdFattura(rs.getInt("id"));
				bean.setData(rs.getString("dataOrdine"));
				bean.setNote(rs.getString("note"));
				bean.setIdUtente(rs.getInt("idUtente"));
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
	
	public synchronized Collection<OrderBean> doRetrieveAll(String sort) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<OrderBean> orders = new LinkedList<OrderBean>();
		
		String selectSQL = "SELECT * FROM " + OrderModelDM.TABLE_NAME;
		
		if(sort != null && !sort.equals("")) {
			selectSQL += "ORDER BY " + sort;
		}
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, sort);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while(rs.next()) {
				OrderBean bean = new OrderBean();
				
				bean.setIdFattura(rs.getInt("id"));
				bean.setData(rs.getString("dataOrdine"));
				bean.setNote(rs.getString("note"));
				bean.setIdUtente(rs.getInt("idUtente"));
				
				orders.add(bean);
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
		return orders;
	}
	
	//Restituisce tutti i prodotti Collegati alla fattura
	public synchronized Collection<ProductBean> doRetrivePrductsF(int id) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		Collection<ProductBean> productsF = new LinkedList<ProductBean>();
		
		String selectSQL = "SELECT * FROM " + "acquisto" + "JOIN Prodotto ON Acquisto.IdProdotto = Prodotto.idProdotto " + "WHERE idFattura = ? ";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, id);
			
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
				
				productsF.add(bean);
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
		return productsF;
	}
	
}