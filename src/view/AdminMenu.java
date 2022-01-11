package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.EmployeeController;
import controller.TransactionController;
import helper.Session;

public class AdminMenu extends JFrame {
	private JPanel mainPanel, titlePanel, bodyPanel;
	private JButton logoutButton, manageEmployeeButton, transactionReportButton;
	private JLabel title;

	private EmployeeController employeeController = EmployeeController.getInstance();
	private TransactionController transactionController = TransactionController.getInstance();
	
	public AdminMenu() {
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Admin Menu");

		mainPanel = new JPanel(new BorderLayout());
		titlePanel = new JPanel(new GridLayout(2, 1));
		bodyPanel = new JPanel(new GridLayout(3, 1));

		title = new JLabel("Admin Menu");
		title.setHorizontalAlignment(JLabel.CENTER);

		logoutButton = new JButton("Logout");
		logoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(AdminMenu.this);
			}
		});

		manageEmployeeButton = new JButton("Manage Employee");
		manageEmployeeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AdminMenu.this.dispose();
				employeeController.getEmployeeView();
			}
		});

		transactionReportButton = new JButton("Transaction Report");
		transactionReportButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminMenu.this.dispose();
				transactionController.viewTransactionReport(null, null);
			}
		});
		
		titlePanel.add(title);
		bodyPanel.add(manageEmployeeButton);
		bodyPanel.add(transactionReportButton);
		bodyPanel.add(logoutButton);
		
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(bodyPanel, BorderLayout.CENTER);
		add(mainPanel);
		
		setVisible(true);
	}
}
