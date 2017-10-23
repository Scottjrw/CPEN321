package com.scott.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.scott.model.Course;

public class CourseDaoImpl implements CourseDao{
	@Override
	public List<Course> getCourse(Connection conn) throws SQLException {
		List<Course> courseList = new ArrayList<Course>();
		String sql = "SELECT * FROM study_buddy.course;";
		Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    while(rt.next()){
	    	Course course = new Course();
	    	course.setId(rt.getInt("id"));
	    	course.setCourseName(rt.getString("course_name"));
	    	course.setCourseNum(rt.getInt("course_num"));
	    	courseList.add(course);
	    }
		return courseList;
	}

	@Override
	public List<Course> getUserCourse(Connection conn, String username) throws SQLException {
		List<Course> courseList = new ArrayList<Course>();
		String sql = "SELECT study_buddy.course.id, study_buddy.course.course_name, study_buddy.course.course_num"
				+ " FROM study_buddy.course"
				+ " INNER JOIN study_buddy.course_user"
				+ " ON study_buddy.course_user.course_id = study_buddy.course.id"
				+ " WHERE study_buddy.course_user.username = '" + username + "'";
		Statement st = conn.createStatement();
	    ResultSet rt = st.executeQuery(sql);
	    while(rt.next()){
	    	Course course = new Course();
	    	course.setId(rt.getInt("id"));
	    	course.setCourseName(rt.getString("course_name"));
	    	course.setCourseNum(rt.getInt("course_num"));
	    	courseList.add(course);
	    }
		return courseList;
	}

	@Override
	public void registerCourse(Connection conn, String username, int courseId) throws SQLException {
		PreparedStatement ps = conn.prepareCall(
				"INSERT INTO study_buddy.course_user(username, course_id) VALUES (?, ?) ");
	     ps.setString(1, username);
	     ps.setInt(2, courseId);
	     ps.execute();
		
	}
}
