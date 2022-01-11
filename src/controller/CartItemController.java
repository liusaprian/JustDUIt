package controller;

import java.util.Vector;

import javax.swing.JFrame;

import connection.Connect;
import model.CartItem;
import model.ProductModel;
import view.ManageCartView;
import view.AddToCartView;

public class CartItemController {
	
	private Connect conn = Connect.getConnection();
	
	private Vector<CartItem> cart = new Vector<>(); 
	private ProductController productController = ProductController.getInstance();
	
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
		ProductModel product = productController.getProduct(id);
		if(product == null || product.getProdcutStock() < quantity) return null;
		for (CartItem cartItem : cart)
			if(cartItem.getProductId() == id) {
				return updateStock(cartItem, quantity + cartItem.getQuantity());
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
	
	public int cartTotalPrice() {
		int total = 0;
		for (CartItem cartItem : cart)
			total += productController.getProduct(cartItem.getProductId()).getProductPrice() * cartItem.getQuantity();
		return total;
	}
	
	public void viewAddToCartForm() {
		Vector<ProductModel> products = productController.getAllProduct();
		new AddToCartView(products);
	}
	
	public void viewManageCartForm(JFrame frame) {
		frame.dispose();
		new ManageCartView(cart);
	}
	
	public ProductModel getProductById(int id) {
		return productController.getProduct(id);
	}
	
	public CartItem updateStock(CartItem cartItem, int quantity) {
		int stock = productController.getProduct(cartItem.getProductId()).getProdcutStock();
		if(stock < quantity) return null;
		cartItem.setQuantity(quantity);
		return cartItem;
	}
	
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
