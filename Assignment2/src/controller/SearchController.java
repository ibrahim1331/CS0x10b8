package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HotelDAOImpl;
import model.Hotel;
import service.SearchService;
import utils.AppHelper;

public class SearchController extends HttpServlet{
	
	Logger logger = Logger.getLogger(this.getClass().getName());
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
		String result = req.getParameter("city");
		Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
		Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
		logger.log(Level.INFO, String.format("result=[%s], from=[%s], to=[%s]", result, from, to));
		
		List<Hotel> hotels = searchService.searchHotels(result, from, to);
		req.setAttribute("hotels", hotels);
		req.setAttribute("result", result);
		if(from!=null){
			req.setAttribute("fromDate", from);
		}
		if(to!=null){
			req.setAttribute("toDate", to);
		}
		
		gotoPage("/jsp/result.jsp",req,res);
	}

	
	
	private void gotoPage(String address, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req, res);
	}
	
	
}
