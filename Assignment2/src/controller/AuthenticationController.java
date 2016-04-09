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
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
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
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String title = req.getParameter("title");
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String gender = req.getParameter("gender");
		
		if(email!=null && password!=null && title!=null 
				&& firstName!=null && lastName!=null && gender!=null){
			if(validServ.validateEmail(email)){
				User user = new User();
				user.setEmail(email);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setGender(gender);
				user.setPassword(password);
				user.setTitle(title);
				user.setIsRegistered(true);
				user.setRole(Role.Customer.getValue());
				
				if(userDAO.getUser(email)!=null){
					res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					res.setContentType("text/html");
					res.getWriter().print("This email is registered.");
				} else if(userDAO.createUser(user)){
					user = userDAO.getUser(email, password);
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
