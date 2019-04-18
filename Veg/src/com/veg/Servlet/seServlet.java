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
import com.veg.dao.sedao;
@WebServlet("/seServlet")
public class seServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		List<Bean_1> li =new ArrayList<>();
		sedao se = new sedao();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(li!=null)
		{
//		String str = JSON.toJSONString(bean);
//		System.out.println(str);
		for(Bean_1 bean:li)
		{
		String st=bean.getNum();
		String st_2=bean.getWater();
		String st_3=bean.getDate();
		
		System.out.println(bean.getDate());
		resp.getWriter().write("<br/>"+"������: "+st+"&nbsp;"+"��ˮ��: "+st_2+"&nbsp;"+"��������: "+st_3);
		}
		}
		else
		{
			resp.getWriter().write("��Ҫ���ҵ����ݲ�����");
		}
	}
	
}
