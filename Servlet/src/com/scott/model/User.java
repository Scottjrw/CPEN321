package com.scott.model;

public class User {
	private String username;
	private String studentName;
	private String email;
	private String password;
	private String token;
	private long id;
	
	public String getToken(){
		return this.token;
	}
	
	public void setToken(String token){
		this.token = token;
	}
	
	public User(){
		this.username = null;
		this.password = null;
		this.studentName = null;
		this.email = null;
		this.id = -1;
		this.token = null;
	}
	
	public User(String username, String password, String studentName, String email, long id){
		this.username =username;
		this.password = password;
		this.studentName = studentName;
		this.email = email;
		this.id = id;
		this.token = null;
	}
	
	public void setStudentName(String name){
		this.studentName = name;
	}
	
	public String getStudentName(){
		return this.studentName;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setName(String name){
		this.username = name;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setId(long l){
		this.id = l;
	}
	
	public String getName(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public long getId(){
		return this.id;
	}
}
