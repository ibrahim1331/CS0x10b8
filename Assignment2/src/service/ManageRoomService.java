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
import model.Search;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Equal;
import sqlwhere.operators.compare.GreaterThan;
import sqlwhere.operators.compare.NotNull;
import sqlwhere.operators.compare.Null;
import sqlwhere.operators.logical.And;
import sqlwhere.operators.logical.Not;
import sqlwhere.operators.logical.Or;
import utils.Columns;

public class ManageRoomService {
	SearchDAO searchDAO = new SearchDAOImpl();
	RoomDAO roomDAO = new RoomDAOImpl();
	HotelDAO hotelDAO = new HotelDAOImpl();
	
	public Hotel getHotel(int hotelId){
		return hotelDAO.getHotelById(hotelId);
	}
	
	public List<Hotel> getHotels(){
		return hotelDAO.getAllHotels();
	}
	
	public List<Search> getRecommendedRooms(int hotelId){
		Where where = new Where(new And(new NotNull(Columns.View.SearchView.RECOMMENDED)
										, new GreaterThan(Columns.View.SearchView.RECOMMENDED, 0)))
				.and(new Equal(Columns.View.SearchView.HOTEL_ID, hotelId))
				.and(new Null(Columns.View.SearchView.BELONGS_TO));
		return searchDAO.search(where);
	}
	
	public List<Search> getNonRecommendedRooms(int hotelId){
		Where where = new Where(new Or(new Null(Columns.View.SearchView.RECOMMENDED)
										, new Equal(Columns.View.SearchView.RECOMMENDED, 0)))
				.and(new Equal(Columns.View.SearchView.HOTEL_ID, hotelId))
				.and(new Null(Columns.View.SearchView.BELONGS_TO));
		return searchDAO.search(where);
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
}