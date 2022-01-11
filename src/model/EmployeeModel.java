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
				
				id = rs.getInt("Id");
				role_id = rs.getInt("Role_id");
				name = rs.getString("Name");
				username = rs.getString("Username");
				status = rs.getString("Status");
				salary = rs.getInt("Salary");
				password = rs.getString("Password");
				
				employeeList.add(new EmployeeModel(id, role_id, name, username, status, salary, password));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return employeeList;
	}
	
	
	public EmployeeModel(Integer employeeId, Integer employeeRole_Id, String employeeName, String employeeUsername,
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
	

	public EmployeeModel(Integer id, Integer Role_Id, String username, String salary, String password) {
		super();
		this.EmployeeId = getEmployeeId();
		this.EmployeeRole_Id = getEmployeeRole_Id();
		this.EmployeeName = getEmployeeName();
		this.EmployeeUsername = getEmployeeUsername();
		this.EmployeeSalary = getEmployeeSalary();
	}

	public EmployeeModel(String name, String username, Integer salary, Integer role_Id) {
		super();
		this.EmployeeName = getEmployeeName();
		this.EmployeeUsername = getEmployeeUsername();
		this.EmployeeStatus = getEmployeeStatus();
		this.EmployeeSalary = getEmployeeSalary();
		this.EmployeePassword = getEmployeePassword();
	}

	public int getEmployeeId() {
		return EmployeeId;
	}

	public void setEmployeeId(int employeeId) {
		EmployeeId = employeeId;
	}

	public int getEmployeeRole_Id() {
		return EmployeeRole_Id;
	}

	public void setEmployeeRole_Id(int employeeRole) {
		EmployeeRole_Id = employeeRole;
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
	
	public String getEmployeePassword() {
		return EmployeePassword;
	}

	public void setEmployeePassword(String employeePassword) {
		EmployeePassword = employeePassword;
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
		connect.executeUpdate("INSERT INTO employee VALUES("
				+ "NULL,"
				+ employee.getEmployeeId()+","
				+ employee.getEmployeeName()+","
				+ employee.getEmployeeSalary()+","
				+ "'" + employee.getEmployeePassword()+"',"
				+ ")");
		
		
	}
	
	

	


}
