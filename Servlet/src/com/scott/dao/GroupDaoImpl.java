package com.scott.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	public void insertGroup(Connection conn, String groupName, String username) throws SQLException{
		PreparedStatement ps = conn.prepareCall(
	             "INSERT INTO study_buddy.group(username, group_name, is_admin) VALUES (?, ?, ?)");
	     ps.setString(1, username);
	     ps.setString(2, groupName);
	     ps.setBoolean(3, true);
	     ps.execute();
		
	}

	@Override
	public void addGroup(Connection conn, String groupName, String username) throws SQLException{
		PreparedStatement ps = conn.prepareCall(
	             "INSERT INTO study_buddy.group(username, group_name, is_admin) VALUES (?, ?, ?)");
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
