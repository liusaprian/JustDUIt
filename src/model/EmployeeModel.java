package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connection.Connect;


public class EmployeeModel {
	
	private Connect connect;
	public static EmployeeModel employeem;
	public Integer EmployeeId;
	public Integer EmployeeRole_Id;
	public String EmployeeName;
	public String EmployeeUsername;
	public String EmployeeStatus;
	public Integer EmployeeSalary;
	public String EmployeePassword;
	
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
				Integer role_id;
				String name;
				String username;
				String status;
				Integer salary;
				String password;
				
				id = rs.getInt("id");
				role_id = rs.getInt("role_id");
				name = rs.getString("name");
				username = rs.getString("username");
				status = rs.getString("status");
				salary = rs.getInt("salary");
				password = rs.getString("password");
				
				employeeList.add(new EmployeeModel(id, role_id, name, username, status, salary, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return employeeList;
	}
	
	
	public EmployeeModel(int employeeId, int employeeRole_Id, String employeeName, String employeeUsername,
			String employeeStatus, int employeeSalary, String employeePassword) {
		super();
		this.EmployeeId = employeeId;
		this.EmployeeRole_Id = employeeRole_Id;
		this.EmployeeName = employeeName;
		this.EmployeeUsername = employeeUsername;
		this.EmployeeStatus = employeeStatus;
		this.EmployeeSalary = employeeSalary;
		this.EmployeePassword = employeePassword;
	}
	
	
	public EmployeeModel(int employeeRole_Id, String employeeName, String employeeUsername,
			String employeeStatus, int employeeSalary, String employeePassword) {
		this.EmployeeRole_Id = employeeRole_Id;
		this.EmployeeName = employeeName;
		this.EmployeeUsername = employeeUsername;
		this.EmployeeStatus = employeeStatus;
		this.EmployeeSalary = employeeSalary;
		this.EmployeePassword = employeePassword;
	}

	public int getEmployeeId() {
		return EmployeeId;
	}

	public int getEmployeeRole_Id() {
		return EmployeeRole_Id;
	}
	
	public String getEmployeeName() {
		return EmployeeName;
	}

	public String getEmployeeUsername() {
		return EmployeeUsername;
	}

	public String getEmployeeStatus() {
		return EmployeeStatus;
	}
	
	public void setEmployeeStatus(String status) {
		this.EmployeeStatus = status;
	}

	public int getEmployeeSalary() {
		return EmployeeSalary;
	}
	
	public String getEmployeePassword() {
		return EmployeePassword;
	}

	public void insertEmployee(EmployeeModel employee) {
		connect.executeUpdate("INSERT INTO employee VALUES("
				+ "NULL,"
				+ employee.getEmployeeName()+","
				+ employee.getEmployeeUsername()+","
				+ employee.getEmployeeSalary()+","
				+ "'" + employee.getEmployeeRole_Id()+"',"
				+ ")");
		
	}

	public void updateEmployee(EmployeeModel employee) {
		connect.executeUpdate("UPDATE employee SET "
				+ "role_id =  " + employee.getEmployeeRole_Id() + ","
				+ "name = '" + employee.getEmployeeName() + "',"
				+ "username = '" + employee.getEmployeeUsername() + "',"
				+ "salary = " + employee.getEmployeeSalary() + ","
				+ "status = '" + employee.getEmployeeStatus() + "',"
				+ "password = '" + employee.getEmployeePassword() + "'"
				+ " WHERE id = " + employee.getEmployeeId());
	}
	
	public EmployeeModel getEmployee(String username) {
		ResultSet rs;
		rs = connect.executeQuery("SELECT * FROM employee WHERE username = '" + username + "'");
		try {
			if(!rs.next()) return null;
			return new EmployeeModel(rs.getInt("id"), rs.getInt("role_id"), rs.getString("name"), username, rs.getString("status"), rs.getInt("salary"), rs.getString("password"));
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public EmployeeModel getEmployee(int id) {
		ResultSet rs;
		rs = connect.executeQuery("SELECT * FROM employee WHERE id = " + id);
		try {
			rs.next();
			return new EmployeeModel(id, rs.getInt("role_id"), rs.getString("name"), rs.getString("username"), rs.getString("status"), rs.getInt("salary"), rs.getString("password"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
