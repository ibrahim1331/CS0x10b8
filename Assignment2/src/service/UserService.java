package service;

import java.util.List;

import dao.UserDAO;
import dao.UserDAOImpl;
import model.User;


public class UserService {
	AuthenticationService authService = new AuthenticationService();
	UserDAO userDAO = new UserDAOImpl();
	
	public boolean authenticate(String email, String password){
		return userDAO.getUser(email, password)!=null;
	}
	
	public List<User> getAllCustomers(){
		return userDAO.getAllCustomers();
	}
	
	public List<User> getAllManagers(){
		return userDAO.getAllManagers();
	}
	
	public List<User> getAllChiefManagers(){
		return userDAO.getAllChiefManagers();
	}
	
	public User getUser(String email, String password){
		return userDAO.getUser(email, password);
	}
	
	public User getUser(String email){
		return userDAO.getUser(email);
	}
	
	public boolean createUser(User user){
		return userDAO.createUser(user);
	}
	
	public boolean updateUser(User user){
		return userDAO.updateUser(user);
	}
	
	public boolean deleteUser(User user){
		return userDAO.deleteUser(user);
	}
}