package controller;

import service.CustomerService;
import model.Customer;

public class CustomerController {
//	AuthenticationService authService = new AuthenticationService();
	CustomerService customerService = new CustomerService();
	
	public boolean authenticate(String email, String password){
		return customerService.getCustomer(email, password) != null;
	}
	
	public Customer getCustomer(String email, String password){
		return customerService.getCustomer(email, password);
	}
	
	public boolean createCustomer(Customer customer){
		return customerService.createCustomer(customer);
	}
	
	public boolean updateCustomer(Customer customer){
		return customerService.updateCustomer(customer);
	}
	
	public boolean deleteCustomer(Customer customer){
		return customerService.deleteCustomer(customer);
	}
}
