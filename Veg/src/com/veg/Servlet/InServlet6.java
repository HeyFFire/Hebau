package com.veg.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.veg.dao.Indao3;
import com.veg.dao.Indao2;
import com.veg.dao.Indao4;
import com.veg.dao.Indao5;
import com.veg.dao.Indao6;
@WebServlet("/InServlet6")
public class InServlet6 extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String num = req.getParameter("a");
		boolean flag=true;
		String name = req.getParameter("b");
		String kg = req.getParameter("c");
		String date_2 = req.getParameter("d");
		try {
			flag=Indao6.Connect(num,name,kg,date_2);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ok");
		if(flag)
		{
		resp.getWriter().write("插入失败!请检查数据是否有误");
		}
		else
		{
			resp.getWriter().write("插入成功");
		}
		
	}
 
}
