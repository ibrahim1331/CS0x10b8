package dao;

import java.util.List;

import model.User;

public interface UserDAO {
	List<User> getAllCustomers();
	
	List<User> getAllManagers();
	
	List<User> getAllChiefManagers();
	
	User getUserById(Integer id);
	
	User getUser(String email, String password);
	
	User getUser(String email);
	
	boolean createUser(User user);
	
	boolean updateUser(User user);
	
	boolean deleteUser(User user);
}
