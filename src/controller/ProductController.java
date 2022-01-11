package controller;

import java.util.Vector;

import model.ProductModel;
import view.ProductDataView;

public class ProductController {

	private static ProductController controller;
	private ProductModel productm;
	
	public static ProductController getInstance() {
		if(controller == null) {
			controller = new ProductController();
		}
		return controller;
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
	
	public ProductModel getProduct(int id) {
		return productm.getProduct(id);
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
