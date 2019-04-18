package com.veg.dao;

 import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.veg.bean.Bean_1;

import javax.servlet.http.*;
import javax.servlet.http.Cookie;

 
public class sedao extends HttpServlet {
    public  List<Bean_1> Connect (String num) throws IOException, ClassNotFoundException, ServletException {
        Connection conn;
         PreparedStatement ps;
         ResultSet rs;
         List<Bean_1> li=new ArrayList<>();
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "select greenhouseid,waterconsumption,irrigationdate from irrigationLog where greenhouseid=? ";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // 连接数据库
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // 建立Statement对象
             ps = conn.prepareStatement(sql);
             ps.setString(1, num);
             rs = ps.executeQuery(); 
            while (rs.next()) {
            	Bean_1 bean=new Bean_1();
            	String  a = rs.getString(1);
                 String b = rs.getString(2);  
                 String c = rs.getString(3); 
                 bean.setNum(a);
                 bean.setWater(b);
                 bean.setDate(c);
                 li.add(bean);
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
         }
         return li;
     }


 }
