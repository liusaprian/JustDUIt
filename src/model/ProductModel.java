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
	
	public void deleteProduct(ProductModel product) {
		connect.executeUpdate("DELETE FROM product WHERE id = " + product.getProductId());
	}
	
	public void updateProduct(ProductModel product) {
		connect.executeUpdate("UPDATE product SET "
				+ "name =  '" + product.getProductName() + "',"
				+ "description = '" + product.getProductDescription() + "',"
				+ "price = " + product.getProductPrice() + ","
				+ "stock = " + product.getProdcutStock()
				+ " WHERE id = " + product.getProductId());
	}
	
	public void insertProduct(ProductModel product) {
		connect.executeUpdate("INSERT INTO product VALUES("
				+ "NULL,"
				+ "'" + product.getProductName()+"',"
				+ "'" + product.getProductDescription()+"',"
				+ product.getProductPrice()+","
				+ product.getProdcutStock()
				+ ")");
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
	
	public ProductModel(String productName, String productDescription, Integer productPrice,
			Integer prodcutStock) {
		super();
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.prodcutStock = prodcutStock;
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
	
	public ProductModel getProduct(int productId) {
		ResultSet rs;
		rs = connect.executeQuery("SELECT * FROM product WHERE id = " + productId);
		try {
			rs.next();
			return new ProductModel(productId, rs.getString("name"), rs.getString("description"), rs.getInt("price"), rs.getInt("stock"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
