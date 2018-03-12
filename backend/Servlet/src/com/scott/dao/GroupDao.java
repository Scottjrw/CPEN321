package com.scott.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.scott.model.Post;

public interface GroupDao {
	List<String> getGroupByUsername(Connection conn, String username)throws SQLException;
	List<String> getUserByGroupName(Connection conn, String groupName)throws SQLException;
	String getAnnouncement(Connection conn, String groupName)throws SQLException;
	String getCourseName(Connection conn, String groupName)throws SQLException;
	List<Post> getPost(Connection conn, String groupName)throws SQLException;
	//create a new group
	void insertGroup(Connection conn, String groupName, String username, int courseId, 
				String inviteCode, int isPrivate)throws SQLException;
	void updateAnnouncement(Connection conn, String groupName, String post)throws SQLException;
	void createNewPost(Connection conn, String groupName, String post, String author) throws SQLException;
	//add to an existing group
	boolean joinGroup(Connection conn, String groupName, String username, String inviteCode)throws SQLException;
	void leaveGroup(Connection conn, String groupName, String username)throws SQLException;
}
