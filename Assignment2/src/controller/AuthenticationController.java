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
		
		if(operation==null || operation.equals("/")){
			req.getRequestDispatcher("/jsp/login-register.jsp").forward(req, res);
		} else if(operation.equals("/login")){
			this.login(req, res);
		} else if(operation.equals("/logout")){
			this.logout(req, res);
		} else if(operation.equals("/register")){
			this.register(req, res);
		}  
	}
	
	private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "login");
		
//		User userReq = AppHelper.getGson().fromJson(req.getReader(), User.class);
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if(email!=null && password!=null){
			if(authServ.authenticateUser(email, password)){
				//login success
				User user = userDAO.getUser(email, password);
				req.getSession().setAttribute("loginUser", user);
				res.sendRedirect(req.getContextPath()+"/");
			} else {
//				res.setStatus(HttpServletResponse.SC_NOT_FOUND);
				req.getSession().setAttribute("errorMsg", "login fail!!! user not found!");
				res.sendRedirect(req.getContextPath()+"/auth");
			}
		} else {
//			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			req.getSession().setAttribute("errorMsg","email or password is missing!");
			res.sendRedirect(req.getContextPath()+"/auth");
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
//		User userReq = AppHelper.getGson().fromJson(req.getReader(), User.class);
		
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		String title = req.getParameter("title");
		String gender = req.getParameter("gender");
		
		User user = new User();
		user.setEmail(email);
		user.setFirstName(firstName);
		user.setGender(gender);
		user.setLastName(lastName);
		user.setPassword(password);
		user.setRegister(true);
		user.setRole(Role.Customer.getValue());
		user.setTitle(title);
		
		if(email!=null 
				&& password!=null 
				&& firstName!=null 
				&& lastName!=null 
				&& title!=null 
				&& gender!=null){
			if(validServ.validateEmail(email)){
				if(userDAO.getUser(email)!=null){
					req.getSession().setAttribute("errorMsg","This email is registered.");
					res.sendRedirect(req.getContextPath()+"/auth/");
				} else if(userDAO.createUser(user)){
					user = userDAO.getUser(email, password);
					req.getSession().setAttribute("loginUser", user);
					res.sendRedirect(req.getContextPath()+"/");
				} else {
					req.getSession().setAttribute("errorMsg","Unable to create user. Please try later.");
					res.sendRedirect(req.getContextPath()+"/auth/");
				}
			}else{
				req.getSession().setAttribute("errorMsg","email is not in valid format");
				res.sendRedirect(req.getContextPath()+"/auth/");
			}
		} else {
			req.getSession().setAttribute("errorMsg","some required information is missing!!!");
			res.sendRedirect(req.getContextPath()+"/auth/");
		}
	}
}
