package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Booking;
import model.BookingMeta;
import model.BookingView;
import model.Search;
import model.User;
import service.BookingService;
import service.SearchService;
import utils.AppHelper;

public class BookingController extends HttpServlet {
	BookingService service = new BookingService();

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
		 * Change of plan, when user book a room, that room should be temporarily stored in a list in session
		 * when create, get that list in the session and create all the booking
		 * some of the room may already get reserved by the time user create booking...
		 * resolve: reserve every other rooms else, report to user what rooms cannot be reserved
		 */
		
		/*
		 *  "/": index page
		 *  "/temp": show the booking in session
		 *  "/temp/update?i=...": update the temp booking with index parameter
		 *  "/temp/remove?i=...": remove the temp booking with index param in sesison
		 *  "/createAll" : create booking from tempBookings in the session
		 *  "/record?pin=...","/record?id=...": show booking details
		 *  "/create?id=...[&confirm=1]": create new booking, confirm required
		 *  "/save?id=..." : save this booking to
		 *  "/update?id=...": update booking, validation needed
		 *  "/cancel?id=...": cancel booking, confirmation required
		 */
		if(operation==null || operation.equals("/")){
			this.showBookingList(req, res);
		} else if(operation.equals("/save")){
			this.saveRecord(req, res);
		} else if(operation.equals("/create")){
			this.createBooking(req, res);
		} else if(operation.equals("/record")){
			this.showRecordDetails(req, res);
		} else if(operation.equals("/temp")){
			this.showTempBookings(req, res);
		} else if(operation.equals("/temp/update")){
			this.updateTempBooking(req, res);
		} else if(operation.equals("/temp/remove")){
			this.removeTempBooking(req, res);
		} else if(operation.equals("/createAll")){
			this.createAllBookings(req, res);
		} else if(operation.equals("/update")){
			this.updateBooking(req, res);
		} else if(operation.equals("/cancel")){
			this.cancelBooking(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void updateBooking(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int bookingId;
		try{
			bookingId = Integer.parseInt(req.getParameter("id"));
		} catch (NumberFormatException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing bookingId");
			return;
		}
		BookingView bookingView = service.getBookingViewById(bookingId);
		
		if(bookingView.getCancelled()){
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The booking is cancelled.");
			return;
		}
		if(bookingView.getCustomerId()!=null){
			User loginUser = (User) req.getSession().getAttribute("loginUser");
			if(loginUser==null || !bookingView.getCustomerId().equals(loginUser.getUserId())){
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed to view the booking.");
				return;
			}
		}
		
		if(req.getParameter("confirm")==null){
			req.setAttribute("bookingView", bookingView);
			req.getRequestDispatcher("/jsp/booking/booking-update.jsp").forward(req, res);
		} else {
			int noOfPeople = Integer.parseInt(req.getParameter("noOfPeople"));
			Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
			Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
			String purpose = req.getParameter("purpose");
			
			Booking booking = service.getBookingById(bookingId);
			booking.setNoOfPeople(noOfPeople);
			booking.setCheckInDate(from);
			booking.setCheckOutDate(to);
			booking.setPurpose(purpose);
			
			if(service.updateBooking(booking)){
				req.getSession().setAttribute("successUpdate", true);
				res.sendRedirect(req.getContextPath()+"/booking/record?number="+bookingView.getBookingNumber());
			} else {
				req.getSession().setAttribute("failUpdate", true);
				res.sendRedirect(req.getContextPath()+"/booking/record?number="+bookingView.getBookingNumber());
			}
		}
	}
	
	private void cancelBooking(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int bookingId;
		try{
			bookingId = Integer.parseInt(req.getParameter("id"));
		} catch (NumberFormatException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing bookingId");
			return;
		}
		BookingView bookingView = service.getBookingViewById(bookingId);
		
		if(bookingView.getCancelled()){
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The booking is cancelled.");
			return;
		}
		if(bookingView.getCustomerId()!=null){
			User loginUser = (User) req.getSession().getAttribute("loginUser");
			if(loginUser==null || !bookingView.getCustomerId().equals(loginUser.getUserId())){
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed to view the booking.");
				return;
			}
		}
		if(req.getParameter("confirm")==null){
			req.setAttribute("bookingView", bookingView);
			req.getRequestDispatcher("/jsp/booking/booking-cancel.jsp").forward(req, res);
		} else {
			Booking booking = service.getBookingById(bookingId);
			if(service.cancelBooking(booking)){
				req.getSession().setAttribute("successCancel", true);
				res.sendRedirect(req.getContextPath()+"/booking/record?number="+bookingView.getBookingNumber());
			} else {
				req.getSession().setAttribute("failCancel", true);
				res.sendRedirect(req.getContextPath()+"/booking/record?number="+bookingView.getBookingNumber());
			}
		}
	}
	
	private void removeTempBooking(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int i;
		try{
			i = Integer.parseInt(req.getParameter("i"));
		} catch(NumberFormatException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing index!");
			return;
		}
		List<Booking> tempBookings = (List<Booking>) req.getSession().getAttribute("tempBookings");
		if(tempBookings==null || i>=tempBookings.size()){
			res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "no booking is made or query index too large");
		}
		
		tempBookings.remove(i);
		
		req.getSession().setAttribute("successRemove", true);
		res.sendRedirect(req.getContextPath()+"/booking/temp");
	}
	
	private void updateTempBooking(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int i;
		try{
			i = Integer.parseInt(req.getParameter("i"));
		} catch(NumberFormatException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "missing index!");
			return;
		}
		List<Booking> tempBookings = (List<Booking>) req.getSession().getAttribute("tempBookings");
		if(tempBookings==null || i>=tempBookings.size()){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "no booking is made or query index too large");
			return;
		}
		if(req.getParameter("confirm")==null){
			req.setAttribute("booking", tempBookings.get(i));
			req.setAttribute("i", i);
			req.setAttribute("roomView", service.getSearch(tempBookings.get(i).getRoomId()));
			req.getRequestDispatcher("/jsp/booking/booking-temp-confirm.jsp").forward(req, res);
		} else {
			int noOfPeople = Integer.parseInt(req.getParameter("noOfPeople"));
			Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
			Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
			String purpose = req.getParameter("purpose");
			if(from==null || to==null || purpose==null){
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required information.");
			} else {
				Booking booking = tempBookings.get(i);
				booking.setNoOfPeople(noOfPeople);
				booking.setCheckInDate(from);
				booking.setCheckOutDate(to);
				booking.setPurpose(purpose);
				
				req.getSession().setAttribute("successUpdate", true);
				res.sendRedirect(req.getContextPath()+"/booking/temp");
			}
		}
		
	}
	
	private void showTempBookings(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/booking/booking-temp.jsp").forward(req, res);
	}
	
	private void saveRecord(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Booking> tempBookings;
		if(req.getSession().getAttribute("tempBookings")!=null){
			tempBookings = (List<Booking>) req.getSession().getAttribute("tempBookings");
		} else {
			tempBookings = new ArrayList<Booking>();
		}
		int roomId;
		try{
			roomId = Integer.parseInt(req.getParameter("id"));
		} catch(NumberFormatException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request does not include room identity.");
			return;
		}
		int noOfPeople = Integer.parseInt(req.getParameter("noOfPeople"));
		Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
		Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
		String purpose = req.getParameter("purpose");
		if(from==null || to==null || purpose==null){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required information.");
		} else {
			Booking booking = new Booking();
			booking.setBookingDate(AppHelper.getCurrentTimestamp());
			booking.setCheckInDate(from);
			booking.setCheckOutDate(to);
			booking.setPurpose(purpose);
			booking.setRoomId(roomId);
			booking.setNoOfPeople(noOfPeople);
			booking.setCancelled(false);
			int price = service.getSearch(roomId).getRoomPrice();
			booking.setPrice(price);
			tempBookings.add(booking);
			
			req.getSession().setAttribute("tempBookings", tempBookings);
			//saving success!!! notify the user
			req.getSession().setAttribute("successAdd", true);
			res.sendRedirect(req.getContextPath()+"/booking/temp");
		}
	}
	
	private void createAllBookings(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		List<Booking> tempBookings = (List<Booking>) req.getSession().getAttribute("tempBookings");
		
		User user = (User) req.getSession().getAttribute("loginUser");
		
		for(Booking b: tempBookings){
			if(user!=null){
				b.setCustomerId(user.getUserId());
			}
		}
		
		BookingMeta meta = service.createBookings(tempBookings);
		
		if(meta.isAllFailed()){
			req.getSession().setAttribute("failAdd", true);
			res.sendRedirect(req.getContextPath()+"/booking/temp");
		} else {
			req.setAttribute("pin", meta.getPin());
			req.setAttribute("failed", meta.getFailed());
				
			//after created remove the list from session
			req.getSession().removeAttribute("tempBookings");
			req.getRequestDispatcher("/jsp/booking/booking-success.jsp").forward(req, res);
		}
	}
		
	private void showRecordDetails(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		boolean checkById = true;
		String pin = req.getParameter("pin");
		int number = 0;
		try{
			number = Integer.parseInt(req.getParameter("number"));
		} catch(NumberFormatException ex){
			checkById = false;
		}
		List<BookingView> bookingView = null;
		if(checkById){
			bookingView = service.getBookingViewByBookingNumber(number);
		} else {
			bookingView = service.getBookingViewByPin(pin);
		}
		
		if(!bookingView.isEmpty()){
			User loginUser = (User) req.getSession().getAttribute("loginUser");
			if(bookingView.get(0).getCustomerId()!=null){
				if(loginUser==null || !bookingView.get(0).getCustomerId().equals(loginUser.getUserId())){
					res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not allowed to view the booking");
					return;
				}
			}
			
			req.setAttribute("bookingNumber", bookingView.get(0).getBookingNumber());
			req.setAttribute("bookingView", bookingView);
			req.getRequestDispatcher("/jsp/booking/booking-details.jsp").forward(req, res);
		} else {
			req.getRequestDispatcher("/jsp/booking/booking-404.jsp").forward(req, res);
		}
		
	}
	
	private void showBookingList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		User user = (User) req.getSession().getAttribute("loginUser");
		if(user!=null){
			List<BookingView> bookings = service.getBookingViews(user.getUserId());
			req.setAttribute("bookings", bookings);
		}
		req.getRequestDispatcher("/jsp/booking/index.jsp").forward(req, res);
	}
	
	private void createBooking(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int roomId;
		try{
			roomId = Integer.parseInt(req.getParameter("id"));
		} catch(NumberFormatException ex){
			res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Request does not include room identity.");
			return;
		}
		Search roomView = service.getSearch(roomId);
		if(req.getParameter("confirm")==null){
			Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
			Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
			req.setAttribute("roomView", roomView);
			req.setAttribute("fromDate", from);
			req.setAttribute("toDate", to);
			req.getRequestDispatcher("/jsp/booking/booking-confirm.jsp").forward(req, res);
		} else {
			int noOfPeople = Integer.parseInt(req.getParameter("noOfPeople"));
			Timestamp from = AppHelper.getTimestamp(req.getParameter("fromDate"));
			Timestamp to = AppHelper.getTimestamp(req.getParameter("toDate"));
			String purpose = req.getParameter("purpose");
			if(from==null || to==null || purpose==null){
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing required information.");
			} else {
				Booking booking = new Booking();
				booking.setBookingDate(AppHelper.getCurrentTimestamp());
				booking.setCheckInDate(from);
				booking.setCheckOutDate(to);
				booking.setPurpose(purpose);
				booking.setRoomId(roomId);
				booking.setNoOfPeople(noOfPeople);
				booking.setCancelled(false);
				User user = (User) req.getSession().getAttribute("loginUser");
				if(user!=null){
					booking.setCustomerId(user.getUserId());
				}
				
				String pin = service.createBooking(booking); 
				if(pin!=null){
					//create success!!!
					req.setAttribute("pin", pin);
					req.getRequestDispatcher("/jsp/booking/booking-success.jsp").forward(req, res);
				} else {
					//fail!!!
					res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "cannot create booking.");
				}
			}
		}
	}

}
