package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;

import helper.Session;
import model.CartItem;
import model.ProductModel;
import model.Transaction;
import model.TransactionItem;
import view.TransactionDetailView;
import view.TransactionReportView;

public class TransactionController {

	private static TransactionController controller = null;
	private Transaction transaction;
	private TransactionItem transactionItem;
	private ProductModel product = ProductModel.getProductModel();
	
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
				ProductModel p = product.getProduct(cartItem.getProductId());
				p.setProdcutStock(p.getProdcutStock() - cartItem.getQuantity());
				product.updateProduct(p);
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
	
	public Vector<TransactionItem> getAllTransactionItens(int id) {
		return transactionItem.getTransactionItems(id);
	}
	
	public void viewTransactionReport(String month, String year) {
		new TransactionReportView(month, year);
	}
	
	public void viewTransactionDetail(int id) {
		new TransactionDetailView(id);
	}
	
//	public void viewTodayTransactionReport() {
//		
//	}
	
//	public Vector<Transaction> getTodayTransaction() {
//		
//	}
}