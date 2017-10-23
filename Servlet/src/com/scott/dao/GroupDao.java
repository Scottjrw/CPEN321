package com.scott.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GroupDao {
	List<String> getGroupByUsername(Connection conn, String username)throws SQLException;
	List<String> getUserByGroupName(Connection conn, String groupName)throws SQLException;
	//create a new group
	void insertGroup(Connection conn, String groupName, String username, int courseId, 
				String inviteCode, int isPrivate)throws SQLException;
	//add to an existing group
	void joinGroup(Connection conn, String groupName, String username)throws SQLException;
	void leaveGroup(Connection conn, String groupName, String username)throws SQLException;
}
