package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connection.Connect;

public class TransactionItem {
	
	private int transactionId, productId, quantity;
	private Connect conn = Connect.getConnection();
	
	public TransactionItem(int transactionId, int productId, int quantity) {
		super();
		this.transactionId = transactionId;
		this.productId = productId;
		this.quantity = quantity;
	}
	
	public int getTransactionId() {
		return transactionId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public TransactionItem addTransactionItem(int transactionId, int productId, int quantity) throws SQLException {
		String query = "INSERT INTO transaction_detail(transaction_id, product_id, quantity) VALUES(?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setInt(1, transactionId);
		ps.setInt(2, productId);
		ps.setInt(3, quantity);
		ps.execute();
		return this;
	}
	
	public Vector<TransactionItem> getTransactionItems(int transactionId) {
		ResultSet rs;
		rs = conn.executeQuery("SELECT * FROM transaction_detail WHERE transaction_id = " + transactionId);
		Vector<TransactionItem> items = new Vector<TransactionItem>();
		try {
			while(rs.next())
				items.add(new TransactionItem(transactionId, rs.getInt("product_id"), rs.getInt("quantity")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
}
