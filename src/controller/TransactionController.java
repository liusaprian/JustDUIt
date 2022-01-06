package controller;

import java.util.Vector;

import model.ProductModel;
import model.Transaction;
import view.AddToCartView;
import view.TransactionReportView;

public class TransactionController {

	private static TransactionController controller = null;
	private Transaction transaction;
	private AddToCartView customerView = AddToCartView.getInstance();
	
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
		Vector<ProductModel> products = ProductModel.getProductModel().getAllProduct();
		customerView.showAddToCartForm(products);
	}
	
	public void returnAdminTransactionReportView() {
		new TransactionReportView();
	}
}