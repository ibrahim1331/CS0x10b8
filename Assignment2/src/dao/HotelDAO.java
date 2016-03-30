package dao;

import java.util.List;

import model.Hotel;

public interface HotelDAO {
	
	List<Hotel> getAllHotels();
	
	Hotel getHotelById(int id);
	
	boolean createHotel(Hotel hotel);
	
	boolean updateHotel(Hotel hotel);
	
	boolean deleteHotel(Hotel hotel);
}
