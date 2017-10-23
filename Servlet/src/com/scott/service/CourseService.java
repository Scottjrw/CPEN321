package com.scott.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.scott.ConnectionFactory;
import com.scott.dao.CourseDao;
import com.scott.dao.CourseDaoImpl;
import com.scott.dao.UserDao;
import com.scott.dao.UserDaoImpl;
import com.scott.model.Course;
import com.scott.model.User;

public class CourseService extends HttpServlet{
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
	        CourseDao courseDao = new CourseDaoImpl();
	        UserDao userDao = new UserDaoImpl();
	        Connection conn = ConnectionFactory.getInstance().makeConnection();
	        
	        try{
		        String action = req.getParameter("action");
		        User user = userDao.getUserByToken(conn, req.getParameter("token"));
		        switch(action){
		        case "getAllCourseNames"://get all courses
		        	List<Course> courseList = courseDao.getCourse(conn);
		        	resp.getWriter().write(new Gson().toJson(courseList));
		        	break;
		        case "listCourses"://get courses that user is enrolled in
		        	List<Course> courses = courseDao.getUserCourse(conn, user.getName());
		        	resp.getWriter().write(new Gson().toJson(courses));
		        	break;
		        case "registerCourses"://register a new course
		        	String username = req.getParameter("username");
		        	int courseId = Integer.parseInt(req.getParameter("courseId"));
		        	courseDao.registerCourse(conn, username, courseId);
		        	resp.getWriter().write("succeed");
		        	break;
		        default:
		        	resp.getWriter().write("unknown operation");
		        	break;
		        }
	        }catch(SQLException e){
	        	e.printStackTrace();
	        }
	 }

	 @Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	         throws ServletException, IOException {

	     System.out.println("doPost ======");
	 }
}
