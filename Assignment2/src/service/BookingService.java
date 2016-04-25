package service;

import java.util.ArrayList;
import java.util.List;

import dao.BookingDAO;
import dao.BookingDAOImpl;
import dao.BookingViewDAO;
import dao.BookingViewDAOImpl;
import dao.SearchDAO;
import dao.SearchDAOImpl;
import model.Booking;
import model.BookingMeta;
import model.BookingView;
import model.Search;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Equal;
import sqlwhere.operators.compare.GreaterThan;
import sqlwhere.operators.compare.LessThan;
import utils.AppHelper;
import utils.Columns;

public class BookingService {
	SearchDAO searchDAO = new SearchDAOImpl();
	BookingDAO bookingDAO = new BookingDAOImpl();
	BookingViewDAO bookingViewDAO = new BookingViewDAOImpl();
	
	public Search getSearch(int roomId){
		Where where = new Where(new Equal(Columns.View.SearchView.ROOM_ID, roomId));
		List<Search> results = searchDAO.search(where); 
		return results.isEmpty()?null:results.get(0);
	}
	
	public List<BookingView> getBookingViews(int customerId){
		Where where = new Where(new Equal(Columns.View.BookingView.CUSTOMER_ID, customerId));
		return bookingViewDAO.getBookings(where);
	}
	
	public List<BookingView> getBookingViewByBookingNumber(int bookingNumber){
		Where where = new Where(new Equal(Columns.View.BookingView.BOOKING_NUMBER, bookingNumber));
		List<BookingView> bookingViews = bookingViewDAO.getBookings(where);
		return bookingViews;
	}
	
	public BookingView getBookingViewById(int bookingId){
		Where where = new Where(new Equal(Columns.View.BookingView.BOOKING_ID, bookingId));
		List<BookingView> bookingViews = bookingViewDAO.getBookings(where);
		return bookingViews.get(0);
	}
	
	public List<BookingView> getBookingViewByPin(String pin){
		Where where = new Where(new Equal(Columns.View.BookingView.PIN, pin));
		List<BookingView> bookingViews = bookingViewDAO.getBookings(where); 
		return bookingViews;
	}
	
	public BookingMeta createBookings(List<Booking> bookings){
		int bookingNumber = this.getBookingNumber();
		String pin = AppHelper.generatePin();
		
		List<Booking> failed = new ArrayList<>();
		
		for(Booking b: bookings){
			Search roomView = this.getSearch(b.getRoomId());
			int price = b.getCustomerId()!=null ? roomView.getRoomPrice() * roomView.getDiscount() / 100 : roomView.getRoomPrice() ;
			b.setPrice(price);
			b.setBookingNumber(bookingNumber);
			b.setPin(pin);
			if(!this.validate(b) || !bookingDAO.createBooking(b)){
				failed.add(b);
			}
		}
		
		BookingMeta meta = new BookingMeta();
		meta.setFailed(failed);
		meta.setPin(pin);
		meta.setAllFailed(failed.size()==bookings.size());
		
		return meta;
	}
	
	public Booking getBookingById(int bookingId){
		return bookingDAO.getBookingById(bookingId);
	}
	
	public String createBooking(Booking booking){
		String pin = AppHelper.generatePin();
		booking.setBookingNumber(this.getBookingNumber());
		booking.setPin(pin);
		if(this.validate(booking) && bookingDAO.createBooking(booking)){
			return pin;
		}
		return null;
	}
	
	public boolean updateBooking(Booking booking){
		if(this.validate(booking) && bookingDAO.updateBooking(booking)){
			return true;
		}
		return false;
	}
	
	public boolean cancelBooking(Booking booking){
		booking.setCancelled(true);
		if(bookingDAO.updateBooking(booking)){
			return true;
		}
		return false;
	}
	
	private int getBookingNumber(){
		return (int) AppHelper.getCurrentTimestamp().getTime();
	}
	
	private boolean validate(Booking booking){
		//check whether the room has any bookings within the datetime range
		// overlapping = startA < endB && endA > startB
		//if there is any -> false, this room cannot be reserved
		Where where = new Where(new Equal(Columns.Table.Booking.ROOM_ID, booking.getRoomId()))
				.and(new Equal(Columns.Table.Booking.IS_CANCELLED, false))
				.and(new LessThan(Columns.Table.Booking.CHECK_IN_DATE, booking.getCheckOutDate()))
				.and(new GreaterThan(Columns.Table.Booking.CHECK_OUT_DATE, booking.getCheckInDate()));
		
		return bookingDAO.getBookings(where).isEmpty();
	}
}
