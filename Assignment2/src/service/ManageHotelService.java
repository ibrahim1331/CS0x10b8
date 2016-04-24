package service;

import java.util.List;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import dao.SearchDAO;
import dao.SearchDAOImpl;
import model.Hotel;

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
}
