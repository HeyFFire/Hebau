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
import com.veg.dao.chdao;
import com.veg.dao.chdao1;
import com.veg.dao.totaldao;
import com.veg.dao.totaldao1;
@WebServlet("/chServlet1")
public class chServlet1 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		List<Bean> li=new ArrayList<>();
		String ss=new String();
		int count=0,sum=0;
		String rows=arg0.getParameter("rows");
		String page=arg0.getParameter("page");
		int a = Integer.parseInt(page);
		int b = Integer.parseInt(rows);
		int c =a*b;
		System.out.println(rows+","+page);
	    chdao1 ch = new chdao1();
	    totaldao1 to = new totaldao1();
	    try {
			sum=to.Connect();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    try {
			li=ch.Connect(a, b);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    for(Bean be:li)
	    {
			String sl=JSON.toJSONString(be);
			ss+=sl+",";
			count++;
	    }
	    String ns=ss.substring(0, ss.length()-1);
	    System.out.println(ns);
		String s1="{"+"\"total\""+":"+sum+","+"\"rows\""+":"+"[";
		String s2="]"+"}";
		String str=s1+ns+s2;
		System.out.println(str);
		arg1.getWriter().write(str);
		
		
		
	}
	
}
