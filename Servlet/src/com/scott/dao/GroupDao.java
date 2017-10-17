package com.scott.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface GroupDao {
	List<String> getGroupByUsername(Connection conn, String username)throws SQLException;
	List<String> getUserByGroupName(Connection conn, String groupName)throws SQLException;
	void insertGroup(Connection conn, String groupName, String username)throws SQLException;
	void addGroup(Connection conn, String groupName, String username)throws SQLException;
	void leaveGroup(Connection conn, String groupName, String username)throws SQLException;
}
