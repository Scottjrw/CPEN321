package com.scott.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.scott.model.Course;

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
	public void joinGroup(Connection conn, String groupName, String username) throws SQLException{
		PreparedStatement ps = conn.prepareCall(
				"INSERT INTO study_buddy.group(username, group_name, is_admin) VALUES (?, ?, ?) ");
	     ps.setString(1, username);
	     ps.setString(2, groupName);
	     ps.setBoolean(3, false);
	     ps.execute();
		
	}

	@Override
	public void leaveGroup(Connection conn, String groupName, String username) throws SQLException{
		PreparedStatement ps = conn.prepareCall(
	             "DELETE FROM study_buddy.group WHERE username = '" + username
	             + "' AND group_name = '" + groupName + "'");
	     ps.execute();
		
	}

	

}
