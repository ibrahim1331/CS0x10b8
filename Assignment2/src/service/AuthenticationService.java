package service;

import dao.UserDAOImpl;
import model.User;

public class AuthenticationService {
	
	public boolean authenticateUser(String email, String password){
		UserDAOImpl UserDAO = new UserDAOImpl();
		
		boolean valid = false;
		
		User user = UserDAO.getUser(email, password);
		
		if(user != null){
			valid = true;
		}
		
		return valid;
	}
}