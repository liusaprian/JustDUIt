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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connection.Connect;
import controller.RoleController;
import controller.TransactionController;
import helper.Session;
import model.Transaction;
import model.TransactionItem;

public class TransactionDetailView extends JFrame{
	private JPanel mainPanel, tablePanel, titlePanel, headerPanel;
	private JTable table;
	private JScrollPane scroll;
	private JLabel title;
	private JButton logoutButton, viewTransactionReportButton;
	private DefaultTableModel dtm;
	
	private TransactionController transactionController = TransactionController.getInstance();
	private RoleController roleController = RoleController.getInstance();
	
	private String currentRole;

	public TransactionDetailView(int id) {
		setSize(1000, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Transaction Detail");

		mainPanel = new JPanel(new BorderLayout());
		titlePanel = new JPanel(new GridLayout(1, 1));
		headerPanel = new JPanel(new GridLayout(1, 2));
		tablePanel = new JPanel(new GridLayout(1, 1));

		Object[] columns = { "Product Id", "Quantity" };
		dtm = new DefaultTableModel(columns, 0);
		table = new JTable(dtm);

		viewTransactionItems(id);

		scroll = new JScrollPane(table);
		tablePanel.add(scroll);
		
		title = new JLabel("Transaction Detail");
		
		logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(TransactionDetailView.this);
			}
		});
		
		viewTransactionReportButton = new JButton("Back");
		viewTransactionReportButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TransactionDetailView.this.dispose();
				if(roleController.getRole(Session.getSession().getCurrentUser().getEmployeeRole_Id()).getName().equals("manager")) {
					transactionController.viewTransactionReport(null, null);
				}
				else transactionController.viewTodayTransaction();
			}
		});
		
		titlePanel.add(title);
		headerPanel.add(viewTransactionReportButton);
		headerPanel.add(logoutButton);
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(headerPanel, BorderLayout.CENTER);
		mainPanel.add(tablePanel, BorderLayout.SOUTH);
		add(mainPanel);

		setVisible(true);
	}

	private void viewTransactionItems(int id) {
		Vector<TransactionItem> transactionItems = transactionController.getAllTransactionItems(id);

		for (TransactionItem transactionItem : transactionItems) {
			Vector<Object> row = new Vector<Object>();
			row.add(transactionItem.getProductId());
			row.add(transactionItem.getQuantity());
			dtm.addRow(row);
		}

		table.setModel(dtm);
	}
}
