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
import sqlwhere.operators.compare.NotNull;
import sqlwhere.operators.compare.Null;
import sqlwhere.operators.logical.Or;
import utils.Columns;

public class RecommendRoomService {
	SearchDAO searchDAO = new SearchDAOImpl();
	RoomDAO roomDAO = new RoomDAOImpl();
	HotelDAO hotelDAO = new HotelDAOImpl();
	
	public Hotel getHotel(int hotelId){
		return hotelDAO.getHotelById(hotelId);
	}
	
	public List<Hotel> getHotels(){
		return hotelDAO.getAllHotels();
	}
	
	public List<Search> getRecommendingRooms(int hotelId){
		Where where = new Where(new NotNull(Columns.View.SearchView.RECOMMENDED))
				.and(new Equal(Columns.View.SearchView.HOTEL_ID, hotelId))
				.and(new Null(Columns.View.SearchView.BELONGS_TO));
		return searchDAO.search(where);
	}
	
	public List<Search> getNonRecommendingRooms(int hotelId){
		Where where = new Where(new Or(new Null(Columns.View.SearchView.RECOMMENDED)
										, new Equal(Columns.View.SearchView.RECOMMENDED, 0)))
				.and(new Equal(Columns.View.SearchView.HOTEL_ID, hotelId))
				.and(new Null(Columns.View.SearchView.BELONGS_TO));
		return searchDAO.search(where);
	}
	
	public Room getRoom(int roomId){
		return roomDAO.getRoomById(roomId);
	}
	
	public boolean recommendRoom(Room room){
		return roomDAO.updateRoom(room);
	}
}
