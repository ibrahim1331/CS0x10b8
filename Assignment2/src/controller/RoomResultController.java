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

import model.Hotel;
import model.Room;
import service.SearchService;
import utils.AppHelper;

public class RoomResultController extends HttpServlet{
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	SearchService searchService = new SearchService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int id = Integer.parseInt(req.getParameter("id"));
		Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
		Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
		if(from==null){
			from = AppHelper.getCurrentTimestamp();
		}
		if(to==null){
			to = AppHelper.getTimestampAfterDays(7);
		}
		logger.log(Level.INFO, String.format("id=[%s], from=[%s], to=[%s]", id, from, to));
		Hotel hotel = searchService.getHotels(id);
		List<Room> recommendingRooms = searchService.getRecommendingRooms(id, from, to);
		List<Room> nonRecommendingRooms = searchService.getNonRecommendingRooms(id, from, to);
		
		req.setAttribute("fromDate", from);
		req.setAttribute("toDate", to);
		req.setAttribute("hotel", hotel);
		req.setAttribute("recommendingRooms", recommendingRooms);
		req.setAttribute("nonRecommendingRooms", nonRecommendingRooms);
		
		gotoPage("/jsp/roomResult.jsp",req,res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	private void gotoPage(String address, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req, res);
	}
	

}
