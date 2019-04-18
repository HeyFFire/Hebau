package com.veg.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.veg.dao.deldao;
import com.veg.dao.deldao2;
import com.veg.dao.deldao4;
import com.veg.dao.deldao5;
@WebServlet("/delServlet5")
public class delServlet5 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		boolean flag=false;
		deldao5 del = new deldao5();
		try {
			flag=del.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag)
		{
			System.out.println("É¾³ý³É¹¦");
		}
		else
		{
			System.out.println("É¾³ýÊ§°Ü");
		}
		
		
	}

	
}
