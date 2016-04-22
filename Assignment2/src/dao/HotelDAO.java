package dao;

import java.util.List;

import model.Hotel;
import sqlwhere.core.Where;

public interface HotelDAO {
	
	List<Hotel> getHotels(Where where);
	
	List<Hotel> getAllHotels();
	
	Hotel getHotelById(int id);
	
	Hotel getHotelByName(String name);
	
	boolean createHotel(Hotel hotel);
	
	boolean updateHotel(Hotel hotel);
	
	boolean deleteHotel(Hotel hotel);
}
