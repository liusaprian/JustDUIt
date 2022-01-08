import controller.CartItemController;

public class Main {
	
//	private TransactionController transactionController = TransactionController.getInstance();
	private CartItemController cartItemController = CartItemController.getInstance();
//	private ProductController productc;
	String username = "customer";
	
	public Main() {
//		transactionController.returnAdminTransactionReportView();
		
		if(username == "customer" || username == "cashier") cartItemController.viewAddToCartForm();
//		productc = ProductController.getProductController();
//		productc.getProductDataView();
	}

	public static void main(String[] args) {
		new Main();
	}
}