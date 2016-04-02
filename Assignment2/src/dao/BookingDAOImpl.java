package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.json.JSONException;
import org.json.JSONObject;

import model.Booking;
import model.Customer;
import model.Hotel;
import utils.DBHelper;

public class BookingDAOImpl implements BookingDAO{

	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking getBookingById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Booking getBookingByBookingNumber(int booking_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> getBookingsOfCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> getBookingsOfHotel(Hotel hotel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> getBookingsOfLocation(String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> getBookings(String filter, String orderBy) {
		try{
			ArrayList<Booking> bookings = new ArrayList<Booking>();
			
			JSONObject filterObj  = null;
			JSONObject orderByObj = null;
			String queryString = "SELECT * FROM [booking]";
			Connection con = DBHelper.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			if(!filter.equals(""))
				filterObj = new JSONObject(filter);
			if(!orderBy.equals(""))
				orderByObj = new JSONObject(orderBy);
			
			if(filterObj != null){
				queryString += " WHERE ";
				for(int i = 0; i < JSONObject.getNames(filterObj).length; i++){
					if(i == JSONObject.getNames(filterObj).length - 1)
						queryString += JSONObject.getNames(filterObj)[i] + "= ?";
					else
						queryString += JSONObject.getNames(filterObj)[i] + "= ? AND ";
				}

				if(orderByObj != null){
					queryString += " ORDER BY ";
					for(int i = 0; i < JSONObject.getNames(orderByObj).length; i++){
						if(i == JSONObject.getNames(orderByObj).length - 1)
							queryString += JSONObject.getNames(orderByObj)[i] + " " + orderByObj.getString(JSONObject.getNames(orderByObj)[i]);
						else
							queryString += JSONObject.getNames(orderByObj)[i] + " " + orderByObj.getString(JSONObject.getNames(orderByObj)[i]) + ", ";
					}
				}
				
				queryString += ";";
				
				pstmt = con.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            
	            for(int i = 0; i < JSONObject.getNames(filterObj).length; i++){
	            	if(filterObj.get(JSONObject.getNames(filterObj)[i]) instanceof Integer)
	            		pstmt.setInt(i + 1, (Integer) filterObj.get(JSONObject.getNames(filterObj)[i]));
	            	else if(filterObj.get(JSONObject.getNames(filterObj)[i]) instanceof String)
	            		pstmt.setString(i + 1, (String) filterObj.get(JSONObject.getNames(filterObj)[i]));
				}
			}else{
				pstmt = con.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			}
			
			// execute the SQL statement
            rs= pstmt.executeQuery();
			
			// total number of records
            int numRow = 0;
            if (rs != null && rs.last() != false) {
                numRow = rs.getRow();
                rs.beforeFirst();
            }
            
            if(numRow > 1){
            	populateBookingArray(bookings, rs);
            }
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
            return bookings;
		}catch(JSONException ex){
			Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return null;
	}
	
	
	private void populateBookingArray(ArrayList<Booking> bookings, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Booking booking = new Booking();
			booking.setBookingId(rs.getInt("booking_id"));
			booking.setBookingNumber(rs.getInt("booking_number"));
			booking.setCustomerId(rs.getInt("customer_id"));
			booking.setRoomId(rs.getInt("room_id"));
			booking.setNoOfPeople(rs.getInt("no_of_people"));
			booking.setCheckInDate(rs.getTimestamp("check_in_date"));
			booking.setCheckOutDate(rs.getTimestamp("check_out_date"));
			booking.setPurpose(rs.getString("purpose"));
			booking.setBookingDate(rs.getTimestamp("booking_date"));
			booking.setPin(rs.getString("pin"));
			booking.setCancelled(rs.getBoolean("is_cancelled"));
			bookings.add(booking);
		}
	}
	
	@Override
	public boolean createBooking(Booking booking) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBooking(Booking booking) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBooking(Booking booking) {
		// TODO Auto-generated method stub
		return false;
	}

}
