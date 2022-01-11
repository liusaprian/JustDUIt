package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connection.Connect;

public class Role {
	private Connect conn;
	private static Role roleModel = null;
	
	public static Role getRole() {
		if(roleModel == null) roleModel = new Role();
		return roleModel;
	}
	
	public Role() {
		conn = Connect.getConnection();
	}
	
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Role(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public Vector<Role> getAllRole() {
		ResultSet rs;
		rs = conn.executeQuery("SELECT * FROM role");
		Vector<Role> roles = new Vector<>();
		try {
			while(rs.next()) 
				roles.add(new Role(rs.getInt("id"), rs.getString("name")));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return roles;
	}
	
	public Role getRole(int id) {
		ResultSet rs;
		rs = conn.executeQuery("SELECT * FROM role WHERE id = " + id);
		try {
			if(!rs.next()) return null;
			return new Role(id, rs.getString("name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
