package controller;

import java.util.Vector;

import model.Role;

public class RoleController {
	
	private static RoleController roleController = null;
	private Role roleModel;

	private RoleController() {
		roleModel = Role.getRole();
	}
	
	public static RoleController getInstance() {
		if(roleController == null) roleController = new RoleController();
		return roleController;
	}
	
	public Vector<Role> getAllRole() {
		return roleModel.getAllRole();
	}
	
	public Role getRole(int id) {
		return roleModel.getRole(id);
	}

}
