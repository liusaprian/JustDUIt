import controller.TransactionController;

public class Main {
	
	private TransactionController transactionController = TransactionController.getInstance();

	public Main() {
		transactionController.returnAdminTransactionReportView();
	}

	public static void main(String[] args) {
		new Main();
	}
}
