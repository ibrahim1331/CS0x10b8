package service;

import java.sql.Timestamp;
import java.util.List;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import dao.HotelDAO;
import dao.HotelDAOImpl;
import model.Hotel;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Like;
import utils.Columns;

public class SearchService {
	HotelDAO hotelDAO = new HotelDAOImpl();
	BookingDAO bookingDAO = new BookingDAOImpl();
	
	public List<Hotel> searchHotels(String name, Timestamp fromDate, Timestamp toDate){
		if(fromDate==null || toDate==null){
			return this.searchHotel(name);
		} else {
			//get booking within date range first
			
			return null;
		}
	}
	
	public List<Hotel> searchHotel(String name){
		Where where = new Where(new Like(Columns.Table.Hotel.NAME, "%"+name+"%"));
		return hotelDAO.getHotels(where);
	}
}
