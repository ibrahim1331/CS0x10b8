package service;

import dao.CustomerDAOImpl;
import model.Customer;

public class AuthenticationService {
	
	public boolean authenticateCustomer(String email, String password){
		CustomerDAOImpl CustomerDAO = new CustomerDAOImpl();
		boolean valid = false;
		
		Customer customer = CustomerDAO.getCustomer(email, password);
		
		if(customer != null){
			valid = true;
		}
		
		return valid;
	}
}
