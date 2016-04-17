package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HotelDAOImpl;
import dao.RoomDAOImpl;
import model.Hotel;
import model.Room;

public class RoomResultController extends HttpServlet{
	
	HotelDAOImpl hotelImpl = new HotelDAOImpl();
	RoomDAOImpl roomImpl = new RoomDAOImpl();
	ArrayList<Room> rooms = new ArrayList<>();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.processRequest(req,res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = req.getParameter("id");
		int id = Integer.parseInt(result);
		Hotel reultHotel = hotelImpl.getHotelById(id);
		rooms = (ArrayList) roomImpl.getAllRoomsofHotel(reultHotel);
		req.setAttribute("rooms", rooms);
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
