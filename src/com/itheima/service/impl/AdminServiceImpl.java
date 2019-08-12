package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.dao.AdminDao;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.Product;
import com.itheima.service.AdminService;

public class AdminServiceImpl implements AdminService{

	public List<Category> findAllCategory() {
		AdminDao dao = new AdminDao();
		try {
			return dao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void saveProduct(Product product) throws SQLException {
		AdminDao dao = new AdminDao();
		dao.saveProduct(product);
	}

	public List<Order> findAllOrders() {
		AdminDao dao = new AdminDao();
		List<Order> ordersList = null;
		try {
			ordersList = dao.findAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordersList;
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		AdminDao dao = new AdminDao();
		List<Map<String, Object>> mapList = null;
		try {
			mapList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}

	
	public List<Product> findAllproducts() {
		AdminDao dao = new AdminDao();
		List<Product> productsList = null;
		try {
			productsList = dao.findAllProducts();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productsList;
	}


	public void deleteByCid(String cid) {
		AdminDao dao = new AdminDao();
		try {
			dao.deleteByCid(cid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
	}


	

	@Override
	public Category findCategoryByCid(String cid) {
		AdminDao dao = new AdminDao();
		Category category =null;
		try {
			 category = dao.findCategoryByCid(cid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return category;
	}

	
	@Override
	public void updateCategory(Category category) {
		AdminDao dao = new AdminDao();
		try {
			 dao.updateCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addCategory(Category category) {
		AdminDao dao = new AdminDao();
		try {
			 dao.addCategory(category);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	

	

	
	
}
