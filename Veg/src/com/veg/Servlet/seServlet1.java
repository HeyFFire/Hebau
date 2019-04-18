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
import com.veg.bean.Bean;
import com.veg.bean.Bean_1;
import com.veg.dao.sedao;
import com.veg.dao.sedao1;
@WebServlet("/seServlet1")
public class seServlet1 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String a = req.getParameter("num");
		List <Bean> li = new ArrayList<Bean>();
		sedao1 se = new sedao1();
		try {
			li=se.Connect(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		if(li!=null)
		{
			for(Bean bean:li)
			{
			resp.getWriter().write("<br/>"+"������: "+bean.getNum()+"<br/>"+"����ʡ: "+bean.getProvince()+"<br/>"+"������: "+bean.getCity()
		+"<br/>"+"������(��): "+bean.getCounty()+"<br/>"+"���ڴ�: "+bean.getVillage()+"<br/>"+"����: "+bean.getX()+"<br/>"+"γ��: "+bean.getY()+"<br/>"+"���֤��: "+bean.getID()+"<br/>"+"���: "+bean.getSquare()+"<br/>"+"��������: "+bean.getGreenhouse()+"<br/>"+"����ṹ����: "+bean.getMain()+"<br/>"+"��������: "+bean.getDate());
			}
			}
		else
		{
			
				resp.getWriter().write("��Ҫ���ҵ����ݲ�����");
			
		}
	}
	
}
