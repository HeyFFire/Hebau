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
import com.veg.dao.sedao;
import com.veg.dao.sedao2;
import com.veg.dao.sedao4;
import com.veg.dao.sedao5;
@WebServlet("/seServlet5")
public class seServlet5 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		List<Bean_5> li = new ArrayList<>();
		sedao5 se = new sedao5();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(li!=null)
		{
			for(Bean_5 bean:li)
			{
		resp.getWriter().write("<br/>"+"������: "+bean.getNum()+"&nbsp;"+"ũҩ����: "+bean.getName()+"&nbsp;"+"ʩҩ��: "+bean.getMl()+"&nbsp;"+"ֲ������: "+bean.getDate());
			}
			}
		else
		{
			resp.getWriter().write("��Ҫ���ҵ����ݲ�����");
		}
	}
	
}
