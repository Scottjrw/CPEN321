package com.scott;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;



public class ConnectionFactory {
	private String driver;
	 private String dburl;
	 private String user;
	 private String password;

	 //private Connection conn;

	 private ConnectionFactory(){
	     // 解析配置文件
	     Properties prop = new Properties();
	     try {
	         InputStream in = ConnectionFactory.class.getClassLoader()
	                 .getResourceAsStream("dbconfig.properties");
	         prop.load(in);
	     } catch (IOException e) {
	         System.out.println("error loading properties file");
	     }

	     driver = prop.getProperty("driver");
	     dburl = prop.getProperty("url");
	     user = prop.getProperty("username");
	     password = prop.getProperty("password");
	 }

	 private static class FactoryHolder{
	     private static final ConnectionFactory instance = new ConnectionFactory();
	 }

	 public static ConnectionFactory getInstance(){

	     return FactoryHolder.instance;
	 }

	 public Connection makeConnection(){
	     // connect to db
		 Connection conn = null;
	     try {
	         Class.forName(driver);
	         conn = DriverManager.getConnection(dburl, user, password);
	     } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
	     return conn;
	 }
}
