package service;

import java.util.List;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.RoomBedDAO;
import dao.RoomBedDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import dao.RoomFacilityDAO;
import dao.RoomFacilityDAOImpl;
import model.Hotel;
import model.Room;
import model.RoomBed;
import model.RoomFacility;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Equal;
import sqlwhere.operators.logical.Not;

public class ManageRoomService {
	RoomDAO roomDAO = new RoomDAOImpl();
	HotelDAO hotelDAO = new HotelDAOImpl();
	RoomFacilityDAO roomFacilitiesDAO = new RoomFacilityDAOImpl();
	RoomBedDAO roomBedDAO = new RoomBedDAOImpl();
	
	public Hotel getHotel(int hotelId){
		return hotelDAO.getHotelById(hotelId);
	}
	
	public List<Hotel> getHotels(){
		return hotelDAO.getAllHotels();
	}
	
	public List<Room> getRooms(){
		Where where = new Where(new Not(new Equal("type", "Bedroom")));
		return roomDAO.getRooms(where);
	}
	
	public List<Room> getBedrooms(Room room){
		Where where = new Where(new Equal("type", "Bedroom")).and(new Equal("belongs_to", room.getRoomId()));
		return roomDAO.getRooms(where);
	}
	
	public Room getRoom(int roomId){
		return roomDAO.getRoomById(roomId);
	}
	
	public Room getRoom(String roomNo){
		return roomDAO.getRoomByNo(roomNo);
	}
	
	public Room getRoom(String roomNo, int hotelId){
		Where where = new Where(new Equal("room_no", roomNo)).and(new Equal("hotel_id", hotelId));
		return roomDAO.getRoom(where);
	}
	
	public boolean createRoom(Room room){
		return roomDAO.createRoom(room);
	}
	
	public boolean updateRoom(Room room){
		return roomDAO.updateRoom(room);
	}
	
	public boolean deleteRoom(Room room){
		return roomDAO.deleteRoom(room);
	}
	
	public boolean addFacility(Integer roomId, Integer facilityId){
		RoomFacility roomFacility = new RoomFacility();
		roomFacility.setRoom(roomId);
		roomFacility.setFacility(facilityId);
		return roomFacilitiesDAO.createRoomFacility(roomFacility);
	}
	
	public boolean deleteFacility(RoomFacility roomFacility){
		return roomFacilitiesDAO.deleteRoomFacility(roomFacility);
	}
	
	public RoomFacility getRoomFacility(Integer roomId, Integer facilityId){
		Where where = new Where(new Equal("room", roomId)).and(new Equal("facility", facilityId));
		return roomFacilitiesDAO.getRoomFacility(where);
	}
	
	public List<RoomFacility> getRoomFacilities(Room room){
		return roomFacilitiesDAO.getRoomFacilities(room);
	}
	
	public List<RoomBed> getRoomBeds(Room room){
		return roomBedDAO.getRoomBeds(room);
	}
	
	public RoomBed getRoomBed(Integer roomBedId){
		Where where = new Where(new Equal("id", roomBedId));
		return roomBedDAO.getRoomBed(where);
	}
	
	public boolean addRoomBed(Integer roomId, Integer bedId){
		RoomBed roomBed = new RoomBed();
		roomBed.setBed(bedId);
		roomBed.setRoom(roomId);
		return roomBedDAO.createRoomBed(roomBed);
	}
	
	public boolean deleteRoomBed(RoomBed roomBed){
		return roomBedDAO.deleteRoomBed(roomBed);
	}
}