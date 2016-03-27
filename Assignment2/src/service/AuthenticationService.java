package service;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import model.Customer;

public class AuthenticationService {
	CustomerDAO customerDAO = new CustomerDAOImpl();
	
	public boolean authenticateCustomer(String email, String password){
		
		boolean valid = false;
		
		Customer customer = customerDAO.getCustomer(email, password);
		
		if(customer != null){
			valid = true;
		}
		
		return valid;
	}
}
