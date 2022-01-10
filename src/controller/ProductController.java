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

	public Vector<ProductModel> getAllProduct(){
		Vector<ProductModel> productList = productm.getAllProduct();
		return productList;
	}
	
	public void getProductDataView() {
		Vector<ProductModel> productList = productm.getAllProduct();
		new ProductDataView(productList);
	}
	
	public void addProduct(String name, String desc, Integer price, Integer stock) {
		ProductModel tempProduct = new ProductModel(name, desc, price, stock);
		productm.insertProduct(tempProduct);
	}
	
	public void updateProduct(Integer id, String name, String desc, Integer price, Integer stock) {
		ProductModel tempProduct = new ProductModel(id, name, desc, price, stock);
		productm.updateProduct(tempProduct);
	}
	
	public void deleteProduct(Integer id) {
		ProductModel tempProduct = productm.getProduct(id);
		productm.deleteProduct(tempProduct);
	}
	
}
