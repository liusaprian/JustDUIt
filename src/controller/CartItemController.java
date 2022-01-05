package controller;

import java.util.Vector;
import connection.Connect;
import view.CustomerTransactionView;

public class CartItemController {
	
	private Connect conn = Connect.getConnection();
	
	private Vector<Integer> cart = new Vector<>(); 
	private CustomerTransactionView view = CustomerTransactionView.getInstance();
	
//	public TransactionItem addToCart(int id, int quantity) {
//	}
	
	public Vector<Integer> getListCartItem() {
		return cart;
	}
	
	public void viewAddToCartForm() {
		
	}
	
	public void viewManageCartForm() {
		view.showTransactionCustomer();
	}
	
	public void clearCartItemList() {
		cart.clear();
	}

}
