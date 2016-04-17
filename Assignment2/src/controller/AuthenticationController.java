package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import dao.UserDAOImpl;
import enums.Role;
import model.User;
import service.AuthenticationService;
import service.ValidationService;
import utils.AppHelper;

/**
 * responsible for routing /login /logout /register under /auth
 * @author lausinleung
 *
 */
public class AuthenticationController extends HttpServlet {
	UserDAO userDAO = new UserDAOImpl();
	AuthenticationService authServ = new AuthenticationService();
	ValidationService validServ = new ValidationService();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.processRequest(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.processRequest(req, res);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String operation = req.getPathInfo();
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		
		if(operation.equals("/login")){
			this.login(req, res);
		} else if(operation.equals("/logout")){
			this.logout(req, res);
		} else if(operation.equals("/register")){
			this.register(req, res);
		}  
	}
	
	//ajax call
	private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "login");
		
		User userReq = AppHelper.getGson().fromJson(req.getReader(), User.class);
		
		String email = userReq.getEmail();
		String password = userReq.getPassword();
		
		if(email!=null && password!=null){
			if(authServ.authenticateUser(email, password)){
				//login success
				User user = userDAO.getUser(email, password);
				req.getSession().setAttribute("loginUser", user);
				res.setContentType("text/html");
				res.getWriter().print(req.getContextPath()+"/");
			} else {
				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				res.setContentType("text/html");
				res.getWriter().print("login fail!!! user not found!");
			}
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType("text/html");
			res.getWriter().print("email or password is missing!");
		}
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "logout");
		req.getSession().invalidate();
		res.sendRedirect(req.getContextPath()+"/");
	}
	
	//ajax
	private void register(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "register");
		
		//get request body and convert it to User object with gson...
		User userReq = AppHelper.getGson().fromJson(req.getReader(), User.class);
		userReq.setRole(Role.Customer.getValue());
		userReq.setRegister(true);
		
		if(userReq.getEmail()!=null 
				&& userReq.getPassword()!=null 
				&& userReq.getFirstName()!=null 
				&& userReq.getLastName()!=null 
				&& userReq.getTitle()!=null 
				&& userReq.getGender()!=null){
			if(validServ.validateEmail(userReq.getEmail())){
				if(userDAO.getUser(userReq.getEmail())!=null){
					res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					res.setContentType("text/html");
					res.getWriter().print("This email is registered.");
				} else if(userDAO.createUser(userReq)){
					User user = userDAO.getUser(userReq.getEmail(), userReq.getPassword());
					req.getSession().setAttribute("loginUser", user);
					res.setContentType("text/html");
					res.getWriter().print(req.getContextPath()+"/");
				} else {
					res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					res.setContentType("text/html");
					res.getWriter().print("Unable to create user. Please try later.");
				}
			}else{
				res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				res.setContentType("text/html;charset=utf-8");
				res.getWriter().print("email is not in valid format");
			}
		} else {
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			res.setContentType("text/html");
			res.getWriter().print("some required information is missing!!!");
		}
	}
}
