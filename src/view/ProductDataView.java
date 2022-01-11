package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.ProductController;
import helper.Session;
import model.ProductModel;

public class ProductDataView extends JFrame{

	private JPanel mainPanel, tablePanel, headerPanel, formPanel, buttonPanel, inputPanel;
	private JTextField productIdText, productNameText, productDescText, productPriceText, productStockText;
	private JButton logout, insertButton, updateButton, deleteButton;
	private JTable table;
	private JScrollPane scroll;
	private ProductController productc;
	
	public ProductDataView(Vector<ProductModel> productList) {
		productc = ProductController.getProductController();
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Product Data View");
		
		mainPanel = new JPanel(new BorderLayout());
		tablePanel = new JPanel(new GridLayout(1,1));
		headerPanel = new JPanel(new GridLayout(1,1));
		formPanel = new JPanel(new GridLayout(2,1));
		buttonPanel = new JPanel();
		inputPanel = new JPanel(); 
		
		productIdText = new JTextField();
		productNameText = new JTextField();
		productNameText.setColumns(20);
		productDescText = new JTextField();
		productDescText.setColumns(20);
		productPriceText = new JTextField();
		productPriceText.setColumns(10);
		productStockText = new JTextField();
		productStockText.setColumns(5);
		
		logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Session.getSession().logout(ProductDataView.this);
			}
		});
		insertButton = new JButton("Insert");
		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = productNameText.getText().toString();
				String desc = productDescText.getText().toString();
				String price = productPriceText.getText().toString();
				String stock = productStockText.getText().toString();
				
				productc.addProduct(name, desc, Integer.parseInt(price), Integer.parseInt(stock));
				Vector<ProductModel> updatedProducts = new Vector<ProductModel>();  
				updatedProducts = productc.getAllProduct();
				prepareTableModel(updatedProducts);
			}
		});
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = productIdText.getText().toString();
				String name = productNameText.getText().toString();
				String desc = productDescText.getText().toString();
				String price = productPriceText.getText().toString();
				String stock = productStockText.getText().toString();
				
				productc.updateProduct(Integer.parseInt(id), name, desc, Integer.parseInt(price), Integer.parseInt(stock));
				Vector<ProductModel> updatedProducts = new Vector<ProductModel>();  
				updatedProducts = productc.getAllProduct();
				prepareTableModel(updatedProducts);
			}
		});
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String id = productIdText.getText().toString();
				
				productc.deleteProduct(Integer.parseInt(id));
				Vector<ProductModel> updatedProducts = new Vector<ProductModel>();  
				updatedProducts = productc.getAllProduct();
				prepareTableModel(updatedProducts);
			}
		});
		
		inputPanel.add(productNameText);
		inputPanel.add(productDescText);
		inputPanel.add(productPriceText);
		inputPanel.add(productStockText);
		
		buttonPanel.add(insertButton);
		buttonPanel.add(updateButton);
		buttonPanel.add(deleteButton);
		
		formPanel.add(inputPanel);
		formPanel.add(buttonPanel);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				int row = table.rowAtPoint(e.getPoint());
				
				productIdText.setText(table.getValueAt(row, 0).toString());
				productNameText.setText(table.getValueAt(row, 1).toString());
				productDescText.setText(table.getValueAt(row, 2).toString());
				productPriceText.setText(table.getValueAt(row, 3).toString());
				productStockText.setText(table.getValueAt(row, 4).toString());
			}
		});
		
		prepareTableModel(productList);
		
		scroll = new JScrollPane(table, 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		tablePanel.add(scroll);
		
		headerPanel.add(logout);
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(tablePanel, BorderLayout.CENTER);
		mainPanel.add(formPanel, BorderLayout.SOUTH);
		
		add(mainPanel);
		setVisible(true);
	}

	public void prepareTableModel(Vector<ProductModel> products) {
		String[] col = {"Id", "Name", "Description", "Price", "Stock"};
		DefaultTableModel dtm = new DefaultTableModel(null, col);
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
}
