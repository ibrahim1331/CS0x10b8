package dao;

import java.util.List;

import model.Customer;

public interface CustomerDAO {
	List<Customer> getCustomer();
	
	Customer getCustomerById(Integer id);
}
