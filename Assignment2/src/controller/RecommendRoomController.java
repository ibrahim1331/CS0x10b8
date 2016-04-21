package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enums.Role;
import model.Room;
import model.User;
import service.RecommendRoomService;

public class RecommendRoomController extends HttpServlet{
	RecommendRoomService service = new RecommendRoomService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.processRequest(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		this.processRequest(req, res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String operation = req.getPathInfo();
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		/*
		 *  "/": show hotels
		 *  "/rooms?hotelId=...": show rooms of the selected hotel
		 *  "/rooms/recommend?id=...&recommend=...": 
		 */ 
		if(!((User) req.getSession().getAttribute("loginUser")).getRole().equals(Role.Manager)){
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not a manager.");
		} else {
			if(operation==null){
				this.showHotelList(req, res);
			} else if(operation.equals("/rooms")){
				this.showRoomList(req, res);
			} else if(operation.equals("/rooms/recommend")){
				this.recommendRoom(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_NOT_FOUND);
			}
		}
	}
	
	private void showHotelList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("hotels", service.getHotels());
		req.getRequestDispatcher("/jsp/recommend-room/index.jsp").forward(req, res);
	}
	
	private void showRoomList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int hotelId = Integer.parseInt(req.getParameter("hotel"));
		req.setAttribute("hotel", service.getHotel(hotelId));
		req.setAttribute("recommendingRooms", service.getRecommendingRooms(hotelId));
		req.setAttribute("nonRecommendingRooms", service.getNonRecommendingRooms(hotelId));
		req.getRequestDispatcher("/jsp/recommend-room/room.jsp").forward(req, res);
	}
	
	private void recommendRoom(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int roomId = Integer.parseInt(req.getParameter("id"));
		int recommend = Integer.parseInt(req.getParameter("recommend"));
		
		Room room = service.getRoom(roomId);
		if(room==null){
			res.sendError(HttpServletResponse.SC_NOT_FOUND, "No such room.");
		} else {
			room.setRecommended(recommend);
			if(service.recommendRoom(room)){
				this.goRoomPage(req, res, room);
			} else {
				this.goRoomPage(req, res, room, "Fail to recommend room.");
			}
		}
	}
	
	private void goRoomPage(HttpServletRequest req, HttpServletResponse res, Room room, String errorMsg) throws ServletException, IOException {
		if(errorMsg!=null){
			req.getSession().setAttribute("errorMsg", errorMsg);
		} else{
			req.getSession().setAttribute("success", true);
		}
		res.sendRedirect(req.getContextPath()+"/recommend-room/rooms?hotel="+room.getHotelId());
	}
	
	private void goRoomPage(HttpServletRequest req, HttpServletResponse res, Room room) throws ServletException, IOException {
		this.goRoomPage(req, res, room, null);
	}
		
}
