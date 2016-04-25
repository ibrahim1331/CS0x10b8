package service;

import java.util.List;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.HotelFacilityDAO;
import dao.HotelFacilityDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import dao.SearchDAO;
import dao.SearchDAOImpl;
import model.Facility;
import model.Hotel;
import model.HotelFacility;
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
	HotelFacilityDAO hotelFacilitiesDAO = new HotelFacilityDAOImpl();
	
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
	
	public boolean addFacility(Integer hotelId, Integer facilityId){
		HotelFacility hotelFacility = new HotelFacility();
		hotelFacility.setHotel(hotelId);
		hotelFacility.setFacility(facilityId);
		return hotelFacilitiesDAO.createHotelFacility(hotelFacility);
	}
	
	public boolean deleteFacility(HotelFacility hotelFacility){
		return hotelFacilitiesDAO.deleteHotelFacility(hotelFacility);
	}
	
	public HotelFacility getHotelFacility(Integer hotelId, Integer facilityId){
		Where where = new Where(new Equal("hotel", hotelId)).and(new Equal("facility", facilityId));
		return hotelFacilitiesDAO.getHotelFacility(where);
	}
	
	public List<HotelFacility> getHotelFacilities(Hotel hotel){
		return hotelFacilitiesDAO.getHotelFacilities(hotel);
	}
}
