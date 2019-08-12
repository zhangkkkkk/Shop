package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.Product;
import com.itheima.service.AdminService;
import com.itheima.service.impl.AdminServiceImpl;
import com.itheima.utils.BeanFactory;

public class AdminServlet extends BaseServlet {
	
	//添加分类项
	public void addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、获取数据
		Map<String, String[]> properties = request.getParameterMap();
		//2、封装数据
		Category category = new Category();
		try {
			BeanUtils.populate(category, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		category.setCid(UUID.randomUUID().toString());
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		service.addCategory(category);
		response.sendRedirect(request.getContextPath()+"/admin?method=findAllCategorys");
	}
	
	//根据Cid修改分类项
	public void updateCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1、获取数据
		Map<String, String[]> properties = request.getParameterMap();
		//2、封装数据
		Category category = new Category();
		try {
			BeanUtils.populate(category, properties);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		//String cname = request.getParameter("cname");
		//String cid = request.getParameter("cid");
		//Category category = new Category();
		//category.setCid(cid);
		//category.setCname(cname);
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		service.updateCategory(category);
		response.sendRedirect(request.getContextPath()+"/admin?method=findAllCategorys");
	}
	
	//根据Cid查询分类项
	public void updateCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		Category category = service.findCategoryByCid(cid);
		request.setAttribute("category", category);
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
		
	}
	
	//根据cid删除分类项
	public void deleteByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取要删除的cid
		String cid = request.getParameter("cid");
		//传递cid到service层
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		service.deleteByCid(cid);
		response.sendRedirect(request.getContextPath()+"/admin?method=findAllCategorys");
	}
	
	
	//根据订单id查询订单项和商品信息
	public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//获得oid
		String oid = request.getParameter("oid");
		
		//用解耦合的方式进行编码----解web层与service层的耦合
		//使用工厂+反射+配置文件
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		
		List<Map<String,Object>> mapList = service.findOrderInfoByOid(oid);
		
		Gson gson = new Gson();
		String json = gson.toJson(mapList);
		/*[
		 * 	{"shop_price":4499.0,"count":2,"pname":"联想（Lenovo）小新V3000经典版","pimage":"products/1/c_0034.jpg","subtotal":8998.0},
		 *  {"shop_price":2599.0,"count":1,"pname":"华为 Ascend Mate7","pimage":"products/1/c_0010.jpg","subtotal":2599.0}
		 *]*/
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().write(json);
		
	}
	
	
	//获得所有商品
	public void findAllProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得所有的商品信息----List<Product>
		//传递请求到service层
		AdminService service = (AdminService) BeanFactory.getBean("adminService");

				//获得所有的商品的类别数据
				List<Category> categoryList = null;
				try {
					categoryList = service.findAllCategory();
				} catch (Exception e) {
					e.printStackTrace();
				}

				request.setAttribute("categoryList", categoryList);



				List<Product> productList = null;
				try {
					productList = service.findAllproducts();
				} catch (Exception e) {
					e.printStackTrace();
				}



				//将productList放到request域
				request.setAttribute("productList", productList);

				request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
		
		
	}
	
	
	//获得所有的订单
	public void findAllOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获得所有的订单信息----List<Order>
		
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Order> orderList = service.findAllOrders();
		
		request.setAttribute("orderList", orderList);
		
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
		
	}

	public void findAllCategorys(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Category> categoryList = service.findAllCategory();
        request.setAttribute("categoryList", categoryList);
		
		request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
		
		
	}
	
	public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//提供一个List<Category> 转成json字符串
		AdminService service = (AdminService) BeanFactory.getBean("adminService");
		List<Category> categoryList = service.findAllCategory();
		
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		
		response.setContentType("text/html;charset=UTF-8");
		
		response.getWriter().write(json);
		
	}

	
}
