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

import com.veg.bean.Bean;
import com.veg.bean.Bean_1;

import javax.servlet.http.*;
import javax.servlet.http.Cookie;

 
public class sedao1 extends HttpServlet {
    public  List<Bean> Connect (String a2) throws IOException, ClassNotFoundException, ServletException {
        Connection conn;
         PreparedStatement ps;
         ResultSet rs;
         List<Bean> li = new ArrayList<>();
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "select id,province,city,county,village,longitude,latitude,master,area,type,structure,builddate  from greenhouse where id=? ";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // 连接数据库
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // 建立Statement对象
             ps = conn.prepareStatement(sql);
             ps.setString(1, a2);
             rs = ps.executeQuery(); 
            while (rs.next()) {
            	Bean bean = new Bean();
            	String  a = rs.getString(1);
                String b = rs.getString(2);  
                String c = rs.getString(3);
                String d = rs.getString(4);
                String e = rs.getString(5);  
                double f = rs.getDouble(6); 
                double g = rs.getDouble(7);
                String h = rs.getString(8);  
                double i = rs.getDouble(9); 
                String j = rs.getString(10);
                String k = rs.getString(11);  
                String l = rs.getString(12); 
                bean.setNum(a);
                bean.setProvince(b);
                bean.setCity(c);
                bean.setCounty(d);
                bean.setVillage(e);
                bean.setX(f);
                bean.setY(g);
                bean.setID(h);
                bean.setSquare(i);
                bean.setGreenhouse(j);
                bean.setMain(k);
                bean.setDate(l);
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
