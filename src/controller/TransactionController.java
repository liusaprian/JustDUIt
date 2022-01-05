package controller;

import java.util.Vector;

import model.Transaction;
import view.CustomerTransactionView;
import view.TransactionReportView;

public class TransactionController {

	private static TransactionController controller = null;
	private Transaction transaction;
	private CustomerTransactionView view = CustomerTransactionView.getInstance();
	
	private TransactionController() {
		transaction = new Transaction();
	}

	public static TransactionController getInstance() {
		if(controller == null) controller = new TransactionController();
		return controller;
	}
	
	public Vector<Transaction> getAllTransaction() {
		return transaction.getAllTransactions();
	}
	
	public void returnTransactionCustomerView() {
		view.showTransactionCustomer();
	}
	
	public void returnAdminTransactionReportView() {
		new TransactionReportView();
	}
}
