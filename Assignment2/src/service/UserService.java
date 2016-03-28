package service;

import service.AuthenticationService;

import java.util.List;

import dao.UserDAOImpl;
import model.User;


public class UserService {
	AuthenticationService authService = new AuthenticationService();
	UserDAOImpl UserDAO = new UserDAOImpl();
	
	public boolean authenticate(String email, String password){
		return authService.authenticateUser(email, password);
	}
	
	public List<User> getAllCustomers(){
		return UserDAO.getAllCustomers();
	}
	
	public List<User> getAllManagers(){
		return UserDAO.getAllManagers();
	}
	
	public List<User> getAllChiefManagers(){
		return UserDAO.getAllChiefManagers();
	}
	
	public User getUser(String email, String password){
		return UserDAO.getUser(email, password);
	}
	
	public boolean createUser(User user){
		return UserDAO.createUser(user);
	}
	
	public boolean updateUser(User user){
		return UserDAO.updateUser(user);
	}
	
	public boolean deleteUser(User user){
		return UserDAO.deleteUser(user);
	}
}