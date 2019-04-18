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

 
public class Indao extends HttpServlet {
    public static boolean Connect (String num,String province,String city,String county,String village,double x,double y,String ID,double square,String greenhouse,String main,String date) throws IOException, ClassNotFoundException, ServletException {
        Connection conn=null;
        PreparedStatement ps=null;
         ResultSet rs = null;
         boolean flag=false;
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "insert into greenhouse(id,province,city,county,village,longitude,latitude,master,area,type,structure,builddate)"+"values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
            	ps.setString(2, province);
            	ps.setString(3, city);
            	ps.setString(4, county);
            	ps.setString(5, village);
            	ps.setString(10, greenhouse);
            	ps.setString(11, main);
            	ps.setString(12, date);
            	
            	ps.setDouble(6,x);
            	ps.setDouble(7,y);
            	ps.setString(8,ID);
            	ps.setDouble(9,square);
            	
            	ps.executeUpdate(); //别忘了 
    	
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
