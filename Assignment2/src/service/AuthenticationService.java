package service;

import dao.UserDAOImpl;
import model.User;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Like;
import dao.BookingDAO;
import dao.BookingDAOImpl;
import dao.SearchDAO;
import dao.SearchDAOImpl;

import service.ValidationService;

public class AuthenticationService {
	
	public boolean authenticateUser(String email, String password){
		UserDAOImpl UserDAO = new UserDAOImpl();
		
		boolean valid = false;
		
		User user = UserDAO.getUser(email, password);
		
		if(user != null){
			valid = true;
		}
		
//		BookingDAO bookingDAO = new BookingDAOImpl();
		
//		System.out.println(bookingDAO.getBookings("{'booking_id':2,'no_of_people':3}", "{'booking_id':'desc'}"));
		
//		SearchDAO searchDAO = new SearchDAOImpl();
		
//		System.out.println(searchDAO.search(new Where(new Like("hotel_name", "%Casino%"))));
//		ValidationService validationServ = new ValidationService();
		
//		System.out.println(validationServ.removeScripts("asdasd as d asd asd<script>function(){alert('hello');}</script> asd asd asdasd<a onclick=\"a\"></a><a href=\"javascript:alert(\"hello\")\"></a>"));
		
		return valid;
	}
}