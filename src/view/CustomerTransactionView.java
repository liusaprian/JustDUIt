package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import connection.Connect;

public class CustomerTransactionView extends JFrame {
	private JPanel mainPanel, headerPanel, tablePanel, footerPanel;
	private JTable table;
	private JScrollPane scroll;
	private Connect connect;
	private JButton cart, logout, payment;
	private JLabel cartItemCount;
	
	private static CustomerTransactionView view = null;
	
	private CustomerTransactionView() {}
	
	public static CustomerTransactionView getInstance() {
		if(view == null) view = new CustomerTransactionView();
		return view;
	}

	public void showTransactionCustomer() {
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Add to Cart Form");
		
		connect = Connect.getConnection();
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		headerPanel = new JPanel(new GridLayout(1,1));
		footerPanel = new JPanel(new GridLayout(1,2));
		
		cart = new JButton("Cart");
		logout = new JButton("Logout");
		payment = new JButton("Pay");
		
		cartItemCount = new JLabel();
		cartItemCount.setHorizontalAlignment(JLabel.CENTER);
		
		table = new JTable();
		prepareTableModel();
		
		scroll = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scroll);
		table.getColumn("action").setCellRenderer(new ButtonRenderer());
		table.getColumn("action").setCellEditor(new ButtonEditor(new JTextField()));
		
		footerPanel.add(cartItemCount);
		footerPanel.add(payment);
		
		headerPanel.add(cart);
		headerPanel.add(logout);
		
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(footerPanel, BorderLayout.SOUTH);
		add(mainPanel);
		
		setVisible(true);
	}
	
	public void prepareTableModel() {
		DefaultTableModel dtm = new DefaultTableModel();
		ResultSet rs;
		ResultSetMetaData rsmd;
		
		try {
			rs = connect.executeQuery("SELECT * FROM product");
			rsmd = rs.getMetaData();
			
			for(int i = 1; i <= rsmd.getColumnCount(); i++) {
				dtm.addColumn(rsmd.getColumnName(i));
			}
			cartItemCount.setText(String.valueOf(rsmd.getColumnCount()) + " items in your cart");
			dtm.addColumn("action");
			while(rs.next()) {
				dtm.addRow(new Object[] {
						rs.getInt("id"),
						rs.getString("name"),
						rs.getString("description"),
						rs.getInt("price"),
						rs.getInt("stock"),
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		table.setModel(dtm);
	}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
		  setOpaque(true);
		}
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		  setText((value == null) ? "Buy" : value.toString());
		  return this;
		}
	}
	
	class ButtonEditor extends DefaultCellEditor {
		protected JButton btn;
		private String label;
		private boolean clicked;
		private int row;
		
		public ButtonEditor(JTextField textField) {
			super(textField);
			btn = new JButton();
			btn.setOpaque(true);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					fireEditingStopped();
				}
			});
		}
		
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			label = (value == null) ? "Buy" : value.toString();
			btn.setText(label);
			clicked = true;
			this.row = row;
			return btn;
		}
		
		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			if(clicked) {
				JOptionPane.showMessageDialog(btn, table.getValueAt(row, 0));
			}
			clicked = false;
			return new String(label);
		}
		
		@Override
		public boolean stopCellEditing() {
			// TODO Auto-generated method stub
			clicked = false;
			return super.stopCellEditing();
		}
		
		@Override
		protected void fireEditingStopped() {
			// TODO Auto-generated method stub
			super.fireEditingStopped();
		}
	}

}
