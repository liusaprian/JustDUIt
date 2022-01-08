package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Vector;

import model.CartItem;
import model.Transaction;
import model.TransactionItem;
import view.TransactionReportView;

public class TransactionController {

	private static TransactionController controller = null;
	private Transaction transaction;
	private TransactionItem transactionItem;
	
	private TransactionController() {
		transaction = Transaction.getTransaction();
		transactionItem = TransactionItem.getInstance();
	}

	public static TransactionController getInstance() {
		if(controller == null) controller = new TransactionController();
		return controller;
	}
	
	public Transaction insertTransaction(String paymentType, Vector<CartItem> carts) {
		int employeeId = 1; //User.getCurrentUser().getId();
		try {
			//UPDATE PRODUCT STOCK
			Transaction tr = transaction.addTransaction(new Date(System.currentTimeMillis()), employeeId, paymentType);
			for(CartItem cartItem: carts)
				transactionItem.addTransactionItem(tr.getId(), cartItem.getProductId(), cartItem.getQuantity());
			return tr;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Vector<Transaction> getAllTransaction() {
		return transaction.getAllTransactions();
	}
	
	public Vector<TransactionItem> getAllTransactionItens(int id) {
		return transactionItem.getTransactionItems(id);
	}
	
	public void returnAdminTransactionReportView() {
		new TransactionReportView();
	}
	
//	public void viewTodayTransactionReport() {
//		
//	}
	
//	public Vector<Transaction> getTodayTransaction() {
//		
//	}
}