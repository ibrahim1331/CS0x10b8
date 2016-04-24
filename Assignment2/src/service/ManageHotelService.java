package service;

import java.util.List;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import dao.SearchDAO;
import dao.SearchDAOImpl;
import model.Hotel;
import model.Room;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Equal;
import sqlwhere.operators.compare.Null;
import sqlwhere.operators.logical.Not;
import sqlwhere.operators.logical.Or;
import utils.Columns;

public class ManageHotelService {
	SearchDAO searchDAO = new SearchDAOImpl();
	RoomDAO roomDAO = new RoomDAOImpl();
	HotelDAO hotelDAO = new HotelDAOImpl();
	
	public Hotel getHotel(int hotelId){
		return hotelDAO.getHotelById(hotelId);
	}
	
	public Hotel getHotel(String hotelName){
		return hotelDAO.getHotelByName(hotelName);
	}
	
	public List<Hotel> getHotels(){
		return hotelDAO.getAllHotels();
	}
	
	public boolean createHotel(Hotel hotel){
		return hotelDAO.createHotel(hotel);
	}
	
	public boolean updateHotel(Hotel hotel){
		return hotelDAO.updateHotel(hotel);
	}
	
	public boolean deleteHotel(Hotel hotel){
		return hotelDAO.deleteHotel(hotel);
	}
	
	public List<Room> getRooms(Hotel hotel){
		Where where = new Where(new Not(new Equal("type", "Bedroom"))).and(new Equal("hotel_id", hotel.getHotelId()));
		return roomDAO.getRooms(where);
	}
	
	public List<Room> getBedrooms(Room room){
		Where where = new Where(new Equal("type", "Bedroom")).and(new Equal("belongs_to", room.getRoomId()));
		return roomDAO.getRooms(where);
	}
}
