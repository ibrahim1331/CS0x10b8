package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FacilityDAO;
import dao.FacilityDAOImpl;
import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.HotelFacilityDAO;
import dao.HotelFacilityDAOImpl;
import dao.LocationDAO;
import dao.LocationDAOImpl;
import model.Hotel;
import model.HotelFacility;
import service.ManageHotelService;
import service.ValidationService;

/**
 * responsible for routing under context /manage-hotel
 * @author Mohammed Ibrahim
 *
 */
public class ManageHotelController extends HttpServlet {
	HotelDAO hotelDAO = new HotelDAOImpl();
	LocationDAO locationDAO = new LocationDAOImpl();
	FacilityDAO facilitiesDAO = new FacilityDAOImpl();
	HotelFacilityDAO hotelFacilityDAO = new HotelFacilityDAOImpl();
	ManageHotelService manageHotelServ = new ManageHotelService();
	ValidationService validationServ = new ValidationService();
	
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
		 * "/add" : add new hotel page
		 * "/remove": remove confirmation page
		 * "/remove?confirm=1": do remove action
		 */
		String operation = req.getPathInfo();

		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "processRequest, operation="+operation);
		
		if(operation == null){
			this.showHotelList(req, res);
		} else if(operation.equals("/edit")) {
			this.editHotel(req, res);
		} else if(operation.equals("/add")){
			this.addHotel(req, res);
		} else if(operation.equals("/remove")){
			this.removeHotel(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	private void showHotelList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("hotels", hotelDAO.getAllHotels());
		req.getRequestDispatcher("/jsp/manage-hotel/index.jsp").forward(req, res);
	}
	
	private void addHotel(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("locations", locationDAO.getAllLocations());
		if(req.getParameter("add")==null){
			req.getRequestDispatcher("/jsp/manage-hotel/add.jsp").forward(req, res);
		} else {
			String name = req.getParameter("name"),
			address = req.getParameter("address"),
			description = req.getParameter("description");
			
			int location = Integer.parseInt(req.getParameter("location")),
				noOfRooms = Integer.parseInt(req.getParameter("noOfRooms"));

			if(name!=null && address!=null && description!=null){
				Hotel hotel = new Hotel();
				hotel.setName(name);
				hotel.setAddress(address);
				hotel.setLocation(location);
				hotel.setNoOfRooms(noOfRooms);
				hotel.setDescription(validationServ.removeScripts(description));
				hotel.setDateJoined(new Timestamp(new Date().getTime()));
				hotel.setRating((float) 0);
				
				if(manageHotelServ.getHotel(name)!=null){
					//add error message
					this.goAddPage(req, res, "This hotel is already registered.", hotel);
				} else {
					if(!manageHotelServ.createHotel(hotel)){
						//add error message
						this.goAddPage(req, res, "Cannot create hotel at this time. Please try again later.", hotel);
					} else {
						this.goHome(req, res);
					}
				}
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is missing information in add hotel request.");
			}
		}
	}
	
	private void editHotel(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		int hotelId = Integer.parseInt((String) req.getParameter("hotel_id")); 
		Hotel hotel = manageHotelServ.getHotel(hotelId);
		req.setAttribute("locations", locationDAO.getAllLocations());
		req.setAttribute("facilities", facilitiesDAO.getAllHotelFacilities());
		req.setAttribute("hotelFacilities", hotelFacilityDAO.getHotelFacilities(hotel));

		if(req.getParameter("edit")==null){
			if(hotel!=null){
				req.setAttribute("rooms", manageHotelServ.getRooms(hotel));
				req.setAttribute("hotel", hotel);
				req.getRequestDispatcher("/jsp/manage-hotel/edit.jsp").forward(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This hotel doesn't exist.");
			}
		}else{
			if(hotel!=null){
				String name = req.getParameter("name"),
				address = req.getParameter("address"),
				description = req.getParameter("description");
				
				int location = Integer.parseInt(req.getParameter("location")),
					noOfRooms = Integer.parseInt(req.getParameter("noOfRooms"));
				
				String[] facilities = req.getParameterValues("facilities");

				if(name!=null && address!=null && description!=null){
					hotel.setName(name);
					hotel.setAddress(address);
					hotel.setLocation(location);
					hotel.setNoOfRooms(noOfRooms);
					hotel.setDescription(validationServ.removeScripts(description));
					
					ArrayList<HotelFacility> hotelFacilities = (ArrayList<HotelFacility>) manageHotelServ.getHotelFacilities(hotel);
					
					if(facilities != null){
						for(String facility : facilities){
							if(manageHotelServ.getHotelFacility(hotel.getHotelId(), Integer.parseInt(facility)) == null)
								manageHotelServ.addFacility(hotel.getHotelId(), Integer.parseInt(facility));
						}
						
						//	Remove the facilities deselected
						for(HotelFacility hotelFacility : hotelFacilities){
							boolean found = false;
							for(String facility : facilities){
								if(hotelFacility.getFacility() == Integer.parseInt(facility))
									found = true;
							}
							if(!found){
								manageHotelServ.deleteFacility(hotelFacility);
							}
						}
					}
					else{	// The hotel has no facilities or remove all facilities	
						for(HotelFacility hotelFacility : hotelFacilities){
							manageHotelServ.deleteFacility(hotelFacility);
						}
					}
					
					if(!manageHotelServ.updateHotel(hotel)){
						//add error message
						this.goEditPage(req, res, "Cannot update hotel at this time. Please try again later.", hotel);
					} else {
						this.goHome(req, res);
					}
				} else {
					res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This hotel doesn't exist.");
				}
			}
		}
	}
	
	private void removeHotel(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int hotelId = Integer.parseInt((String) req.getParameter("hotel_id")); 
		
		if(req.getParameter("confirm")==null){
			Hotel hotel = manageHotelServ.getHotel(hotelId);
			
			if(hotel!=null){
				req.setAttribute("hotel", hotel);
				req.getRequestDispatcher("/jsp/manage-hotel/confirm.jsp").forward(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This hotel doesn't exist.");
			}
		} else {
			Hotel hotel = manageHotelServ.getHotel(hotelId);
			if(hotel!=null){
				manageHotelServ.deleteHotel(hotel);
				this.goHome(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This hotel doesn't exist.");
			}
		}
	}
	
	private void goHome(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.sendRedirect(req.getContextPath()+"/manage-hotel");
	}
	
	private void goAddPage(HttpServletRequest req, HttpServletResponse res, String errorMsg, Hotel hotel) throws ServletException, IOException {
		req.getSession().setAttribute("errorMsg", errorMsg);
		req.getSession().setAttribute("inputBefore", hotel);
		res.sendRedirect(req.getContextPath()+"/manage-hotel/add");
	}
	
	private void goEditPage(HttpServletRequest req, HttpServletResponse res, String errorMsg, Hotel hotel) throws ServletException, IOException {
		req.setAttribute("errorMsg", errorMsg);
		req.setAttribute("hotel", hotel);
		res.sendRedirect(req.getContextPath()+"/manage-hotel/edit?hotel_id="+hotel.getHotelId());
	}
}
