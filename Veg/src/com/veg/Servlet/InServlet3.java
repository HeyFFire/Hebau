package com.veg.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.veg.dao.Indao3;
@WebServlet("/InServlet3")
public class InServlet3 extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String num = req.getParameter("a");
		boolean flag=true;
		String water_1 = req.getParameter("b");
		int water=Integer.parseInt(water_1);
		System.out.println("imherer");
		String date = req.getParameter("c");
		try {
			flag=Indao3.Connect(num, water, date);
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
