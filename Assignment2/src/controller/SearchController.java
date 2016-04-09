package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HotelDAOImpl;
import model.Hotel;

public class SearchController extends HttpServlet{
	
	HotelDAOImpl impl = new HotelDAOImpl();
	ArrayList <Hotel> hotels = new ArrayList<>();
	
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
		hotels = impl.getAllHotels();
		
		//Split the ArrayList<Hotel> hotels
		ArrayList <String> hotelNames = new ArrayList<>();
		ArrayList <String> location = new ArrayList<>();
		ArrayList <Integer> noOfRooms = new ArrayList<>();
		ArrayList <Float> rating = new ArrayList<>();
		
		for (int i=0;i<hotels.size();i++){
			hotelNames.add(hotels.get(i).getName());
			location.add(hotels.get(i).getLocation());
			noOfRooms.add(hotels.get(i).getNoOfRooms());
			rating.add(hotels.get(i).getRating());
		}
		
		req.setAttribute("hotelNames", hotelNames);
		req.setAttribute("location", location);
		req.setAttribute("noOfRooms", noOfRooms);
		req.setAttribute("rating", rating);
		req.setAttribute("count", hotels.size());
		req.setAttribute("hotels", hotels);
		
		System.out.print("The counter is" + hotels.size());
		gotoPage("/jsp/result.jsp",req,res);
	}

	
	
	private void gotoPage(String address, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req, res);
	}
	
	
}
