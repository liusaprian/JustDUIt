package controller;

import java.util.Vector;

import model.ProductModel;
import view.ProductDataView;

public class ProductController {

	private static ProductController productc;
	private ProductModel productm;
	
	public static ProductController getProductController() {
		if(productc == null) {
			productc = new ProductController();
		}
		return productc;
	}
	
	private ProductController() {
		productm = ProductModel.getProductModel();
	}

	public void getProductDataView() {
		Vector<ProductModel> productList = productm.getAllProduct();
		new ProductDataView(productList);
	}
	
	
	
}
