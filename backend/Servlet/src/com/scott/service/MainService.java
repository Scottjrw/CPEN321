package com.scott.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.scott.ConnectionFactory;
import com.scott.dao.GroupDao;
import com.scott.dao.GroupDaoImpl;
import com.scott.dao.UserDao;
import com.scott.dao.UserDaoImpl;
import com.scott.model.User;

public class MainService extends HttpServlet {

	 @Override
	 public void init() throws ServletException {
	     System.out.println("init without parameters ======");
	     super.init();
	 }

	 @Override
	 public void init(ServletConfig config) throws ServletException {

	     System.out.println("init with parameters ======");
	     super.init(config);
	 }

	 @Override
	 protected void service(HttpServletRequest req, HttpServletResponse resp)
	         throws ServletException, IOException {

	     System.out.println("service ======");
	     super.service(req, resp);
	 }

	 @Override
	 public void destroy() {

	     System.out.println("destroy ======");
	     super.destroy();
	 }

	 @Override
	 protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	         throws ServletException, IOException {

	        System.out.println("doGet ======");

	        String token = req.getParameter("token");
	        //String username = req.getParameter("username");
	        //String password = req.getParameter("password");

	        try {
	            Connection conn = ConnectionFactory.getInstance().makeConnection();


	            UserDao userDao = new UserDaoImpl();
	            User user = userDao.getUserByToken(conn, token);
	            if(user!=null){
	            	GroupDao groupDao = new GroupDaoImpl();
	            	List<String> groupList = groupDao.getGroupByUsername(conn, user.getName());
	            	int size = groupList.size();
	            	resp.setContentType("application/json");
	            	PrintWriter pw = resp.getWriter();
	            	
	            	JsonObject object = new JsonObject();//create json object
	            	object.addProperty("numOfGroups", Integer.toString(size));
	            	object.addProperty("username", user.getName());
	            	object.addProperty("email", user.getEmail());
	            	object.addProperty("studentName", user.getStudentName());

	            	pw.println(object);
	            	pw.close();
	            }
	            else resp.getWriter().write("failed");
	            conn.close();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	 }

	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	         throws ServletException, IOException {

	     System.out.println("doPost ======");
	 }
}