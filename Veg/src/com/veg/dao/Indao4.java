package com.veg.dao;

 import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

 
public class Indao4 extends HttpServlet {
    public static boolean Connect (String  num,String name,String date,String date_2) throws IOException, ClassNotFoundException, ServletException {
        Connection conn=null;
        PreparedStatement ps=null;
         ResultSet rs = null;
         boolean flag=false;
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "insert into fertilizationLog(greenhouseid,fertilizer,fertilizationconsumption,fertilizationdate)"+"values(?,?,?,?)";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // 连接数据库
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // 建立Statement对象
             ps = conn.prepareStatement(sql);
             /**
              * Statement createStatement() 创建一个 Statement 对象来将 SQL 语句发送到数据库。
              */
             // 执行数据库查询语句
             /**
              * ResultSet executeQuery(String sql) throws SQLException 执行给定的 SQL
              * 语句，该语句返回单个 ResultSet 对象
              */
            	ps.setString (1,num);
    			ps.setString(2,name);
    			ps.setString(3,date);
    			ps.setString(4,date_2);
    			ps.executeUpdate();  
             if (rs != null) {
                 rs.close();
                 rs = null;
             }
             if (ps != null) {
                 ps.close();
                 ps = null;
            }
             if (conn != null) {
                 conn.close();
                 conn = null;
             }
         } catch (SQLException e) {
             e.printStackTrace();
             System.out.println("数据库连接失败");
             flag=true;
             return flag;
         }
         return flag;
     }


 }
