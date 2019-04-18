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
import com.veg.dao.sedao;
import com.veg.dao.sedao2;
@WebServlet("/seServlet2")
public class seServlet2 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		
		List <Bean_2> li = new ArrayList<>();
		sedao2 se = new sedao2();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(li!=null)
		{
			for(Bean_2 bean:li)
			{
		resp.getWriter().write("<br/>"+"大棚编号: "+bean.getNum()+"&nbsp;"+"种植蔬菜名称: "+bean.getName()+"&nbsp;"+"种植日期: "+bean.getPdate()+"&nbsp;"+"收获开始日期: "+bean.getSdate()+"&nbsp;"+"收获结束日期: "+bean.getEdate()+"&nbsp;");
			}
			}
		else
		{
			resp.getWriter().write("您要查找的数据不存在");
		}
	}
	
}
