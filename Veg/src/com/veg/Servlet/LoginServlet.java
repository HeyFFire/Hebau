package com.veg.Servlet;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.veg.dao.Logindao;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean flag=false;
		String uname=req.getParameter("username");
		String pwd=req.getParameter("password");
		String validatetext = req.getParameter("validatetext");
		String str=null;
		Logindao a=new Logindao();
		try {
			 str =a.Connect(uname,pwd);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(str!=null)
		{
			
			HttpSession session = req.getSession();
			String code1=(String)session.getAttribute("code");
			String code= code1.substring(0,code1.length());
			System.out.println(code);
			System.out.println(validatetext);
			if(code.equals(validatetext))
			{
				
				session.setAttribute("uname", str);
				System.out.println(str);
				resp.getWriter().write("1");
			}
			else
			{
				resp.getWriter().write("2");
			}
		
		}
		else
		{
			resp.getWriter().write("’ ∫≈ªÚ’ﬂ√‹¬Î”–ŒÛ");
		}
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
	

}
