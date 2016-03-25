package controller;

import service.AuthenticationService;
import dao.CustomerDAOImpl;
import model.Customer;

public class CustomerController {
	AuthenticationService authService = new AuthenticationService();
	CustomerDAOImpl customerDAO = new CustomerDAOImpl();
	
	public boolean authenticate(String email, String password){
		return authService.authenticateCustomer(email, password);
	}
	
	public Customer getCustomer(String email, String password){
		return customerDAO.getCustomer(email, password);
	}
	
	public boolean createCustomer(Customer customer){
		return customerDAO.createCustomer(customer);
	}
	
	public boolean updateCustomer(Customer customer){
		return customerDAO.updateCustomer(customer);
	}
	
	public boolean deleteCustomer(Customer customer){
		return customerDAO.deleteCustomer(customer);
	}
}
