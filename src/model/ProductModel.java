package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import connection.Connect;

public class ProductModel {

	private static ProductModel productm;
	private Integer productId;
	private String productName;
	private String productDescription;
	private Integer productPrice;
	private Integer prodcutStock;
	private Connect connect = Connect.getConnection();
	
	public static ProductModel getProductModel() {
		if(productm == null) {
			productm = new ProductModel();
		}
		return productm;
	}
	
	private ProductModel() {
		connect = Connect.getConnection();
	}
	
	public Vector<ProductModel> getAllProduct(){
		Vector<ProductModel> productList = new Vector<>();
		ResultSet rs;
		
		rs = connect.executeQuery("SELECT * FROM product");
		
		try {
			while(rs.next()) {
				Integer id;
				String name;
				String desc;
				Integer price;
				Integer stock;
				
				id = rs.getInt("id");
				name = rs.getString("name");
				desc = rs.getString("description");
				price = rs.getInt("price");
				stock = rs.getInt("stock");
				
				productList.add(new ProductModel(id, name, desc, price, stock));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return productList;
	}

	public ProductModel(Integer productId, String productName, String productDescription, Integer productPrice,
			Integer prodcutStock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.prodcutStock = prodcutStock;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProdcutStock() {
		return prodcutStock;
	}

	public void setProdcutStock(Integer prodcutStock) {
		this.prodcutStock = prodcutStock;
	}
	
}
