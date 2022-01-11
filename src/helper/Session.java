package helper;

import javax.swing.JFrame;

import model.EmployeeModel;
import view.LoginView;

public class Session {

	private static EmployeeModel currentEmployee;
	private static Session session;
	
	private Session() {}
	
	public void login(EmployeeModel emp) {
		currentEmployee = emp;
	}
	
	public EmployeeModel getCurrentUser() {
		return currentEmployee;
	}
	
	public void logout(JFrame frame) {
		frame.dispose();
		currentEmployee = null;
		new LoginView();
	}

	public static Session getSession() {
		if(session == null) session = new Session();
		return session;
	}
}
