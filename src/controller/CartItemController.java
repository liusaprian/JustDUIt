package controller;

import java.util.Vector;
import connection.Connect;
import model.CartItem;
import model.ProductModel;
import view.ManageCartView;
import view.AddToCartView;

public class CartItemController {
	
	private Connect conn = Connect.getConnection();
	
	private Vector<CartItem> cart = new Vector<>(); 
	private AddToCartView customerView = AddToCartView.getInstance();
	private ManageCartView cashierView = ManageCartView.getInstance();
	
	private static CartItemController controller = null;
	private CartItemController() {}
	
	public static CartItemController getInstance() {
		if(controller == null) controller = new CartItemController();
		return controller;
	}
	
	public Vector<CartItem> getListCartItem() {
		return cart;
	}
	
	public CartItem addToCart(int id, int quantity) {
		ProductModel product = ProductModel.getProductModel().getProduct(id);
		if(product == null || product.getProdcutStock() < quantity) return null;
		for (CartItem cartItem : cart)
			if(cartItem.getProductId() == id) {
				cartItem.setQuantity(cartItem.getQuantity()+quantity);
				return cartItem;
			}
		CartItem newItem = new CartItem(id, quantity);
		cart.add(newItem);
		return newItem;
	}
	
	public int cartItemCount() {
		int count = 0;
		for (CartItem cartItem : cart)
			count += cartItem.getQuantity();
		return count;
	}
	
	public void viewAddToCartForm() {
		Vector<ProductModel> products = ProductModel.getProductModel().getAllProduct();
		customerView.showAddToCartForm(products);
	}
	
	public void viewManageCartForm() {
		cashierView.showManageCartForm();
	}
	
//	public CartItem updateStock(int id, int stock) {
//		
//	}
	
	public boolean deleteItem(int id) {
		for(int i = 0; i < cart.size(); i++)
			if(cart.get(i).getProductId() == id) {
				cart.remove(i);
				return true;
			}
		return false;
	}
	
	public void clearCartItemList() {
		cart.clear();
	}

}
