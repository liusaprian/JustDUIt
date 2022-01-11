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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.EmployeeController;
import model.EmployeeModel;

public class EmployeeView extends JFrame{

	
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel, tablePanel, headerPanel, formPanel, buttonPanel, inputPanel;
	private JTextField EmployeeIdText, EmployeeRole_IdText, EmployeeUsernameText, EmployeeNameText, EmployeeSalaryText, EmployeeStatusText, EmployeePasswordText;
	private JButton logout, insertButton, updateButton;
	private JTable table;
	private JScrollPane scroll;
	private EmployeeController Employeec;
	
	public EmployeeView(Vector<EmployeeModel> EmployeeList) {
		Employeec = EmployeeController.getEmployeeController();
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Employee View");
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		headerPanel = new JPanel(new GridLayout(1,1));
		formPanel = new JPanel(new GridLayout(2,1));
		buttonPanel = new JPanel();
		inputPanel = new JPanel(); 
		
		EmployeeIdText = new JTextField();
		EmployeeRole_IdText = new JTextField();
		EmployeeNameText = new JTextField();
		EmployeeNameText.setColumns(20);
		EmployeeUsernameText = new JTextField();
		EmployeeUsernameText.setColumns(20);
		EmployeeSalaryText = new JTextField();
		EmployeeStatusText = new JTextField();
		EmployeeStatusText.setColumns(20);
		EmployeeStatusText = new JTextField();
		EmployeeStatusText.setColumns(20);
		
		logout = new JButton("Logout");
		insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String role_Id = EmployeeRole_IdText.getText().toString();
				String name = EmployeeNameText.getText().toString();
				String username = EmployeeUsernameText.getText().toString();
				String salary = EmployeeSalaryText.getText().toString();
				
				
				Employeec.addEmployee(username, name, Integer.parseInt(role_Id), Integer.parseInt(salary));
				Vector<EmployeeModel> updatedEmployees = new Vector<EmployeeModel>();  
				updatedEmployees = Employeec.getAllEmployee();
				prepareTableModel(updatedEmployees);
			}
		});
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = EmployeeIdText.getText().toString();
				String name = EmployeeNameText.getText().toString();
				String salary = EmployeeSalaryText.getText().toString();
				String password = EmployeePasswordText.getText().toString();
				
				Employeec.updateEmployee(Integer.parseInt(id), name, salary, password);
				Vector<EmployeeModel> updatedEmployees = new Vector<EmployeeModel>();  
				updatedEmployees = Employeec.getAllEmployee();
				prepareTableModel(updatedEmployees);
			}
		});
		
		
		inputPanel.add(EmployeeIdText);
		inputPanel.add(EmployeeRole_IdText);
		inputPanel.add(EmployeeNameText);
		inputPanel.add(EmployeeUsernameText);
		inputPanel.add(EmployeeSalaryText);
		inputPanel.add(EmployeeStatusText);
		inputPanel.add(EmployeePasswordText);
		
		buttonPanel.add(insertButton);
		buttonPanel.add(updateButton);
		
		formPanel.add(inputPanel);
		formPanel.add(buttonPanel);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row = table.rowAtPoint(e.getPoint());
				
				EmployeeIdText.setText(table.getValueAt(row, 0).toString());
				EmployeeRole_IdText.setText(table.getValueAt(row, 1).toString());
				EmployeeNameText.setText(table.getValueAt(row, 2).toString());
				EmployeeUsernameText.setText(table.getValueAt(row, 3).toString());
				EmployeeSalaryText.setText(table.getValueAt(row, 4).toString());
				EmployeeStatusText.setText(table.getValueAt(row, 5).toString());
				EmployeePasswordText.setText(table.getValueAt(row, 6).toString());
			}
		});
		
		prepareTableModel(EmployeeList);
		
		scroll = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tablePanel.add(scroll);
		
		headerPanel.add(logout);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(formPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		setVisible(true);
	}

	public void prepareTableModel(Vector<EmployeeModel> Employees) {
		String[] col = {"Id","Role_Id", "Name", "Username", "Salary", "Status", "Password"};
		DefaultTableModel dtm = new DefaultTableModel(null, col);
		for (EmployeeModel p : Employees)
			dtm.addRow(new Object[] {
					p.getEmployeeId(),
					p.getEmployeeRole_Id(),
					p.getEmployeeName(),
					p.getEmployeeUsername(),
					p.getEmployeeSalary(),
					p.getEmployeeStatus(),
					p.getEmployeePassword()
			});
		
		table.setModel(dtm);
	}
}
