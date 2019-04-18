package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Features;
import bean.Geometry;
import bean.Infobean;
import bean.Properties;

public class getDao {
	public List<Features> get() throws ClassNotFoundException
	{
	List<Features> list = new ArrayList<Features>();
	Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    String url = "jdbc:sqlserver://localhost:1433;DatabaseName=student_course;";
    String sql = "select Yloc,Xloc,type,address,status from point";
    String  driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    try {
   	 Class.forName(driverName); 
        // �������ݿ�
       conn = DriverManager.getConnection(url, "sa", "su1998928");
        // ����Statement����
       
        ps = conn.prepareStatement(sql);

        rs = ps.executeQuery();
        /**
         * ResultSet executeQuery(String sql) throws SQLException ִ�и����� SQL
         * ��䣬����䷵�ص��� ResultSet ����
         */
       while (rs.next()) {
    	   Geometry geometry = new Geometry();
    	    Properties properties = new Properties();
    	    Features fatures = new Features();
    	   double x = rs.getDouble(2);
    	   double y = rs.getDouble(1);
    	   double a[]={x,y};
    	   String address = rs.getString(4);
    	    geometry.setCoordinates(a);
    		geometry.setType("Point");
    		properties.setAddress(address);
    		properties.setStatus("3");
    		fatures.setGeometry(geometry);
    		fatures.setProperties(properties);
    		fatures.setType("Feature");
    		list.add(fatures);
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
    return list;
	
}
}
