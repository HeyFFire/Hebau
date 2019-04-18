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
import com.veg.bean.Bean_2;
import com.veg.bean.Bean_4;
import com.veg.bean.Bean_5;
import com.veg.bean.Bean_6;

import javax.servlet.http.*;
import javax.servlet.http.Cookie;

 
public class sedao6 extends HttpServlet {
    public  List<Bean_6> Connect (String num) throws IOException, ClassNotFoundException, ServletException {
        Connection conn;
         PreparedStatement ps;
         ResultSet rs;
         List<Bean_6> li = new ArrayList<Bean_6>();
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "select greenhouseid,plant,harvestquality,harvestdate from harvestLog where greenhouseid=? ";
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
            	Bean_6 bean = new Bean_6();
            	String  a = rs.getString(1);
                 String b = rs.getString(2);  
                 int c = rs.getInt(3); 
                 String d = rs.getString(4); 
                 bean.setNum(a);
                 bean.setName(b);
                 bean.setMl(c);
                 bean.setDate(d);
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
