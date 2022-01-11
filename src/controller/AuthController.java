package controller;

import java.util.Arrays;

import javax.swing.JFrame;

import connection.Connect;
import helper.Session;
import model.EmployeeModel;
import model.Role;

public class AuthController {
	
	private Connect conn = Connect.getConnection();
	
	private EmployeeController employeeController = EmployeeController.getInstance();
	private RoleController roleController = RoleController.getInstance();
	private ProductController productController = ProductController.getInstance();
	private CartItemController cartItemController = CartItemController.getInstance();
	private AdminController adminController = AdminController.getInstance();
	private static AuthController controller = null;
	private AuthController() {}
	
	private Session session = Session.getSession();

	public static AuthController getInstance() {
		if(controller == null) controller = new AuthController();
		return controller;
	}
	
	public String login(String username, char[] password) {
		EmployeeModel employee = EmployeeModel.getEmployeeModel().getEmployee(username);
		if(employee == null) return "Username not found";
		else if(!Arrays.equals(password, employee.getEmployeePassword().toCharArray())) return "Crendentials doesn\'t match";
		session.login(employee);
		return "Success Login";
	}
	
	public void goToRegister(JFrame frame) {
		frame.dispose();
		//register view
	}
	
	public void navigateView(JFrame frame) {
		frame.dispose();
		Role role = roleController.getRole(session.getCurrentUser().getEmployeeRole_Id());
		switch (role.getName()) {
		case "cashier":
			cartItemController.viewAddToCartForm();
			break;
		case "product":
			productController.getProductDataView();
			break;
		case "human resource":
			employeeController.getEmployeeView();
			break;
		case "manager":
			adminController.getAdminMenu();
			break;
		default:
			break;
		}
	}
	
}