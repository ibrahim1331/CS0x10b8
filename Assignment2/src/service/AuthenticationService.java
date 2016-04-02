package service;

import dao.UserDAOImpl;
import model.User;


import dao.BookingDAO;
import dao.BookingDAOImpl;

public class AuthenticationService {
	
	public boolean authenticateUser(String email, String password){
		UserDAOImpl UserDAO = new UserDAOImpl();
		
		boolean valid = false;
		
		User user = UserDAO.getUser(email, password);
		
		if(user != null){
			valid = true;
		}
		
		BookingDAO bookingDAO = new BookingDAOImpl();
		
		System.out.println(bookingDAO.getBookings("{'booking_id':1,'no_of_people':3}", "{'booking_id':'desc'}").toString());
		
		return valid;
	}
}