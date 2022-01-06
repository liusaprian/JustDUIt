package controller;

import java.util.Vector;

import model.TransactionItem;
import view.CustomerTransactionView;

public class TransactionController {

	private static TransactionController controller = null;
	private CustomerTransactionView customerView = CustomerTransactionView.getInstance();
	private TransactionController() {}

	public static TransactionController getInstance() {
		if(controller == null) controller = new TransactionController();
		return controller;
	}
}
