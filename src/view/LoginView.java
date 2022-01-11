package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.AuthController;

public class LoginView extends JFrame {
	private JPanel mainPanel, titlePanel, formPanel, buttonPanel;
	private JButton login, register;
	private JTextField username;
	private JPasswordField password;
	private JLabel title, usernameLabel, passwordLabel, msg;
	private AuthController authController = AuthController.getInstance();

	public LoginView() {
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Login");
		
		mainPanel = new JPanel(new BorderLayout());
		titlePanel = new JPanel(new GridLayout(2,1));
		formPanel = new JPanel(new GridLayout(2,2));
		buttonPanel = new JPanel(new GridLayout(1,2));
		
		title = new JLabel("Login");
		title.setHorizontalAlignment(JLabel.CENTER);
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		msg = new JLabel();
		
		username = new JTextField();
		password = new JPasswordField();
		
		login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = authController.login(username.getText().toString(), password.getPassword());
				msg.setText(message);
				if(message == "Success Login") authController.navigateView(LoginView.this);
			}
		});
		register = new JButton("Register");
		
		titlePanel.add(title);
		titlePanel.add(msg);
		formPanel.add(usernameLabel);
		formPanel.add(username);
		formPanel.add(passwordLabel);
		formPanel.add(password);
		buttonPanel.add(login);
		buttonPanel.add(register);
		
		mainPanel.add(titlePanel, BorderLayout.NORTH);
		mainPanel.add(formPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		add(mainPanel);
		
		setVisible(true);
	}

}
