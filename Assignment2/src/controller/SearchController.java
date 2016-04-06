package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result;
		result = req.getParameter("city");
		System.out.print(result);
		hotel=impl.getHotelById(Integer.parseInt(result));
		System.out.print(hotel.getName());
		req.setAttribute("name", hotel.getName());
		gotoPage("/jsp/result.jsp",req,res);
	}
	
	private void gotoPage(String address, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req, res);
	}
}
