package controller;

import java.util.Vector;

import model.TransactionItem;
import view.CustomerTransactionView;

public class TransactionController {

	private static TransactionController controller = null;
	private CustomerTransactionView view = CustomerTransactionView.getInstance();
	private TransactionController() {}
	
	private Vector<TransactionItem> cart = new Vector<>(); 

	public static TransactionController getInstance() {
		if(controller == null) controller = new TransactionController();
		return controller;
	}
	
	public void returnView() {
		view.showTransactionCustomer();
	}
}
