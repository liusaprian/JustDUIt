package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

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
import controller.CartItemController;
import model.CartItem;
import model.ProductModel;

public class AddToCartView extends JFrame {
	private JPanel mainPanel, headerPanel, tablePanel;
	private JTable table;
	private JScrollPane scroll;
	private Connect connect;
	private JButton cart, logout, payment;
	private JLabel cartItemCount;
	private JTextField quantity;
	
	private static AddToCartView view = null;
	
	private AddToCartView() {}
	
	public static AddToCartView getInstance() {
		if(view == null) view = new AddToCartView();
		return view;
	}

	public void showAddToCartForm(Vector<ProductModel> products) {
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Add to Cart Form");
		
		connect = Connect.getConnection();
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		headerPanel = new JPanel(new GridLayout(1,1));
		
		cart = new JButton("Cart (0)");
		cart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				CartItemController cartController = CartItemController.getInstance();
				cartController.viewManageCartForm();
			}
		});
		logout = new JButton("Logout");
		payment = new JButton("Pay");
		
		quantity = new JTextField();
		
		cartItemCount = new JLabel();
		cartItemCount.setHorizontalAlignment(JLabel.CENTER);
		
		table = new JTable();
		prepareTableModel(products);
		
		scroll = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		tablePanel.add(scroll);
		table.getColumn("Action").setCellRenderer(new ButtonRenderer());
		table.getColumn("Action").setCellEditor(new ButtonEditor(new JTextField()));
		
		headerPanel.add(cart);
		headerPanel.add(logout);
		
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		add(mainPanel);
		
		setVisible(true);
	}
	
	public void prepareTableModel(Vector<ProductModel> products) {
		String[] columns = {"#", "Name", "Description", "Price", "Stock", "Action"};
		DefaultTableModel dtm = new DefaultTableModel(null, columns);
		for (ProductModel p : products)
			dtm.addRow(new Object[] {
					p.getProductId(),
					p.getProductName(),
					p.getProductDescription(),
					p.getProductPrice(),
					p.getProdcutStock()
			});
		
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
				Object[] quantityInput = {"Quantity: ", quantity};
				int option = JOptionPane.showConfirmDialog(btn, quantityInput, "Buy " + table.getValueAt(row, 1), JOptionPane.OK_CANCEL_OPTION);
				if(option == JOptionPane.OK_OPTION) {
					int qtyInputted = Integer.parseInt(quantity.getText());
					CartItemController cartController = CartItemController.getInstance();
					CartItem itemAdded = cartController.addToCart(Integer.parseInt(table.getValueAt(row, 0).toString()), qtyInputted);
					if(itemAdded == null) 
						JOptionPane.showMessageDialog(btn, "Product stock is not enough");
					else {
						cart.setText("Cart ("+ cartController.cartItemCount() +")");
						JOptionPane.showMessageDialog(btn, qtyInputted + " " + table.getValueAt(row, 1) + " added to cart");
					}
				}
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
