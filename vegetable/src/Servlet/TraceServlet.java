package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import Dao.DetailDao;
import bean.Details;

@WebServlet("/TraceServlet")
public class TraceServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		HttpSession session = arg0.getSession();
		String address = (String) session.getAttribute("address");
		Details detail = new Details();
		System.out.println("TraceServlet");
		System.out.println(address);
		DetailDao det = new DetailDao();
		try {
			detail=det.Connect(address);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		arg1.getWriter().write("<html>");
		arg1.getWriter().write("<ul>");
		arg1.getWriter().write("<li>"+"建造日期:"+detail.getDate()+"</li>");
		arg1.getWriter().write("<li>"+"基地编号:"+detail.getNum()+"</li>");
		arg1.getWriter().write("<li>"+"大棚类型:"+detail.getGreenhouse()+"</li>");
		arg1.getWriter().write("<li>"+"大棚结构:"+detail.getMain()+"</li>");
		arg1.getWriter().write("</ul>");
		arg1.getWriter().write("</html>");
		
	}

	
}
