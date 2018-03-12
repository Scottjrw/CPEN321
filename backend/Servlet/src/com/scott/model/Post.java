package com.scott.model;

import java.sql.Date;

public class Post {
	private int id;
	private String groupName;
	private String date;
	private String post;
	private boolean is_announcement;
	private String author;
	
	public int getId(){
		return this.id;
	}
	
	public String getGroupName(){
		return this.groupName;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getPost(){
		return this.post;
	}
	
	public boolean isAnnouncement(){
		return this.is_announcement;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setGroupName(String groupName){
		this.groupName = groupName;
	}
	
	public void setDate(String date){
		this.date = date;
	}
	
	public void setPost(String post){
		this.post = post;
	}
	
	public void setIsAnnouncement(boolean is_annoucement){
		this.is_announcement = is_annoucement;
	}
	
	public void setAuthor(String author){
		this.author = author;
	}
}
