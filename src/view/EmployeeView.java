package view;

import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.EmployeeModel;


public class EmployeeView extends JFrame{
	
	private JPanel mainPanel;

	
	public void EmployeeModel (Vector<EmployeeModel> employeeList){
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Employee View");
		
		mainPanel = new JPanel(); 
		
		//masih belum selesai
		
		add(mainPanel);
		setVisible(true);
	}
}