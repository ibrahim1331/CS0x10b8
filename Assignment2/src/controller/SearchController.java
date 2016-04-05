package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HotelDAOImpl;
import model.Hotel;

public class SearchController extends HttpServlet{
	
	HotelDAOImpl impl = new HotelDAOImpl();
	Hotel hotel = new Hotel();
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) {
		// TODO Auto-generated method stub
		
		String result;
		result = req.getParameter("city");
		System.out.print(result);
		hotel=impl.getHotelById(Integer.parseInt(result));
		
	}
}
