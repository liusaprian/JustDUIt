package controller;

import java.util.Vector;

import model.Transaction;
import view.CustomerTransactionView;
import view.TransactionReportView;

public class TransactionController {

	private static TransactionController controller = null;
<<<<<<< HEAD
	private CustomerTransactionView customerView = CustomerTransactionView.getInstance();
	private TransactionController() {}
=======
	private Transaction transaction;
	private CustomerTransactionView view = CustomerTransactionView.getInstance();
	
	private TransactionController() {
		transaction = new Transaction();
	}
>>>>>>> ca95875fbe8c0d31dbdc28bac5d2112acce2f882

	public static TransactionController getInstance() {
		if(controller == null) controller = new TransactionController();
		return controller;
	}
<<<<<<< HEAD
=======
	
	public Vector<Transaction> getAllTransaction() {
		return transaction.getAllTransactions();
	}
	
	public void returnTransactionCustomerView() {
		view.showTransactionCustomer();
	}
	
	public void returnAdminTransactionReportView() {
		new TransactionReportView();
	}
>>>>>>> ca95875fbe8c0d31dbdc28bac5d2112acce2f882
}
