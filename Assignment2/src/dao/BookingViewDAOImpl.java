package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import model.BookingView;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class BookingViewDAOImpl implements BookingViewDAO {

	@Override
	public List<BookingView> getAllBookings() {
		ArrayList<BookingView> result = new ArrayList<>();
		
		try{
			Connection con = DBHelper.getConnection();
	        Select select = new Select("*").from("bookingView");
	        
	        Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
	        System.out.println(select.getIndexMap());
	        PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        this.populateBookingViewArray(result, rs);
	        
	        DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		}catch(SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	public List<BookingView> getBookings(Where where){
		ArrayList<BookingView> result = new ArrayList<>();
		
		try{
			Connection con = DBHelper.getConnection();
	        Select select = new Select("*").from("bookingView").where(where);
	        
	        Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
	        System.out.println(select.getIndexMap());
	        PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        this.populateBookingViewArray(result, rs);
	        
	        DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		}catch(SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	private void populateBookingViewArray(ArrayList<BookingView> result, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			BookingView booking = new BookingView();
			booking.setBookingId(rs.getInt(Columns.View.BookingView.BOOKING_ID));
			booking.setBookingNumber(rs.getInt(Columns.View.BookingView.BOOKING_NUMBER));
			booking.setCustomerId(rs.getInt(Columns.View.BookingView.CUSTOMER_ID));
			booking.setNoOfPeople(rs.getInt(Columns.View.BookingView.NO_OF_PEOPLE));
			booking.setCheckInDate(rs.getTimestamp(Columns.View.BookingView.CHECK_IN_DATE));
			booking.setCheckOutDate(rs.getTimestamp(Columns.View.BookingView.CHECK_OUT_DATE));
			booking.setPurpose(rs.getString(Columns.View.BookingView.PURPOSE));
			booking.setBookingDate(rs.getTimestamp(Columns.View.BookingView.BOOKING_DATE));
			booking.setPin(rs.getString(Columns.View.BookingView.PIN));
			booking.setIsCancelled(rs.getBoolean(Columns.View.BookingView.IS_CANCELLED));
			
			booking.setHotelId(rs.getInt(Columns.View.BookingView.HOTEL_ID));
			booking.setHotelName(rs.getString(Columns.View.BookingView.HOTEL_NAME));
			booking.setHotelAddress(rs.getString(Columns.View.BookingView.ADDRESS));
			booking.setNoOfRooms(rs.getInt(Columns.View.BookingView.NO_OF_ROOMS));
			booking.setHotelRating(rs.getFloat(Columns.View.BookingView.RATING));
			booking.setHotelDescription(rs.getString(Columns.View.BookingView.DESCRIPTION));
			booking.setHotelDateJoined(rs.getTimestamp(Columns.View.BookingView.JOIN_DATE));
			
			booking.setRoomId(rs.getInt(Columns.View.BookingView.ROOM_ID));
			booking.setRoomType(rs.getString(Columns.View.BookingView.TYPE));
			booking.setRoomPrice(rs.getInt(Columns.View.BookingView.ROOM_PRICE));
			booking.setRoomCapacity(rs.getInt(Columns.View.BookingView.ROOM_CAPACITY));
			booking.setRoomSize(rs.getInt(Columns.View.BookingView.ROOM_SIZE));
			booking.setRoomNo(rs.getString(Columns.View.BookingView.ROOM_NO));
			booking.setBelongsTo(rs.getInt(Columns.View.BookingView.BELONGS_TO));
			booking.setDiscount(rs.getInt(Columns.View.BookingView.DISCOUNT));
			booking.setRecommended(rs.getInt(Columns.View.BookingView.RECOMMENDED));
			booking.setLocationId(rs.getInt(Columns.View.BookingView.LOCATION_ID));
			booking.setLocationName(rs.getString(Columns.View.BookingView.LOCATION_NAME));
			booking.setCountry(rs.getString(Columns.View.BookingView.COUNTRY));
			result.add(booking);
		}
	}
	
}
