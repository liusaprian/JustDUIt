package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import controller.CartItemController;
import controller.TransactionController;
import helper.Session;
import model.Transaction;

public class TransactionHistoryView extends JFrame {
	private JPanel mainPanel, headerPanel, tablePanel;
	private JTable table;
	private JScrollPane scroll;
	private JButton logout, back;
	private DefaultTableModel dtm;
	
	private CartItemController cartItemController = CartItemController.getInstance();
	
	public TransactionHistoryView() {
		setSize(1000, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Today's Transactions History");

		mainPanel = new JPanel(new BorderLayout());
		headerPanel = new JPanel(new GridLayout(1, 1));
		tablePanel = new JPanel(new GridLayout(1, 1));

		Object[] columns = { "Id", "Employee Id", "Payment Type", "Purchase Date" };
		dtm = new DefaultTableModel(columns, 0);
		table = new JTable(dtm);

		viewTodayTransactionReport();

		scroll = new JScrollPane(table);
		tablePanel.add(scroll);
		
		back = new JButton("Back");
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TransactionHistoryView.this.dispose();
				cartItemController.viewAddToCartForm();
			}
		});

		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(TransactionHistoryView.this);
			}
		});
		
		headerPanel.add(back);
		headerPanel.add(logout);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);

		add(mainPanel);

		setVisible(true);
	}

	private void viewTodayTransactionReport() {
		Vector<Transaction> transactions = TransactionController.getInstance().getTodayTransactionReport();

		for (Transaction transaction : transactions) {
			Vector<Object> row = new Vector<Object>();
			row.add(transaction.getId());
			row.add(transaction.getEmployeeId());
			row.add(transaction.getPaymentType());
			row.add(transaction.getPurchaseDate());
			dtm.addRow(row);
		}

		table.setModel(dtm);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				int row = table.rowAtPoint(e.getPoint());
				int transactionId = (int) table.getValueAt(row, 0);
				TransactionController.getInstance().viewTransactionDetail(TransactionHistoryView.this, transactionId);
			}
		});
	}

}
