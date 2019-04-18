package com.veg.Servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ValidateCode")
public class ValidateServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		HttpSession session = arg0.getSession();
		//´´½¨Í¼Æ¬	
		BufferedImage image = new BufferedImage(200,100,BufferedImage.TYPE_INT_RGB);
		//»­±Ê	
		Graphics2D gra = image.createGraphics();
		gra.setColor(Color.white);
		gra.fillRect(0, 0, 200, 100);
		List<Integer> randList=new ArrayList<Integer>();
		Random random=new Random();
		for(int i=0;i<4;i++)
		{
			randList.add(random.nextInt(10));
		}
		Color colors[]= new Color[]{Color.black,Color.BLUE,Color.gray,Color.green,Color.pink};
		gra.setFont(new Font("ËÎÌå",Font.BOLD|Font.ITALIC, 40));
		for(int i=0;i<randList.size();i++)
		{
			gra.setColor(colors[random.nextInt(colors.length)]);
			gra.drawString(randList.get(i)+"", i*40, 70+(random.nextInt(21)-10));
		}
		for(int i=0;i<2;i++)
		{
			gra.setColor(colors[random.nextInt(colors.length)]);
			gra.drawLine(0,random.nextInt(101), 200, random.nextInt(101));
		}
		
		ServletOutputStream  outputstream= arg1.getOutputStream();
		ImageIO.write(image, "jpg", outputstream);
		
		session.setAttribute("code",""+randList.get(0)+randList.get(1)+randList.get(2)+randList.get(3));
	}
	
	
}
