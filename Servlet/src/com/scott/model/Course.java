package com.scott.model;

public class Course {
	private int id;
	private String courseName;
	private int courseNum;
	
	public Course(){
		this.id = 0;
		this.courseName = null;
		this.courseName = null;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setCourseName(String name){
		this.courseName = name;
	}
	
	public void setCourseNum(int courseNum){
		this.courseNum = courseNum;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getCourseName(){
		return this.courseName;
	}
	
	public int getCourseNum(){
		return this.courseNum;
	}
}
