import controller.CartItemController;

public class Main {
	
	private CartItemController cartController = CartItemController.getInstance();

	public Main() {
		cartController.viewAddToCartForm();
	}

	public static void main(String[] args) {
		new Main();
	}
}
