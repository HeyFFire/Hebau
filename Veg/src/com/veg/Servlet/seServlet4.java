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
import com.veg.dao.sedao;
import com.veg.dao.sedao2;
import com.veg.dao.sedao4;
@WebServlet("/seServlet4")
public class seServlet4 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		List <Bean_4> li = new ArrayList<Bean_4>();
		sedao4 se = new sedao4();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(li!=null)
		{
			for(Bean_4 bean:li)
			{
		resp.getWriter().write("<br/>"+"������: "+bean.getNum()+"&nbsp;"+"��������: "+bean.getName()+"&nbsp;"+"ʩ����: "+bean.getMl()+"&nbsp;"+"ʩ������: "+bean.getDate());
			}
		}
		else
		{
			resp.getWriter().write("��Ҫ���ҵ����ݲ�����");
		}
	}
	
}
