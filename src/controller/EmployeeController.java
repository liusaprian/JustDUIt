package controller;

import java.util.Vector;

import model.EmployeeModel;
import view.EmployeeView;

public class EmployeeController {

	private static EmployeeController controller;
	private EmployeeModel Employeem;
	
	public static EmployeeController getInstance() {
		if(controller == null) controller = new EmployeeController();
		return controller;
	}
	
	private EmployeeController() {
		Employeem = EmployeeModel.getEmployeeModel();
	}

	public Vector<EmployeeModel> getAllEmployee(){
		Vector<EmployeeModel> EmployeeList = Employeem.getAllEmployee();
		return EmployeeList;
	}
	
	public void getEmployeeView() {
		Vector<EmployeeModel> employeeList = Employeem.getAllEmployee();
		new EmployeeView(employeeList);
	}
	
	public void addEmployee(String name, String username, int salary, int role_Id) {
		EmployeeModel employee = new EmployeeModel(role_Id, name, username, "active", salary, username);
		Employeem.insertEmployee(employee);
	}

	public void updateEmployee(int id, int roleId, String name, String username, String status, int salary, String password) {
		EmployeeModel employee = new EmployeeModel(id, roleId, name, username, status, salary, password);
		Employeem.updateEmployee(employee);
	}
	
	public void fireEmployee(int id) {
		EmployeeModel employee = Employeem.getEmployee(id);
		employee.setEmployeeStatus("not active");
		Employeem.updateEmployee(employee);
	}
	
	public EmployeeModel getEmployee(String username) {
		EmployeeModel employee = Employeem.getEmployee(username);
		return employee;
	}
}
