package com.scott.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.scott.ConnectionFactory;
import com.scott.dao.GroupDao;
import com.scott.dao.GroupDaoImpl;
import com.scott.dao.UserDao;
import com.scott.dao.UserDaoImpl;
import com.scott.model.Post;
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
				case "createGroup"://create a group
					if (!groupList.contains(req.getParameter("groupName"))) {//handles duplicated group name
						int isPrivate = Integer.parseInt(req.getParameter("isPrivate"));
						String inviteCode = req.getParameter("inviteCode");
						int courseId = Integer.parseInt(req.getParameter("courseId"));
						groupDao.insertGroup(conn, req.getParameter("groupName"), user.getName(), courseId, inviteCode, isPrivate);
						resp.getWriter().write("succeed");
					} else
						resp.getWriter().write("failed");
					break;
				case "joinGroup"://add to a group (need to fix this)
					if (!groupList.contains(req.getParameter("groupName"))){
						boolean result = groupDao.joinGroup(conn, req.getParameter("groupName"), user.getName(), req.getParameter("inviteCode"));
						if(result) resp.getWriter().write("succeed");
						else resp.getWriter().write("failed");
					}
					else
						resp.getWriter().write("failed");
					break;
				case "listGroup"://list user's group 
					resp.getWriter().write(new Gson().toJson(groupList));
					break;
				case "listUser"://list all users in the group
					List<String> userList = groupDao.getUserByGroupName(conn, req.getParameter("groupName"));
					String announcement = groupDao.getAnnouncement(conn, req.getParameter("groupName"));
					List<Post> postList = groupDao.getPost(conn, req.getParameter("groupName"));
					List<Object> resultList = new ArrayList<Object>();
					resultList.add(userList);
				    resultList.add(announcement);
					resultList.add(postList);
					resp.getWriter().write(new Gson().toJson(resultList));
					break;
				case "updateAnnouncement":
					groupDao.updateAnnouncement(conn, req.getParameter("groupName"), req.getParameter("post"));
					resp.getWriter().write("succeed");
					break;
				case "getCourseName":
					String courseId = groupDao.getCourseName(conn, req.getParameter("groupName"));
					resp.getWriter().write(courseId);
					break;
				case "newPost":
					groupDao.createNewPost(conn, req.getParameter("groupName"), 
							req.getParameter("post"), user.getName());
					resp.getWriter().write("succeed");
					break;
				case "leaveGroup"://leave a group
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

		System.out.println("doPost ======");
	}
}
