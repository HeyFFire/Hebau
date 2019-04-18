package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Infobean;

public class InquiryDao {

	public static Infobean inquiry(String a) throws ClassNotFoundException
	{
		 Connection conn;
         PreparedStatement ps;
         ResultSet rs;
         Infobean bean = new Infobean();
         String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
         String sql = "select owner,phone,address,name  from info where id=?";
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
         try {
        	 Class.forName(driverName); 
             // �������ݿ�
            conn = DriverManager.getConnection(url, "sa", "su1998928");
             // ����Statement����
            
             ps = conn.prepareStatement(sql);
             ps.setString(1, a);

             rs = ps.executeQuery();
             /**
              * ResultSet executeQuery(String sql) throws SQLException ִ�и����� SQL
              * ��䣬����䷵�ص��� ResultSet ����
              */
            while (rs.next()) {
            	bean.setOwner(rs.getString(1));
            	bean.setPhone(rs.getString(2));
            	bean.setAddress(rs.getString(3));
            	bean.setName(rs.getString(4));
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
             System.out.println("���ݿ�����ʧ��");
         }
         return bean;
		
	}
}
