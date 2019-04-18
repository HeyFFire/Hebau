package Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import Dao.getDao;
import bean.Features;

@WebServlet("/getAllServlet")
public class getAllServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		getDao getdao = new getDao();
		String nostr="";
		List<Features>list = new ArrayList<Features>();
		try {
			list = getdao.get();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Features fatures:list)
		{
		nostr += JSON.toJSONString(fatures)+",";
		}
		String str=nostr.substring(0, nostr.length()-1);
		String str1="{\"type\":\"FeatureCollection\",\"features\":[";
		String str2="]}";
		String allstr=str1+str+str2;
		arg1.getWriter().write(allstr);
		
	}

	
}
