package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

/**
 * responsible for routing under context /manage-user
 * @author lausinleung
 *
 */
public class UserManagerController extends HttpServlet {
	UserService userService = new UserService();
	
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
		/*
		 * "/" : main page. show list of users
		 * 
		 */
		String operation = req.getPathInfo();
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		
		if(operation == null){
			this.showUserList(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void showUserList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
	}
}
