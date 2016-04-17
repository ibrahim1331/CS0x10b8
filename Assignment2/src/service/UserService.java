package service;

import java.util.List;

import dao.UserDAO;
import dao.UserDAOImpl;
import enums.Role;
import model.User;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Equal;
import utils.Columns;


public class UserService {
	AuthenticationService authService = new AuthenticationService();
	UserDAO userDAO = new UserDAOImpl();
	
	public boolean authenticate(String email, String password){
		return userDAO.getUser(email, password)!=null;
	}
	
	public List<User> getAllUsers(){
		return userDAO.getUsers(null);
	}
	
	public List<User> getAllCustomers(){
		return userDAO.getUsers(new Where(new Equal(Columns.Table.User.ROLE, Role.Customer.getValue())));
	}
	
	public List<User> getAllManagers(){
		return userDAO.getUsers(new Where(new Equal(Columns.Table.User.ROLE, Role.Manager.getValue())));
	}
	
	public List<User> getAllChiefManagers(){
		return userDAO.getUsers(new Where(new Equal(Columns.Table.User.ROLE, Role.Chief_Manager.getValue())));
	}
	
	public User getUserById(int id){
		return userDAO.getUserById(id);
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