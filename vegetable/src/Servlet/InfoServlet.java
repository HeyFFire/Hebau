package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;

import Dao.InquiryDao;
import bean.Infobean;
@WebServlet("/InfoServlet")
public class InfoServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		String a = arg0.getParameter("daddress");
		HttpSession session = arg0.getSession();
		session.setAttribute("address", a);
		String demo = (String) session.getAttribute("address");
		System.out.println(demo);
		Infobean bean = new Infobean();
		try {
			bean=InquiryDao.inquiry(a);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String str =JSON.toJSONString(bean);
		arg1.getWriter().write(str);
		
	}

	
}
