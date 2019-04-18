package com.veg.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.veg.bean.Bean;
import com.veg.bean.Bean_1;
import com.veg.dao.sedao;
import com.veg.dao.sedao1;
@WebServlet("/seServlet1")
public class seServlet1 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		List <Bean> li = new ArrayList<Bean>();
		sedao1 se = new sedao1();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		if(li!=null)
		{
			for(Bean bean:li)
			{
			resp.getWriter().write("<br/>"+"大棚编号: "+bean.getNum()+"<br/>"+"所在省: "+bean.getProvince()+"<br/>"+"所在市: "+bean.getCity()
		+"<br/>"+"所在县(区): "+bean.getCounty()+"<br/>"+"所在村: "+bean.getVillage()+"<br/>"+"经度: "+bean.getX()+"<br/>"+"纬度: "+bean.getY()+"<br/>"+"身份证号: "+bean.getID()+"<br/>"+"面积: "+bean.getSquare()+"<br/>"+"大棚类型: "+bean.getGreenhouse()+"<br/>"+"主体结构类型: "+bean.getMain()+"<br/>"+"建造日期: "+bean.getDate());
			}
			}
		else
		{
			
				resp.getWriter().write("您要查找的数据不存在");
			
		}
	}
	
}
