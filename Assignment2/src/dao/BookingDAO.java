package dao;

import java.util.List;

import model.Booking;
import sqlwhere.core.Where;

public interface BookingDAO {
	Booking getBookingById(Integer id);
	
	List<Booking> getBookings(Where where);
	
	boolean createBooking(Booking booking);
	
	boolean updateBooking(Booking booking);
	
	boolean deleteBooking(Booking booking);
}