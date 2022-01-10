package controller;

import java.util.Vector;
import model.EmployeeModel;
import view.EmployeeView;

public class EmployeeController {
	
	private static EmployeeController employeec = null;
	private EmployeeModel employeem;
	
	
	public static EmployeeController getIntance() {
		if(employeec == null) {
			employeec = new EmployeeController();
		}
		return employeec;
	}
	
	private EmployeeController() {
		employeem = EmployeeModel.getEmployeeModel();
	}
	
	//masih belum selesai
	
	public void getEmployeeView() {
		Vector<EmployeeModel> employeeList= employeem.getAllEmployee();
//		new EmployeeView(employeeList);
		new EmployeeView();
	}
}