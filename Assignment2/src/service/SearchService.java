package service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import dao.BookingViewDAO;
import dao.BookingViewDAOImpl;
import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import model.BookingView;
import model.Hotel;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.GreaterThanEqual;
import sqlwhere.operators.compare.In;
import sqlwhere.operators.compare.LessThanEqual;
import sqlwhere.operators.compare.Like;
import utils.Columns;

public class SearchService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	HotelDAO hotelDAO = new HotelDAOImpl();
	BookingDAO bookingDAO = new BookingDAOImpl();
	RoomDAO roomDAO = new RoomDAOImpl();
	BookingViewDAO bookingViewDAO = new BookingViewDAOImpl();
	
	public List<Hotel> searchHotels(String name, Timestamp fromDate, Timestamp toDate){
		if(fromDate==null && toDate==null){
			logger.log(Level.INFO, "searchHotels, name only");
			Where where = new Where(new Like(Columns.Table.Hotel.NAME, "%"+name+"%"));
			return hotelDAO.getHotels(where);
		} else {
			logger.log(Level.INFO, "searchHotels, name + date range");
			//get booking within date range first
			Where whereBooking = new Where(new Like(Columns.View.BookingView.HOTEL_NAME, "%"+name+"%"))
					.and(new GreaterThanEqual(Columns.View.BookingView.CHECK_IN_DATE, fromDate))
					.and(new LessThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, toDate));
			
			List<BookingView> bookingViews = bookingViewDAO.getBookings(whereBooking);
			
			Integer[] hotelIds = (Integer[]) bookingViews.stream().map(bv->bv.getHotelId()).distinct().collect(Collectors.toList()).toArray();
			
			Where whereHotel = new Where(new In(Columns.Table.Hotel.HOTEL_ID, hotelIds));
			
			return hotelDAO.getHotels(whereHotel);
		}
	}
}
