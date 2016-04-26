package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BedDAO;
import dao.BedDAOImpl;
import dao.FacilityDAO;
import dao.FacilityDAOImpl;
import dao.RoomBedDAO;
import dao.RoomBedDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import dao.RoomFacilityDAO;
import dao.RoomFacilityDAOImpl;
import model.Hotel;
import model.HotelFacility;
import model.Room;
import model.RoomBed;
import model.RoomFacility;
import service.ManageRoomService;

public class ManageRoomController extends HttpServlet {
	RoomDAO roomDAO = new RoomDAOImpl();
	FacilityDAO facilityDAO = new FacilityDAOImpl();
	BedDAO bedDAO = new BedDAOImpl();
	RoomFacilityDAO roomFacilityDAO = new RoomFacilityDAOImpl();
	RoomBedDAO roomBedDAO = new RoomBedDAOImpl();
	ManageRoomService manageRoomServ = new ManageRoomService();
	
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
		
//		if(operation == null){
//			this.showRoomList(req, res);
//		} else 
		if(operation.equals("/edit")) {
			this.editRoom(req, res);
		} else if(operation.equals("/add")){
			this.addRoom(req, res);
		} else if(operation.equals("/remove")){
			this.removeRoom(req, res);
		} else {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
//	private void showRoomList(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setAttribute("rooms", roomDAO.getAllRooms());
//		req.getRequestDispatcher("/jsp/manage-room/index.jsp").forward(req, res);
//	}
	
	private void addRoom(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int hotelId = Integer.parseInt(req.getParameter("hotel_id"));
		req.setAttribute("hotelId", hotelId);

		if(req.getParameter("add")==null){
			req.getRequestDispatcher("/jsp/manage-room/add.jsp").forward(req, res);
		} else {
			String type = req.getParameter("type"),
			roomNo = req.getParameter("roomNo"),
			discount = req.getParameter("discount"),
			recommended = req.getParameter("recommended");
			
			int price = Integer.parseInt(req.getParameter("price")),
				capacity = Integer.parseInt(req.getParameter("capacity")),
				size = Integer.parseInt(req.getParameter("size"));

			if(type!=null && roomNo!=null && discount!=null
						&& recommended!=null){
				Room room = new Room();
				room.setType(type);
				room.setPrice(price);
				room.setHotelId(hotelId);
				room.setCapacity(capacity);
				room.setSize(size);
				room.setRoomNo(roomNo);
				
				if(discount.equals(""))
					room.setDiscount(null);
				else
					room.setDiscount(Integer.parseInt(discount));
				
				if(recommended.equals(""))	
					room.setRecommended(null);
				else
					room.setRecommended(Integer.parseInt(recommended));
				
				if(manageRoomServ.getRoom(roomNo, hotelId)!=null){
					//add error message
					this.goAddPage(req, res, "This room is already registered.", room, hotelId);
				} else {
					if(!manageRoomServ.createRoom(room)){
						//add error message
						this.goAddPage(req, res, "Cannot create room at this time. Please try again later.", room, hotelId);
					} else {
						this.goHome(req, res, hotelId);
					}
				}
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "There is missing information in add room request.");
			}
		}
	}
	
	private void editRoom(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		int roomId = Integer.parseInt((String) req.getParameter("room_id"));
		Room room = manageRoomServ.getRoom(roomId);
		req.setAttribute("facilities", facilityDAO.getAllRoomFacilities());
		req.setAttribute("beds", bedDAO.getAllBeds());
		req.setAttribute("roomFacilities", roomFacilityDAO.getRoomFacilities(room));
		req.setAttribute("roomBeds", roomBedDAO.getRoomBeds(room));

		if(req.getParameter("edit")==null){
			if(room!=null){
				req.setAttribute("room", room);
				req.getRequestDispatcher("/jsp/manage-room/edit.jsp").forward(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This room doesn't exist.");
			}
		}else{
			if(room!=null){
				Hotel hotel = manageRoomServ.getHotel(room.getHotelId());
				int hotelId = hotel.getHotelId();
				req.setAttribute("hotelId", hotelId);
				String type = req.getParameter("type"),
				roomNo = req.getParameter("roomNo"),
				discount = req.getParameter("discount");
				
				int price = Integer.parseInt(req.getParameter("price")),
					capacity = Integer.parseInt(req.getParameter("capacity")),
					size = Integer.parseInt(req.getParameter("size"));
				
				String[] facilities = req.getParameterValues("facilities");
				
				String[] beds = req.getParameterValues("bedType");
				
				if(type!=null && roomNo!=null && discount!=null){
					String originalRoomNo = room.getRoomNo();
					
					room.setType(type);
					room.setPrice(price);
					room.setHotelId(hotelId);
					room.setCapacity(capacity);
					room.setSize(size);
					room.setRoomNo(roomNo);
					
					if(discount.equals(""))
						room.setDiscount(null);
					else
						room.setDiscount(Integer.parseInt(discount));
					
					ArrayList<RoomFacility> roomFacilities = (ArrayList<RoomFacility>) manageRoomServ.getRoomFacilities(room);
					
					ArrayList<RoomBed> roomBeds = (ArrayList<RoomBed>) manageRoomServ.getRoomBeds(room);
					
					if(facilities != null){
						for(String facility : facilities){
							if(manageRoomServ.getRoomFacility(room.getRoomId(), Integer.parseInt(facility)) == null)
								manageRoomServ.addFacility(room.getRoomId(), Integer.parseInt(facility));
						}
						
						//	Remove the facilities deselected
						for(RoomFacility roomFacility : roomFacilities){
							boolean found = false;
							for(String facility : facilities){
								if(roomFacility.getFacility() == Integer.parseInt(facility))
									found = true;
							}
							if(!found){
								manageRoomServ.deleteFacility(roomFacility);
							}
						}
					}
					else{	// The room has no facilities or remove all facilities	
						for(RoomFacility roomFacility : roomFacilities){
							manageRoomServ.deleteFacility(roomFacility);
						}
					}
					
					if(beds != null){
						for(String bed : beds){
							System.out.println(bed);
							if(manageRoomServ.getRoomBed(Integer.parseInt(bed)) == null)
								manageRoomServ.addRoomBed(room.getRoomId(), Integer.parseInt(bed));
						}
						
						//	Remove the beds deleted
						for(RoomBed roomBed : roomBeds){
							boolean found = false;
							for(String bed : beds){
								if(roomBed.getBed() == Integer.parseInt(bed))
									found = true;
							}
							if(!found){
								manageRoomServ.deleteRoomBed(roomBed);
							}
						}
					}
					else{	// The room has no beds or remove all beds	
						for(RoomBed roomBed : roomBeds){
							manageRoomServ.deleteRoomBed(roomBed);
						}
					}
					
					if(!originalRoomNo.equals(roomNo) && manageRoomServ.getRoom(roomNo, hotelId)!=null){
						//add error message
						this.goEditPage(req, res, "Room with this room number already registered.", room);
					} else {
						if(!manageRoomServ.updateRoom(room)){
							//add error message
							this.goEditPage(req, res, "Cannot update room at this time. Please try again later.", room);
						} else {
							this.goHome(req, res, hotelId);
						}
					}
				} 
			}else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This room doesn't exist.");
			}
		}
	}
	
	private void removeRoom(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		int roomId = Integer.parseInt((String) req.getParameter("room_id")); 
		int hotelId = Integer.parseInt(req.getParameter("hotel_id"));
		
		if(req.getParameter("confirm")==null){
			Room room = manageRoomServ.getRoom(roomId);
			
			if(room!=null){
				req.setAttribute("room", room);
				req.setAttribute("hotelId", hotelId);
				req.getRequestDispatcher("/jsp/manage-room/confirm.jsp").forward(req, res);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This room doesn't exist.");
			}
		} else {
			Room room = manageRoomServ.getRoom(roomId);
			if(room!=null){
				manageRoomServ.deleteRoom(room);
				this.goHome(req, res, hotelId);
			} else {
				res.sendError(HttpServletResponse.SC_BAD_REQUEST, "This room doesn't exist.");
			}
		}
	}
	
	private void goHome(HttpServletRequest req, HttpServletResponse res, int hotelId) throws ServletException, IOException {
		res.sendRedirect(req.getContextPath()+"/manage-hotel/edit?hotel_id="+hotelId);
	}
	
	private void goAddPage(HttpServletRequest req, HttpServletResponse res, String errorMsg, Room room, int hotelId) throws ServletException, IOException {
		req.getSession().setAttribute("errorMsg", errorMsg);
		req.getSession().setAttribute("inputBefore", room);
		res.sendRedirect(req.getContextPath()+"/manage-room/add?hotel_id="+hotelId);
	}
	
	private void goEditPage(HttpServletRequest req, HttpServletResponse res, String errorMsg, Room room) throws ServletException, IOException {
		req.getSession().setAttribute("errorMsg", errorMsg);
		req.setAttribute("room", room);
		res.sendRedirect(req.getContextPath()+"/manage-room/edit?room_id="+room.getRoomId());
	}
}