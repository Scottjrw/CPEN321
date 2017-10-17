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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scott.ConnectionFactory;
import com.scott.dao.GroupDao;
import com.scott.dao.GroupDaoImpl;
import com.scott.dao.UserDao;
import com.scott.dao.UserDaoImpl;
import com.scott.model.User;

public class GroupService extends HttpServlet {
	@Override
	public void init() throws ServletException {
		// initialize
		System.out.println("init without parameters ======");
		super.init();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// initialize
		System.out.println("init with parameters ======");
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("service ======");
		super.service(req, resp);
	}

	@Override
	public void destroy() {

		System.out.println("destroy ======");
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 接收程序get请求时执行的操作
		System.out.println("doGet ======");

		String token = req.getParameter("token");
		String action = req.getParameter("action");
		Connection conn = ConnectionFactory.getInstance().makeConnection();

		try {
			UserDao userDao = new UserDaoImpl();
			User user = userDao.getUserByToken(conn, token);

			if (user == null) {
				resp.getWriter().write("failed");
			} else {
				GroupDao groupDao = new GroupDaoImpl();
				List<String> groupList = groupDao.getGroupByUsername(conn, user.getName());
				switch (action) {
				case "createGroup":
					if (!groupList.contains(req.getParameter("groupName"))) {
						groupDao.insertGroup(conn, req.getParameter("groupName"), user.getName());
						resp.getWriter().write("succeed");
					} else
						resp.getWriter().write("failed");
					break;
				case "addToGroup"://need to fix this
					if (!groupList.contains(req.getParameter("groupName"))){
						groupDao.addGroup(conn, req.getParameter("groupName"), user.getName());
						resp.getWriter().write("succeed");
					}
					else
						resp.getWriter().write("failed");
					break;
				case "listGroup":
					resp.getWriter().write(new Gson().toJson(groupList));
					break;
				case "listUser":
					List<String> userList = groupDao.getUserByGroupName(conn, req.getParameter("groupName"));
					resp.getWriter().write(new Gson().toJson(userList));
					break;
				case "leaveGroup":
					groupDao.leaveGroup(conn, req.getParameter("groupName"), user.getName());
					resp.getWriter().write("succeed");
					break;
				default:
					resp.getWriter().write("unknown operation");
					break;
				}
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resp.getWriter().write("exception: " + e.toString());
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 接收程序post请求时执行的操作
		System.out.println("doPost ======");
	}
}
