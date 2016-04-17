package dao;

import java.util.List;

import model.User;
import sqlwhere.core.Where;

public interface UserDAO {
	List<User> getUsers(Where where);
	
	User getUserById(Integer id);
	
	User getUser(String email, String password);
	
	User getUser(String email);
	
	boolean createUser(User user);
	
	boolean updateUser(User user);
	
	boolean deleteUser(User user);
}
