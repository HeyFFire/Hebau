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
             // �������ݿ�
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // ����Statement����
             stmt = conn.createStatement();
             /**
              * Statement createStatement() ����һ�� Statement �������� SQL ��䷢�͵����ݿ⡣
              */
             // ִ�����ݿ��ѯ���
             rs = stmt.executeQuery(sql);
             /**
              * ResultSet executeQuery(String sql) throws SQLException ִ�и����� SQL
              * ��䣬����䷵�ص��� ResultSet ����
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
             System.out.println("���ݿ�����ʧ��");
         }
         return str;
    }
    
   
     }


 
