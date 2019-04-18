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

 
public class deldao4 extends HttpServlet {
    public static boolean Connect (String num) throws IOException, ClassNotFoundException, ServletException {
        Connection conn=null;
        PreparedStatement ps=null;
        boolean flag=false;
         ResultSet rs = null;
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "delete from fertilizationLog where greenhouseid =?";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // 连接数据库
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // 建立Statement对象
             ps = conn.prepareStatement(sql);
             ps.setString(1,num);
             int result=ps.executeUpdate();
             if(result>0)
             {
            	 flag=true;
             }
          

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
             return flag;
         }
         return flag;
     }


 }
