package dao;

import java.util.List;

import model.Hotel;
import model.HotelFacility;
import sqlwhere.core.Where;


public interface HotelFacilityDAO {
	List<HotelFacility> getAllHotelFacilities();
	
	List<HotelFacility> getHotelFacilities(Hotel hotel);
	
	List<HotelFacility> getHotelFacilities(Where where);
	
	HotelFacility getHotelFacility(Where where);
	
	boolean createHotelFacility(HotelFacility hotelFacility);
	
	boolean deleteHotelFacility(HotelFacility hotelFacility);
}
