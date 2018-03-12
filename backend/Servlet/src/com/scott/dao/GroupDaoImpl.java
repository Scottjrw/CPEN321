package com.scott.dao;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.scott.model.Post;

public class GroupDaoImpl implements GroupDao{

	@Override
	public List<String> getGroupByUsername(Connection conn, String username) throws SQLException {
		String sql = "SELECT * FROM study_buddy.group WHERE username = '" + username + "'";
	    Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    List<String> resultList = new ArrayList<String>();
	    while(rt.next()){
	    	resultList.add(rt.getString("group_name"));
	    }
		return resultList;
	}

	@Override
	public List<String> getUserByGroupName(Connection conn, String groupName) throws SQLException{
		String sql = "SELECT * FROM study_buddy.group WHERE group_name = '" + groupName + "'";
		Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    List<String> resultList = new ArrayList<String>();
	    while(rt.next()){
	    	resultList.add(rt.getString("username"));
	    }
		return resultList;
	}
	
	@Override
	public String getAnnouncement(Connection conn, String groupName) throws SQLException {
		String sql = "SELECT * FROM study_buddy.group_content "
				+ "WHERE study_buddy.group_content.group_name = '" + groupName + "'"
				+ "AND study_buddy.group_content.is_announcement = 1";
		Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    String announcement=null;
	    while(rt.next()){
	    	announcement = rt.getString("post");
	    }
		return announcement;
	}
	
	@Override
	public List<Post> getPost(Connection conn, String groupName) throws SQLException {
		String sql = "SELECT * FROM study_buddy.group_content"
				+ " WHERE study_buddy.group_content.group_name = '"+groupName+"'"
				+ " AND study_buddy.group_content.is_announcement = 0"
				+ " ORDER BY date desc"
				+ " LIMIT 10";
		Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    List<Post> postList = new ArrayList<Post>();
	    while(rt.next()){
	    	Post post = new Post();
	    	post.setId(rt.getInt("id"));
	    	post.setGroupName(rt.getString("group_name"));
	    	post.setDate(rt.getString("date"));
	    	post.setPost(rt.getString("post"));
	    	post.setIsAnnouncement(rt.getBoolean("is_announcement"));
	    	post.setAuthor(rt.getString("author"));
	    	postList.add(post);
	    }
		return postList;
	}

	@Override
	public void insertGroup(Connection conn, String groupName, String username, int courseId, 
			String inviteCode, int isPrivate) throws SQLException{
		PreparedStatement ps = conn.prepareCall(
	             "INSERT INTO study_buddy.group(username, group_name, is_admin, is_private, course_id, invite_code) "
	             + "VALUES (?, ?, ?, ?, ?, ?)");
	     ps.setString(1, username);
	     ps.setString(2, groupName);
	     ps.setBoolean(3, true);
	     if(isPrivate==1) ps.setBoolean(4, true);
	     else ps.setBoolean(4, false);
	     ps.setInt(5, courseId);
	     ps.setString(6, inviteCode);
	     ps.execute();
	}
	
	@Override
	public void updateAnnouncement(Connection conn, String groupName, String post)throws SQLException{
		String announcement = this.getAnnouncement(conn, groupName);
		
		if(announcement!=null){
			PreparedStatement ps = conn.prepareCall("UPDATE group_content SET post = ? "
					+ "WHERE group_name = ? AND is_announcement = 1");
			ps.setString(1, post);
			ps.setString(2, groupName);
			ps.execute();
		}else{
			PreparedStatement ps = conn.prepareCall("INSERT INTO study_buddy.group_content(group_name, post, is_announcement, date, author)"
					+ " VALUES (?,?,?,?,?)");
			ps.setString(1, groupName);
			ps.setString(2, post);
			ps.setBoolean(3, true);
			String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
			ps.setString(4, timeStamp);
			ps.setString(5, "admin");
			ps.execute();
		}
	}

	@Override
	public boolean joinGroup(Connection conn, String groupName, String username, String inviteCode) throws SQLException{
		String sql = "SELECT * from study_buddy.group WHERE group_name = '"
				+ groupName + "' LIMIT 1";
		Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    String code = null;
	    int courseId = 0;
	    boolean isPrivate = false;
	    while(rt.next()){
	    	code = rt.getString("invite_code");
	    	courseId = rt.getInt("course_id");
	    	isPrivate = rt.getBoolean("is_private");
	    }
	    if(isPrivate && !code.equals(inviteCode)) return false;
	    
		PreparedStatement ps = conn.prepareCall(
				"INSERT INTO study_buddy.group(username, group_name, is_admin, is_private, course_id, invite_code) "
				+ "VALUES (?, ?, ?, ?, ?, ?) ");
	     ps.setString(1, username);
	     ps.setString(2, groupName);
	     ps.setBoolean(3, false);
	     ps.setBoolean(4, isPrivate);
	     ps.setInt(5, courseId);
	     ps.setString(6, code);
	     ps.execute();
	     
	     return true;
		
	}

	@Override
	public void leaveGroup(Connection conn, String groupName, String username) throws SQLException{
		PreparedStatement ps = conn.prepareCall(
	             "DELETE FROM study_buddy.group WHERE username = '" + username
	             + "' AND group_name = '" + groupName + "'");
	     ps.execute();
		
	}

	@Override
	public void createNewPost(Connection conn, String groupName, String post, String author) throws SQLException {
		PreparedStatement ps = conn.prepareCall("INSERT INTO study_buddy.group_content(group_name, post, is_announcement, date, author)"
				+ " VALUES (?,?,?,?,?) ");
		ps.setString(1, groupName);
		ps.setString(2, post);
		ps.setBoolean(3, false);
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		ps.setString(4, timeStamp);
		ps.setString(5, author);
		ps.execute();
		
	}

	@Override
	public String getCourseName(Connection conn, String groupName) throws SQLException {
		String sql = "SELECT course_id FROM study_buddy.group WHERE "
				+ "group_name = '" + groupName + "' LIMIT 1";
	    Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    int result = -1;
	    while(rt.next()){
	    	result = rt.getInt("course_id");
	    }
		return Integer.toString(result);
	}

	

	

}
