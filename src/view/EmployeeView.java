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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import controller.EmployeeController;
import controller.RoleController;
import helper.Session;
import model.EmployeeModel;

public class EmployeeView extends JFrame{
	
	private JPanel mainPanel, tablePanel, headerPanel, formPanel, buttonPanel, inputPanel;
	private JTextField EmployeeIdText, EmployeeRole_IdText, EmployeeUsernameText, EmployeeNameText, EmployeeSalaryText, EmployeeStatusText, EmployeePasswordText;
	private JButton logout, insertButton, updateButton, deleteButton, back;
	private JTable table;
	private JScrollPane scroll;
	private EmployeeController Employeec;
	private JLabel nameLabel, usernameLabel, salaryLabel, passwordLabel, roleLabel;
	private RoleController roleController = RoleController.getInstance();
	
	public EmployeeView(Vector<EmployeeModel> EmployeeList) {
		Employeec = EmployeeController.getInstance();
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Employee View");
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		headerPanel = new JPanel(new GridLayout(1,1));
		formPanel = new JPanel(new GridLayout(2,1));
		buttonPanel = new JPanel();
		inputPanel = new JPanel(new GridLayout(5,1)); 
		
		nameLabel = new JLabel("Name");
		usernameLabel = new JLabel("Username");
		salaryLabel = new JLabel("Salary");
		passwordLabel = new JLabel("Password");
		roleLabel = new JLabel("Role (1. Cashier, 2.Product Staff, 3.HR Staff, 4. Manager)");
		
		EmployeeIdText = new JTextField();
		EmployeeRole_IdText = new JTextField();
		EmployeeNameText = new JTextField();
		EmployeeUsernameText = new JTextField();
		EmployeeSalaryText = new JTextField();
		EmployeeStatusText = new JTextField();
		EmployeePasswordText = new JTextField();
		
		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(EmployeeView.this);
			}
		});
		insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String role_Id = EmployeeRole_IdText.getText().toString();
				String name = EmployeeNameText.getText().toString();
				String username = EmployeeUsernameText.getText().toString();
				String salary = EmployeeSalaryText.getText().toString();
				
				if(validate(role_Id, name, username, salary)) {
					Employeec.addEmployee(name, username, Integer.parseInt(salary), Integer.parseInt(role_Id));
					refresh();
				}
			}
		});
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = EmployeeIdText.getText().toString();
				String roleId = EmployeeRole_IdText.getText().toString();
				String name = EmployeeNameText.getText().toString();
				String username = EmployeeUsernameText.getText().toString();
				String status = EmployeeStatusText.getText().toString();
				String salary = EmployeeSalaryText.getText().toString();
				String password = EmployeePasswordText.getText().toString().equals("") ? username : EmployeePasswordText.getText().toString();
				
				if(validate(roleId, name, salary)) {
					Employeec.updateEmployee(Integer.parseInt(id), Integer.parseInt(roleId), name, username, status, Integer.parseInt(salary), password);
					refresh();
				}
			}
		});
		
		deleteButton = new JButton("Fire");
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = EmployeeIdText.getText().toString();
				Employeec.fireEmployee(Integer.parseInt(id));
				refresh();
			}
		});
		
		inputPanel.add(roleLabel);
		inputPanel.add(EmployeeRole_IdText);
		inputPanel.add(nameLabel);
		inputPanel.add(EmployeeNameText);
		inputPanel.add(usernameLabel);
		inputPanel.add(EmployeeUsernameText);
		inputPanel.add(salaryLabel);
		inputPanel.add(EmployeeSalaryText);
		inputPanel.add(passwordLabel);
		inputPanel.add(EmployeePasswordText);
		
		buttonPanel.add(insertButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		
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

		if(roleController.getRole(Session.getSession().getCurrentUser().getEmployeeRole_Id()).getName().equals("manager")) {
			back = new JButton("Back");
			back.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					EmployeeView.this.dispose();
					AdminController.getInstance().getAdminMenu();
				}
			});
			headerPanel.add(back);
		}
		
		headerPanel.add(logout);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(formPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		setVisible(true);
	}

	public void prepareTableModel(Vector<EmployeeModel> Employees) {
		String[] col = {"Id","Role Id", "Name", "Username", "Salary", "Status", "Password"};
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
	
	public void refresh() {
		Vector<EmployeeModel> updatedEmployees = new Vector<EmployeeModel>();  
		updatedEmployees = Employeec.getAllEmployee();
		prepareTableModel(updatedEmployees);
	}
	
	public boolean validate(String roleId, String name, String username, String salary) {
		if(roleId.equals("") || name.equals("") || username.equals("") || salary.equals("")) {
			JOptionPane.showMessageDialog(null, "Fields must not be empty");
			return false;
		}
		int roleIdNum, salaryNum;
		try {
			roleIdNum = Integer.parseInt(roleId);
			salaryNum = Integer.parseInt(salary);
		} catch(NumberFormatException e) {
			EmployeeRole_IdText.setText("");
			EmployeeSalaryText.setText("");
			JOptionPane.showMessageDialog(null, "Role Id & Salary must be numeric");
			return false;
		}
		if(roleController.getRole(roleIdNum) == null) {
			EmployeeRole_IdText.setText("");
			JOptionPane.showMessageDialog(null, "Role Id must exist");
			return false;
		}
		if(Employeec.getEmployee(username) != null) {
			EmployeeUsernameText.setText("");
			JOptionPane.showMessageDialog(null, "Username must be unique");
			return false;
		}
		if(salaryNum <= 0) {
			EmployeeSalaryText.setText("");
			JOptionPane.showMessageDialog(null, "Salary must be above zero");
			return false;
		}
		return true;
	}
	
	public boolean validate(String roleId, String name, String salary) {
		if(roleId.equals("") || name.equals("") || salary.equals("")) {
			JOptionPane.showMessageDialog(null, "Fields must not be empty");
			return false;
		}
		int roleIdNum, salaryNum;
		try {
			roleIdNum = Integer.parseInt(roleId);
			salaryNum = Integer.parseInt(salary);
		} catch(NumberFormatException e) {
			EmployeeRole_IdText.setText("");
			EmployeeSalaryText.setText("");
			JOptionPane.showMessageDialog(null, "Role Id & Salary must be numeric");
			return false;
		}
		if(roleController.getRole(roleIdNum) == null) {
			EmployeeRole_IdText.setText("");
			JOptionPane.showMessageDialog(null, "Role Id must exist");
			return false;
		}
		if(salaryNum <= 0) {
			EmployeeSalaryText.setText("");
			JOptionPane.showMessageDialog(null, "Salary must be above zero");
			return false;
		}
		return true;
	}
}
