package com.veg.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.veg.dao.Indao3;
import com.veg.dao.Indao2;
@WebServlet("/InServlet2")
public class InServlet2 extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String num = req.getParameter("a");
		
		boolean flag=true;
		String name = req.getParameter("b");
		String date = req.getParameter("c");
		String date_2 = req.getParameter("d");
		String date_3 = req.getParameter("e");
		try {
			flag=Indao2.Connect(num, name, date,date_2,date_3);
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
