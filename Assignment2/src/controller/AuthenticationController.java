package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CustomerService;

public class AuthenticationController extends HttpServlet{
	CustomerService customerService = new CustomerService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doProcess(req, res);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doProcess(req, res);
	}
	
	private void doProcess(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String operation = req.getPathInfo();
		if(operation.equals("login")){
			login(req,res);
		} else if(operation.equals("logout")){
			logout(req,res);
		} else if(operation.equals("register")){
			
		}
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.getSession().invalidate();
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/index.jsp");
		dispatcher.forward(req, res);
	}
	
	private void login(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		
		if(customerService.getCustomer(email, password)==null){
			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			
			res.setContentType("text/html");
			res.getWriter().print("Account not found!");
		}
	}
	
	private void register(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		
	}
}
