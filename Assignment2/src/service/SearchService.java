package service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import dao.BookingViewDAO;
import dao.BookingViewDAOImpl;
import dao.HotelDAO;
import dao.HotelDAOImpl;
import dao.RoomDAO;
import dao.RoomDAOImpl;
import dao.SearchDAO;
import dao.SearchDAOImpl;
import model.BookingView;
import model.Hotel;
import model.Room;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Equal;
import sqlwhere.operators.compare.GreaterThan;
import sqlwhere.operators.compare.GreaterThanEqual;
import sqlwhere.operators.compare.LessThanEqual;
import sqlwhere.operators.compare.Like;
import sqlwhere.operators.compare.NotIn;
import sqlwhere.operators.compare.Null;
import utils.AppHelper;
import utils.Columns;

public class SearchService {
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	HotelDAO hotelDAO = new HotelDAOImpl();
	BookingDAO bookingDAO = new BookingDAOImpl();
	RoomDAO roomDAO = new RoomDAOImpl();
	SearchDAO searchDAO = new SearchDAOImpl();
	BookingViewDAO bookingViewDAO = new BookingViewDAOImpl();
	
	public Hotel getHotels(int id){
		return hotelDAO.getHotelById(id);
	}
	
	public List<Hotel> searchHotels(String name, Timestamp fromDate, Timestamp toDate){
		if(fromDate==null && toDate==null){
			logger.log(Level.INFO, "searchHotels, name only");
			Where where = new Where(new Like(Columns.Table.Hotel.NAME, "%"+name+"%"));
			return hotelDAO.getHotels(where);
		} else {
			logger.log(Level.INFO, "searchHotels, name + date range");
			//if there is any room within the date range not booked by anyone, then the hotel is in the list
			//fromDate is null, assume from today
			//toDate is null, assume to infinity
			Where whereBooking = new Where(new Like(Columns.View.BookingView.HOTEL_NAME, "%"+name+"%"));
		
			if(fromDate==null && toDate==null){
				//exclude rooms that are booked within the coming week
				whereBooking.and(new GreaterThanEqual(Columns.View.BookingView.CHECK_IN_DATE, AppHelper.getCurrentTimestamp()))
					 .and(new LessThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, AppHelper.getTimestampAfterDays(7)));
			} else if(fromDate!=null && toDate!=null){
				//startA <= endB and endA >= startB
				whereBooking.and(new GreaterThanEqual(Columns.View.BookingView.CHECK_IN_DATE, toDate))
					 .and(new LessThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, fromDate));
			}
			
			List<BookingView> bookingViews = bookingViewDAO.getBookings(whereBooking);
			
			List<Integer> hotelIds = bookingViews.stream().map(bv->bv.getHotelId()).distinct().collect(Collectors.toList());
			
			Where whereHotel = new Where(new Like(Columns.Table.Hotel.NAME, "%"+name+"%"));
			
			if(!hotelIds.isEmpty()) whereHotel.and(new NotIn(Columns.Table.Hotel.HOTEL_ID, hotelIds));
			
			return hotelDAO.getHotels(whereHotel);
		}
	}
	
	public List<Room> getRecommendingRooms(int hotelId, Timestamp fromDate, Timestamp toDate){
		Where where = new Where(new Equal(Columns.View.BookingView.HOTEL_ID, hotelId))
				.and(new Equal(Columns.View.BookingView.IS_CANCELLED, false));
		if(fromDate==null && toDate==null){
			//exclude rooms that are booked within the coming week
			where.and(new LessThanEqual(Columns.View.BookingView.CHECK_IN_DATE, AppHelper.getCurrentTimestamp()))
				 .and(new GreaterThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, AppHelper.getTimestampAfterDays(7)));
		} else if(fromDate!=null && toDate!=null){
			//startA <= endB and endA >= startB
			where.and(new LessThanEqual(Columns.View.BookingView.CHECK_IN_DATE, toDate))
				 .and(new GreaterThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, fromDate));
		}
		
		List<BookingView> bookingView = bookingViewDAO.getBookings(where);
		
		List<Integer> roomIds = bookingView.stream().map(bv->bv.getRoomId()).distinct().collect(Collectors.toList());
		
		Where whereRoom = new Where(new Equal(Columns.Table.Room.HOTEL_ID, hotelId))
				.and(new GreaterThan(Columns.Table.Room.RECOMMENDED, 0));
		if(!roomIds.isEmpty()){
			whereRoom.and(new NotIn(Columns.Table.Room.ROOM_ID, roomIds));
		}
		
		Select.OrderBy orderBy = new Select.OrderBy(Columns.Table.Room.RECOMMENDED, false);
		List<Select.OrderBy> orderBys = new ArrayList<>();
		orderBys.add(orderBy);
		
		return roomDAO.getRooms(whereRoom, orderBys);
	}
	
	public List<Room> getNonRecommendingRooms(int hotelId, Timestamp fromDate, Timestamp toDate){
		Where where = new Where(new Equal(Columns.View.BookingView.HOTEL_ID, hotelId))
				.and(new Equal(Columns.View.BookingView.IS_CANCELLED, false));
		if(fromDate==null && toDate==null){
			//exclude rooms that are booked within the coming week
			where.and(new LessThanEqual(Columns.View.BookingView.CHECK_IN_DATE, AppHelper.getCurrentTimestamp()))
				 .and(new GreaterThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, AppHelper.getTimestampAfterDays(7)));
		} else if(fromDate!=null && toDate!=null){
			//startA <= endB and endA >= startB
			where.and(new LessThanEqual(Columns.View.BookingView.CHECK_IN_DATE, toDate))
				 .and(new GreaterThanEqual(Columns.View.BookingView.CHECK_OUT_DATE, fromDate));
		}
		
		List<BookingView> bookingView = bookingViewDAO.getBookings(where);
		
		List<Integer> roomIds = bookingView.stream().map(bv->bv.getRoomId()).distinct().collect(Collectors.toList());
		
		Where whereRoom = new Where(new Null(Columns.Table.Room.RECOMMENDED))
				.or(new Equal(Columns.Table.Room.RECOMMENDED, 0))
				.and(new Equal(Columns.Table.Room.HOTEL_ID, hotelId));
		if(!roomIds.isEmpty()){
			whereRoom.and(new NotIn(Columns.Table.Room.ROOM_ID, roomIds));
		}
		
		return roomDAO.getRooms(whereRoom);
	}
}
