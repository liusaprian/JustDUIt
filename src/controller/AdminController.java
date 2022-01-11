package controller;

import view.AdminMenu;

public class AdminController {
	
	private static AdminController controller;
	
	public static AdminController getInstance() {
		if(controller == null) controller = new AdminController();
		return controller;
	}
	
	public void getAdminMenu() {
		new AdminMenu();
	}
}
