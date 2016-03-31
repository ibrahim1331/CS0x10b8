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
import model.User;

public class UserController extends HttpServlet{
	UserDAO userDAO = new UserDAOImpl();

	/**
	 * 
	 */
	private static final long serialVersionUID = -6463179069092857254L;
	
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
	
	protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String operation = req.getPathInfo();
		
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		
		if(operation==null){
			req.getRequestDispatcher("/jsp/user.jsp").forward(req, res);
		} else if(operation.equals("/update/account")){
			this.updateAccount(req, res);
		} else if(operation.equals("/update/personal")){
			this.updatePersonal(req, res);
		}
	}

	private void updatePersonal(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		User user = ((User) req.getSession().getAttribute("loginUser"));
		
		user.setFirstName(req.getParameter("firstname"));
		user.setGender(req.getParameter("gender"));
		user.setLastName(req.getParameter("lastname"));
		user.setTitle(req.getParameter("title"));
		
		if(userDAO.updateUser(user)){
			this.goHome(req, res);
		} else {
			this.goHome(req, res, "Your personal information cannot be updated at this time. Please try again later.");
		}
	}

	private void updateAccount(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		User user = (User) req.getSession().getAttribute("loginUser");
		
		String oldPassword = req.getParameter("oldPassword");
		String newPassword = req.getParameter("newPassword");
		
		if(!user.getPassword().equals(oldPassword)){
			this.goHome(req, res, "Your old password does not match the currently used one!");
		} else {
			user.setPassword(newPassword);
			if(userDAO.updateUser(user)){
				this.goHome(req, res);
			} else {
				this.goHome(req, res, "Cannot change your password right now. Please try again later.");
			}
		}
		
	}
	
	private void goHome(HttpServletRequest req, HttpServletResponse res, String errorMsg) throws ServletException, IOException{
		if(errorMsg==null){
			req.getSession().setAttribute("success", true);
		} else{
			req.getSession().setAttribute("failure", true);
			req.getSession().setAttribute("errorMsg", errorMsg);
		}
		res.sendRedirect(req.getContextPath()+"/user");
	}
	
	private void goHome(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		this.goHome(req, res, null);
	}
}
