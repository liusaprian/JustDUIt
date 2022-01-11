package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connection.Connect;
import controller.TransactionController;
import helper.Session;
import model.Transaction;

public class TransactionReportView extends JFrame {
	private JPanel mainPanel, headerPanel, tablePanel, footerPanel;
	private JTable table;
	private JScrollPane scroll;
	private JButton logout, submit;
	private JLabel monthLabel, yearLabel;
	private JTextField txtMonth, txtYear;
	private DefaultTableModel dtm;

	public TransactionReportView(String month, String year) {
		setSize(1000, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Transactions Report");

		mainPanel = new JPanel(new BorderLayout());
		headerPanel = new JPanel(new GridLayout(1, 1));
		tablePanel = new JPanel(new GridLayout(1, 1));
		footerPanel = new JPanel(new GridLayout(1, 2));

		monthLabel = new JLabel("Month");
		yearLabel = new JLabel("Year");
		txtMonth = new JTextField();
		txtYear = new JTextField();
		submit = new JButton("Submit");
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String monthInp = txtMonth.getText().toString();
				String yearInp = txtYear.getText().toString();
				TransactionReportView.this.dispose();
				TransactionController.getInstance().viewTransactionReport(monthInp, yearInp);
			}
		});

		Object[] columns = { "Id", "Employee Id", "Payment Type", "Purchase Date" };
		dtm = new DefaultTableModel(columns, 0);
		table = new JTable(dtm);

		viewAllTransactionReport(month, year);

		scroll = new JScrollPane(table);
		tablePanel.add(scroll);

		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(TransactionReportView.this);
			}
		});
		
		headerPanel.add(logout);
		footerPanel.add(monthLabel);
		footerPanel.add(txtMonth);
		footerPanel.add(yearLabel);
		footerPanel.add(txtYear);
		footerPanel.add(submit);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);

		add(mainPanel);

		setVisible(true);
	}

	private void viewAllTransactionReport(String month, String year) {
		Vector<Transaction> transactions = TransactionController.getInstance().getAllTransaction(month, year);

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
				TransactionController.getInstance().viewTransactionDetail(transactionId);
			}
		});
	}

}
