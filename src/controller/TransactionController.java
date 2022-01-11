package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JFrame;

import helper.Session;
import model.CartItem;
import model.ProductModel;
import model.Transaction;
import model.TransactionItem;
import view.TransactionDetailView;
import view.TransactionHistoryView;
import view.TransactionReportView;

public class TransactionController {

	private static TransactionController controller = null;
	private Transaction transaction;
	private TransactionItem transactionItem;
	private ProductController productController = ProductController.getInstance();
	
	private TransactionController() {
		transaction = Transaction.getTransaction();
		transactionItem = TransactionItem.getInstance();
	}

	public static TransactionController getInstance() {
		if(controller == null) controller = new TransactionController();
		return controller;
	}
	
	public Transaction insertTransaction(String paymentType, Vector<CartItem> carts) {
		int employeeId = Session.getSession().getCurrentUser().getEmployeeId();
		try {
			Transaction tr = transaction.addTransaction(new Date(System.currentTimeMillis()), employeeId, paymentType);
			for(CartItem cartItem: carts) {
				transactionItem.addTransactionItem(tr.getId(), cartItem.getProductId(), cartItem.getQuantity());
				ProductModel p = productController.getProduct(cartItem.getProductId());
				p.setProdcutStock(p.getProdcutStock() - cartItem.getQuantity());
				productController.updateProduct(p.getProductId(), p.getProductName(), p.getProductDescription(), p.getProductPrice(), p.getProdcutStock());
			}
			return tr;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Transaction> getAllTransaction(String month, String year) {
		return transaction.getAllTransactions(month, year);
	}
	
	public Vector<TransactionItem> getAllTransactionItems(int id) {
		return transactionItem.getTransactionItems(id);
	}
	
	public void viewTransactionReport(String month, String year) {
		new TransactionReportView(month, year);
	}
	
	public void viewTodayTransaction() {
		new TransactionHistoryView();
	}
	
	public Vector<Transaction> getTodayTransactionReport() {
		Date currDate = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		return transaction.getAllTransactions(day, month+1, year);
	}
	
	public void viewTransactionDetail(JFrame frame, int id) {
		frame.dispose();
		new TransactionDetailView(id);
	}
}