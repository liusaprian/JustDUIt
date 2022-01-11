package controller;

import java.util.Vector;

import model.EmployeeModel;
import view.EmployeeView;

public class EmployeeController {

	private static EmployeeController Employeec;
	private EmployeeModel Employeem;
	
	public static EmployeeController getEmployeeController() {
		if(Employeec == null) {
			Employeec = new EmployeeController();
		}
		return Employeec;
	}
	
	private EmployeeController() {
		Employeem = EmployeeModel.getEmployeeModel();
	}

	public Vector<EmployeeModel> getAllEmployee(){
		Vector<EmployeeModel> EmployeeList = Employeem.getAllEmployee();
		return EmployeeList;
	}
	
	public void getEmployeeView() {
		Vector<EmployeeModel> productList = Employeem.getAllEmployee();
		new EmployeeView(productList);
	}

	
	public void addEmployee(String name, String username, Integer salary, Integer role_Id) {
		EmployeeModel employee = new EmployeeModel(name, username, salary, role_Id);
		Employeem.insertEmployee(employee);
	}

	public void updateEmployee(Integer id, String name, String salary, String password) {
		EmployeeModel employee = new EmployeeModel(id, id, name, salary, password);
		Employeem.updateEmployee(employee);
	}
	
	

	
}
