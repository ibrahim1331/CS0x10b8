package dao;

import java.util.List;

import model.Customer;

public interface CustomerDAO {
	List<Customer> getAllCustomers();
	
	Customer getCustomerById(Integer id);
	
	Customer getCustomer(String email, String password);
	
	Customer getCustomer(String email);
	
	boolean createCustomer(Customer customer);
	
	boolean updateCustomer(Customer customer);
	
	boolean deleteCustomer(Customer customer);
}
