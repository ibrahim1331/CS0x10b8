package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HotelDAOImpl;
import model.Hotel;
import service.SearchService;

public class SearchController extends HttpServlet{
	
	HotelDAOImpl impl = new HotelDAOImpl();
	SearchService searchService = new SearchService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result, fromDate, toDate;
		result = req.getParameter("city");
		fromDate = req.getParameter("fromDate");
		toDate = req.getParameter("toDate");
		//System.out.print(result + " " + fromDate + " " + " " + toDate);
		List<Hotel> hotels = searchService.searchHotels(result, null, null);
//		hotel = impl.getHotelByName(result);
//		req.setAttribute("hotel", hotel);
		req.setAttribute("hotels", hotels);
		gotoPage("/jsp/result.jsp",req,res);
	}

	
	
	private void gotoPage(String address, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req, res);
	}
	
	
}
