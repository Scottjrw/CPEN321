package com.scott.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.scott.model.User;



public class UserDaoImpl implements UserDao{

	 @Override
	 public User getUser(Connection conn, String name, String password) throws SQLException {
	     String sql = "SELECT * FROM user WHERE username = '" + name + 
	             "' AND password = '" + password + "'";
	     Statement st = conn.createStatement();
	     ResultSet rt = st.executeQuery(sql);

	     if (rt.next()){
	         User user = new User();
	         user.setId(rt.getLong("id"));
	         user.setName(rt.getString("username"));
	         user.setPassword(rt.getString("password"));
	         user.setEmail(rt.getString("email"));
	         user.setStudentName(rt.getString("student_name"));
	         user.setToken(rt.getString("token"));
	         return user;
	     }
	     else{
	         return null;
	     }
	 }

	 @Override
	 public void insert(Connection conn, User user)  throws SQLException{

	     PreparedStatement ps = conn.prepareCall(
	             "INSERT INTO user(username, password, student_name, email) VALUES (?, ?, ?, ?)");
	     ps.setString(1, user.getName());
	     ps.setString(2, user.getPassword());
	     ps.setString(3, user.getStudentName());
	     ps.setString(4, user.getEmail());
	     ps.execute();
	 }

	 @Override
	 public void update(Connection conn, User user)  throws SQLException{

	     PreparedStatement ps = conn.prepareCall(
	             "UPDATE user SET username = ?, password = ? WHERE id = ?");
	     ps.setString(1, user.getName());
	     ps.setString(2, user.getPassword());
	     ps.setLong(3, user.getId());
	     ps.execute();
	 }

	 @Override
	 public void delete(Connection conn, User user)  throws SQLException{

	     PreparedStatement ps = conn.prepareCall(
	             "DELETE FROM user WHERE id = ?");
	     ps.setLong(1, user.getId());
	     ps.execute();
	 }

	@Override
	public User getUserByUsername(Connection conn, String username) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = '" + username + "'";
	     Statement st = conn.createStatement();
	     ResultSet rt = st.executeQuery(sql);

	     if (rt.next()){
	         User user = new User();
	         user.setId(rt.getLong("id"));
	         user.setName(rt.getString("username"));
	         user.setPassword(rt.getString("password"));
	         user.setEmail(rt.getString("email"));
	         user.setStudentName(rt.getString("student_name"));
	         user.setToken(rt.getString("token"));
	         return user;
	     }
	     else{
	         return null;
	     }
	}

	@Override
	public User getUserByToken(Connection conn, String token) throws SQLException {
		String sql = "SELECT * FROM user WHERE token = '" + token + "'";
	     Statement st = conn.createStatement();
	     ResultSet rt = st.executeQuery(sql);

	     if (rt.next()){
	         User user = new User();
	         user.setId(rt.getLong("id"));
	         user.setName(rt.getString("username"));
	         user.setPassword(rt.getString("password"));
	         user.setEmail(rt.getString("email"));
	         user.setStudentName(rt.getString("student_name"));
	         user.setToken(rt.getString("token"));
	         return user;
	     }
	     else{
	         return null;
	     }
	}

	@Override
	public void updateToken(Connection conn, String token, String username) throws SQLException {
		PreparedStatement ps = conn.prepareCall(
	             "UPDATE user SET token = ? WHERE username = ?");
	     ps.setString(1, token);
	     ps.setString(2, username);
	     ps.execute();
		
	}
}