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
@WebServlet("/delServlet4")
public class delServlet4 extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		boolean flag=false;
		deldao4 del = new deldao4();
		try {
			flag=del.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(flag)
		{
			System.out.println("ɾ���ɹ�");
		}
		else
		{
			System.out.println("ɾ��ʧ��");
		}
		
		
	}

	
}
