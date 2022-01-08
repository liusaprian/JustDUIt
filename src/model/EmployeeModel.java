package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connection.Connect;


public class EmployeeModel {
	
	private Connect connect;
	public static EmployeeModel employeem;
	public int EmployeeId;
	public String EmployeeRole;
	public String EmployeeName;
	public String EmployeeUsername;
	public String EmployeeStatus;
	public int EmployeeSalary;
	
	public static EmployeeModel getEmployeeModel() {
		if(employeem == null) {
			employeem = new EmployeeModel();
		}
		return employeem;
	}
	
	private EmployeeModel(){
		
		connect = Connect.getConnection();
	}
	
	
	
	public  Vector<EmployeeModel> getAllEmployee(){
		Vector<EmployeeModel> employeeList = new Vector<>();
		ResultSet rs;
		
		rs = connect.executeQuery("SELECT * FROM employee");
		
		try {
			while(rs.next()) {
				Integer id;
				String role_id;
				String name;
				String username;
				String status;
				Integer salary;
				
				id = rs.getInt("Id");
				role_id = rs.getString("Role_id");
				name = rs.getString("Name");
				username = rs.getString("Username");
				status = rs.getString("Status");
				salary = rs.getInt("Salary");
				
				employeeList.add(new EmployeeModel(id, role_id, name, username, status, salary));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return employeeList;
	}
	
	
	public EmployeeModel(int employeeId, String employeeRole, String employeeName, String employeeUsername,
			String employeeStatus, int employeeSalary) {
		super();
		EmployeeId = employeeId;
		EmployeeRole = employeeRole;
		EmployeeName = employeeName;
		EmployeeUsername = employeeUsername;
		EmployeeStatus = employeeStatus;
		EmployeeSalary = employeeSalary;
	}

	public int getEmployeeId() {
		return EmployeeId;
	}

	public void setEmployeeId(int employeeId) {
		EmployeeId = employeeId;
	}

	public String getEmployeeRole() {
		return EmployeeRole;
	}

	public void setEmployeeRole(String employeeRole) {
		EmployeeRole = employeeRole;
	}

	public String getEmployeeName() {
		return EmployeeName;
	}

	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}

	public String getEmployeeUsername() {
		return EmployeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		EmployeeUsername = employeeUsername;
	}

	public String getEmployeeStatus() {
		return EmployeeStatus;
	}

	public void setEmployeeStatus(String employeeStatus) {
		EmployeeStatus = employeeStatus;
	}

	public int getEmployeeSalary() {
		return EmployeeSalary;
	}

	public void setEmployeeSalary(int employeeSalary) {
		EmployeeSalary = employeeSalary;
	}
	
	
	


}