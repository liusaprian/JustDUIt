package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.Connect;

public class TransactionItem {
	private int transactionId, productId, quantity;
	private Connect conn = Connect.getConnection();
	
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
	
	public ResultSet getTransactionItems(int transactionId) {
		ResultSet rs;
		rs = conn.executeQuery("SELECT * FROM transaction_detail WHERE transaction_id = " + transactionId);
		return rs;
	}
}
