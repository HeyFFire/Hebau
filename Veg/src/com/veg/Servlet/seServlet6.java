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
import com.veg.bean.Bean_1;
import com.veg.bean.Bean_2;
import com.veg.bean.Bean_4;
import com.veg.bean.Bean_5;
import com.veg.bean.Bean_6;
import com.veg.dao.sedao;
import com.veg.dao.sedao2;
import com.veg.dao.sedao4;
import com.veg.dao.sedao5;
import com.veg.dao.sedao6;
@WebServlet("/seServlet6")
public class seServlet6 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		List<Bean_6> li = new ArrayList<Bean_6>();
		sedao6 se = new sedao6();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(li!=null)
		{
			for(Bean_6 bean:li)
			{
		resp.getWriter().write("<br/>"+"大棚编号: "+bean.getNum()+"&nbsp;"+"收获物: "+bean.getName()+"&nbsp;"+"收获物质量: "+bean.getMl()+"&nbsp;"+"收获日期: "+bean.getDate());
			}
			}
		else
		{
			resp.getWriter().write("您要查找的数据不存在");
		}
	}
	
}
