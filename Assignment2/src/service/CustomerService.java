package service;

import dao.CustomerDAO;
import dao.CustomerDAOImpl;
import model.Customer;

public class CustomerService {
	CustomerDAO customerDAO = new CustomerDAOImpl();
	
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
