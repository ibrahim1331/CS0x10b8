package dao;

import java.util.List;

import model.Booking;
import model.Customer;
import model.Hotel;

public interface BookingDAO {
	List<Booking> getAllBookings();
	
	Booking getBookingById(int id);
	
	Booking getBookingByBookingNumber(int booking_number);
	
	List<Booking> getBookingsOfCustomer(Customer customer);
	
	List<Booking> getBookingsOfHotel(Hotel hotel);
	
	List<Booking> getBookingsOfLocation(String location);
	
	List<Booking> getBookings(String filter, String orderBy);
	
	boolean createBooking(Booking booking);
	
	boolean updateBooking(Booking booking);
	
	boolean deleteBooking(Booking booking);
}
