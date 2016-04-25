package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import model.Booking;
import model.Search;
import service.BookingService;
import utils.AppHelper;

public class RestBookingController extends HttpServlet{
	BookingService service = new BookingService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.processRequest(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.processRequest(req, res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		/*
		 * "/booking/checkAvailable": return false or true based on room available
		 * "/booking/getRooms": return list of rooms of hotel
		 */
		String operation = req.getPathInfo();
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		
		if(operation!=null){
			if(operation.equals("/checkAvailable")){
				Booking b = AppHelper.getGson().fromJson(req.getReader(), Booking.class);
				Map<String, Object> result = new HashMap<>();
				if(service.checkAvailability(b)){
					result.put("available", true);
					result.put("msg", "The room is available for booking!");
				} else {
					result.put("available", false);
					result.put("msg", "The room is not available.");
				}
				res.setContentType("application/json");
				res.getWriter().print(AppHelper.getGson().toJson(result));
			} else if(operation.equals("/getRoom")){
				JsonElement je = new JsonParser().parse(req.getReader());
				if(je.isJsonObject() && je.getAsJsonObject().has("roomId")){
					int roomId = je.getAsJsonObject().get("roomId").getAsInt();
					Search result = service.getSearch(roomId);
					res.setContentType("application/json");
					res.getWriter().print(AppHelper.getGson().toJson(result));
				} else {
					Map<String, Object> result = new HashMap<>();
					result.put("errorMsg", "missing roomId!!!");
					res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
					res.setContentType("application/json");
					res.getWriter().print(AppHelper.getGson().toJson(result));
				}

			} else {
				this.pathNotFound(res);
			}
		} else {
			this.pathNotFound(res);
		}
	}
	
	private void pathNotFound(HttpServletResponse res) throws ServletException, IOException{
		Map<String, Object> error = new HashMap<>();
		error.put("errorMsg", "request url not available");
		res.setStatus(HttpServletResponse.SC_NOT_FOUND);
		res.setContentType("application/json");
		res.getWriter().print(AppHelper.getGson().toJson(error));
	}

}
