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

import javax.servlet.http.*;
import javax.servlet.http.Cookie;

 
public class chdao2 extends HttpServlet {
    public  List<Bean_2> Connect (int page,int rows) throws IOException, ClassNotFoundException, ServletException {
        Connection conn;
         PreparedStatement ps;
         ResultSet rs;
         int total=(page-1)*rows;
         int num=rows;
         List<Bean_2> li=new ArrayList<>();
         boolean flag=false;
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "select greenhouseid,plant,plantdate,harveststartdate,harvestenddate from greenhouseplant  order by greenhouseid offset ?  rows fetch next ? rows only ";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // 连接数据库
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // 建立Statement对象
             ps = conn.prepareStatement(sql);
             ps.setInt(1, total);
             ps.setInt(2, rows);
             rs = ps.executeQuery();
             /**
              * ResultSet executeQuery(String sql) throws SQLException 执行给定的 SQL
              * 语句，该语句返回单个 ResultSet 对象
              */
            while (rs.next()) {
            	Bean_2 bean = new Bean_2();
            	String  a = rs.getString(1);
                 String b = rs.getString(2);  
                 String c = rs.getString(3); 
                 String d = rs.getString(4); 
                 String e = rs.getString(5); 
                 bean.setNum(a);
                 bean.setName(b);
                 bean.setPdate(c);
                 bean.setSdate(d);
                 bean.setEdate(e);
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
