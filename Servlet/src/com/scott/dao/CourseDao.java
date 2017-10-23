package com.scott.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.scott.model.Course;

public interface CourseDao {
	List<Course> getCourse(Connection conn) throws SQLException;
	List<Course> getUserCourse(Connection conn, String username) throws SQLException;
	void registerCourse(Connection conn, String username, int courseId) throws SQLException;
}
