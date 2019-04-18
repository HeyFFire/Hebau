/**
 * InServlet 1-6 分别对应info 1-6
 * 
 * 
 * 
 */
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
import com.veg.dao.Indao;
@WebServlet("/InServlet")
public class InServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String num = req.getParameter("a");
		boolean flag=true;
		String province = req.getParameter("b");
		String city = req.getParameter("c");
		String county= req.getParameter("d");
		String village= req.getParameter("e");
		String x1= req.getParameter("f");
		double x=Double.parseDouble(x1);
		String y1= req.getParameter("g");
		double y=Double.parseDouble(y1);
		String h= req.getParameter("h");
		String ID=req.getParameter("h");
		String i= req.getParameter("i");
		double square = Double.parseDouble(i);
		String greenhouse= req.getParameter("j");
		String main= req.getParameter("k");
		String date= req.getParameter("l");
		System.out.println(greenhouse+main);
		try {
			flag=Indao.Connect(num,province,city,county,village,x,y,ID,square,greenhouse,main,date);
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
