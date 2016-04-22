package dao;

import java.util.List;

import model.BookingView;
import sqlwhere.core.Where;

public interface BookingViewDAO {
	List<BookingView> getAllBookings();
	
	List<BookingView> getBookings(Where where);
}
