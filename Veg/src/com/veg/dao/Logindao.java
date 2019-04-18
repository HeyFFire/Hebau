package com.veg.dao;

 import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import javax.servlet.http.Cookie;

 
public class Logindao extends HttpServlet {
    public static String Connect (String username,String password) throws IOException, ClassNotFoundException, ServletException {
        Connection conn;
         Statement stmt;
         ResultSet rs;
         String str=null;
         boolean flag=false;
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "select * from admin";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // 连接数据库
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // 建立Statement对象
             stmt = conn.createStatement();
             /**
              * Statement createStatement() 创建一个 Statement 对象来将 SQL 语句发送到数据库。
              */
             // 执行数据库查询语句
             rs = stmt.executeQuery(sql);
             /**
              * ResultSet executeQuery(String sql) throws SQLException 执行给定的 SQL
              * 语句，该语句返回单个 ResultSet 对象
              */
            while (rs.next()) {
                 String usr = rs.getString("usr");
                 String pwd = rs.getString("pwd");
                 
                 if(usr.equals(username)&&pwd.equals(password))
                 {
                	 str=usr;
                	 System.out.println("www");
                	    return str;
                 }
                
             }
           
           
             if (rs != null) {
                 rs.close();
                 rs = null;
             }
             if (stmt != null) {
                 stmt.close();
                 stmt = null;
            }
             if (conn != null) {
                 conn.close();
                 conn = null;
             }
         } catch (SQLException e) {
             e.printStackTrace();
             System.out.println("数据库连接失败");
         }
         return str;
    }
    
   
     }


 
