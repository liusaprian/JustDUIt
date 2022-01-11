package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connection.Connect;

public class Transaction {
	private int id, employeeId;
	private Date purchaseDate;
	private String paymentType;
	private static Transaction transactionModel;
	private Connect conn;
	
	public static Transaction getTransaction() {
		if(transactionModel == null) transactionModel = new Transaction();
		return transactionModel;
	}
	
	public Transaction(int id, int employeeId, Date purchaseDate, String paymentType) {
		super();
		this.id = id;
		this.employeeId = employeeId;
		this.purchaseDate = purchaseDate;
		this.paymentType = paymentType;
	}
	
	private Transaction() {
		conn = Connect.getConnection();
	}
	
	public int getId() {
		return id;
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
	
	public Transaction addTransaction(Date purchaseDate, int employeeId, String paymentType) throws SQLException {
		String query = "INSERT INTO transaction(purchase_date, employee_id, payment_type) VALUES(?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setDate(1, purchaseDate);
		ps.setInt(2, employeeId);
		ps.setString(3, paymentType);
		ps.execute();
		Vector<Transaction> trs = getAllTransactions(null, null);
		return trs.get(trs.size()-1);
	}
	
	public Vector<Transaction> getAllTransactions(String month, String year) {
		ResultSet rs;
		
		if(month == null && year == null) {
			rs = conn.executeQuery("SELECT * FROM transaction");
		} else if(month == null && year != null) {
			rs = conn.executeQuery("SELECT * FROM transaction WHERE YEAR(purchase_date) = " + year);
		} else if(month != null && year == null) {
			rs = conn.executeQuery("SELECT * FROM transaction WHERE MONTH(purchase_date) = " + month);
		} else {
			rs = conn.executeQuery("SELECT * FROM transaction WHERE MONTH(purchase_date) = " + month + " AND YEAR(purchase_date) = " + year);
		}
		
		Vector<Transaction> transactions = new Vector<>();
		try {
			while(rs.next()) 
				transactions.add(new Transaction(rs.getInt("id"), rs.getInt("employee_id"), rs.getDate("purchase_date"), rs.getString("payment_type")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactions;
	}
	
	public Vector<Transaction> getAllTransactions(int day, int month, int year) {
		ResultSet rs = 
				conn.executeQuery("SELECT * FROM transaction WHERE DAY(purchase_date) = " + day + " AND MONTH(purchase_date) = " + month + " AND YEAR(purchase_date) = " + year);

		Vector<Transaction> transactions = new Vector<>();
		try {
			while(rs.next()) 
				transactions.add(new Transaction(rs.getInt("id"), rs.getInt("employee_id"), rs.getDate("purchase_date"), rs.getString("payment_type")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return transactions;
	}
}
