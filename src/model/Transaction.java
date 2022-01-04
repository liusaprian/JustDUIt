package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.Connect;

public class Transaction {
	private int id, employeeId;
	private Date purchaseDate;
	private String paymentType;
	private Connect conn = Connect.getConnection();
	
	public Transaction(int employeeId, String paymentType) {
		super();
		this.employeeId = employeeId;
		this.purchaseDate = new Date(System.currentTimeMillis());
		this.paymentType = paymentType;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public Transaction addTransaction() throws SQLException {
		String query = "INSERT INTO transaction_header(purchase_date, employee_id, payment_type) VALUES(?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setDate(1, purchaseDate);
		ps.setInt(2, employeeId);
		ps.setString(3, paymentType);
		ps.execute();
		return this;
	}
	
	public ResultSet getAllTransactions() {
		ResultSet rs;
		rs = conn.executeQuery("SELECT * FROM transaction_header");
		return rs;
	}
}
