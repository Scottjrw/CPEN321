package com.scott.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.scott.ConnectionFactory;
import com.scott.dao.UserDao;
import com.scott.dao.UserDaoImpl;
import com.scott.model.User;

public class RegistrationService extends HttpServlet {

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

	        String username = req.getParameter("username");
	        String password = req.getParameter("password");
	        String email = req.getParameter("email");
	        String studentName = req.getParameter("studentName");

	        try {
	            Connection conn = ConnectionFactory.getInstance().makeConnection();

	            if(username!=null&&password!=null&&email!=null&&studentName!=null){
		            UserDao dao = new UserDaoImpl();
		            if(dao.getUserByUsername(conn, username)==null){
			            User user = new User(username, password, studentName, email, -1);
			            dao.insert(conn, user);
			            resp.getWriter().write("succeed");
		            }else{
		            	resp.getWriter().write("usernameExists");//handles duplicated user names
		            }
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