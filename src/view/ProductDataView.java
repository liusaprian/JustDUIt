package view;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ProductModel;

public class ProductDataView extends JFrame{

	private JPanel mainPanel;
	
	public ProductDataView(Vector<ProductModel> productList) {
		setSize(1000,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Product Data View");
		
		//nnti perlu aku ubh lagi viewnya
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(productList.size()*5, 1));
		
		for (int i = 0; i < productList.size(); i++) {
			ProductModel product = productList.get(i);
			mainPanel.add(new JLabel(product.getProductId()+""));
			mainPanel.add(new JLabel(product.getProductName()));
			mainPanel.add(new JLabel(product.getProductDescription()));
			mainPanel.add(new JLabel(product.getProductPrice()+""));
			mainPanel.add(new JLabel(product.getProdcutStock()+""));
		}
		
		add(mainPanel);
		setVisible(true);
	}

}
