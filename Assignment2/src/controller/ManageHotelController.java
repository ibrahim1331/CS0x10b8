package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import enums.Role;

/**
 * responsible for routing under context /manage-hotel
 * @author Mohammed Ibrahim
 *
 */
public class ManageHotelController extends HttpServlet {
	HotelDAO hotelDAO = new HotelDAOImpl();
	
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
		 * "/" : main page. show list of hotels
		 * "/add" : add new manage page
		 * "/remove": remove confirmation page
		 * "/remove?confirm=1": do remove action
		 */
		String operation = req.getPathInfo();
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		
		if(operation == null){
			this.showHotelList(req, res);
		} else if(operation.equals("/add")){
			this.addHotelManager(req, res);
		} else if(operation.equals("/remove")){
			this.removeHotelManager(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void showHotelList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("hotels", hotelDAO.getAllHotels());
		req.getRequestDispatcher("/jsp/manage-hotel/index.jsp").forward(req, res);
	}
	
	private void addHotelManager(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if(req.getParameter("add")==null){
			req.getRequestDispatcher("/jsp/manage-hotel/add.jsp").forward(req, res);
		} else {
			String email = req.getParameter("email"),
					firstName = req.getParameter("firstName"),
					lastName = req.getParameter("lastName"),
					gender = req.getParameter("gender"),
					password = req.getParameter("password"),
					title = req.getParameter("title");
			
			if(email!=null && firstName!=null && lastName!=null && gender!=null && password!=null && title!=null){
				User manager = new User();
				manager.setEmail(req.getParameter("email"));
				manager.setFirstName(req.getParameter("firstName"));
				manager.setLastName(req.getParameter("lastName"));
				manager.setGender(req.getParameter("gender"));
				manager.setPassword(req.getParameter("password"));
				manager.setTitle(req.getParameter("title"));
				manager.setRegister(true);
				manager.setRole(Role.Manager.getValue());
				
				if(userService.getUser(email)!=null){
					//add error message
					this.goAddPage(req, res, "This email is already registered.", manager);
				} else {
					if(!userService.createUser(manager)){
						//add error message
						this.goAddPage(req, res, "Cannot create hotel manager at this time. Please try again later.", manager);
					} else {
						this.goHome(req, res);
					}
				}
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is missing information in adding hotel manager.");
			}
		}
	}
	
	private void removeHotelManager(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int userId = Integer.parseInt((String) req.getParameter("id")); 
		
		if(req.getParameter("confirm")==null){
			User manager = userService.getUserById(userId);
			if(manager!=null && manager.getRole().equals(Role.Manager)){
				req.setAttribute("manager", manager);
				req.getRequestDispatcher("/jsp/manage-user/confirm.jsp").forward(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This is not a hotel manager.");
			}
		} else {
			User manager = userService.getUserById(userId);
			if(manager!=null && manager.getRole().equals(Role.Manager)){
				userService.deleteUser(manager);
				this.goHome(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This is not a hotel manager.");
			}
		}
	}
	
	private void goHome(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendRedirect(req.getContextPath()+"/manage-user");
	}
	
	private void goAddPage(HttpServletRequest req, HttpServletResponse res, String errorMsg, User manager) throws ServletException, IOException {
		req.getSession().setAttribute("errorMsg", errorMsg);
		req.getSession().setAttribute("inputBefore", manager);
		res.sendRedirect(req.getContextPath()+"/manage-user/add");
	}
}
