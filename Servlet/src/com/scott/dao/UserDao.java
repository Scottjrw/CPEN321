package com.scott.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.scott.model.User;


public interface UserDao {
	User getUser(Connection conn, String name, String password) throws SQLException;
	User getUserByUsername(Connection conn, String username) throws SQLException;
	User getUserByToken(Connection conn, String token) throws SQLException;
	void insert(Connection conn, User user) throws SQLException;
	void update(Connection conn, User user) throws SQLException;
	void updateToken(Connection conn, String token, String username) throws SQLException;
	void delete(Connection conn, User user) throws SQLException;

}
