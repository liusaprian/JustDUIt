package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connection.Connect;
import controller.TransactionController;
import model.Transaction;

public class TransactionReportView extends JFrame {
	private JPanel mainPanel, tablePanel;
	private JTable table;
	private JScrollPane scroll;
	private JButton logout;
	private DefaultTableModel dtm;

	private Connect connect;

	public TransactionReportView() {
		setSize(1000, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Transactions Report");

		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1, 1));

		Object[] columns = { "Id", "Employee Id", "Payment Type", "Purchase Date" };
		dtm = new DefaultTableModel(columns, 0);
		table = new JTable(dtm);

		viewAllTransactionReport();

		scroll = new JScrollPane(table);
		tablePanel.add(scroll);

		mainPanel.add(tablePanel);
		add(mainPanel);

		setVisible(true);
	}

	private void viewAllTransactionReport() {
		Vector<Transaction> transactions = TransactionController.getInstance().getAllTransaction();

		for (Transaction transaction : transactions) {
			Vector<Object> row = new Vector<Object>();
			row.add(transaction.getId());
			row.add(transaction.getEmployeeId());
			row.add(transaction.getPaymentType());
			row.add(transaction.getPurchaseDate());
			dtm.addRow(row);
		}

		table.setModel(dtm);
	}

}
