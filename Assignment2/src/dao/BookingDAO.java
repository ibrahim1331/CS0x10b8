package dao;

import java.util.List;

import model.Booking;
import model.Hotel;
import model.User;

public interface BookingDAO {
	List<Booking> getAllBookings();
	
	Booking getBookingById(int id);
	
	List<Booking> getBookingByBookingNumber(int booking_number);
	
	List<Booking> getBookingsOfCustomer(User customer);
	
	List<Booking> getBookingsOfHotel(Hotel hotel);
	
	List<Booking> getBookingsOfLocation(String location);
	
	List<Booking> getBookings(String filter, String orderBy);
	
	boolean createBooking(Booking booking);
	
	boolean updateBooking(Booking booking);
	
	boolean deleteBooking(Booking booking);
}
