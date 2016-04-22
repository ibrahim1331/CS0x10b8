package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Booking;
import model.Hotel;
import model.User;
import service.ValidationService;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class BookingDAOImpl implements BookingDAO{
	ValidationService validServ = new ValidationService();
	
	@Override
	public List<Booking> getBookings(Where where){
		ArrayList<Booking> bookings = new ArrayList<>();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("booking").where(where).orderBy(Columns.Table.Booking.BOOKING_ID, true);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			this.populateBookingArray(bookings, rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
	}

	@Override
	public Booking getBookingById(int id) {
		Booking booking = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [booking] WHERE [booking_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            booking = this.populateBooking(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	booking = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	booking = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return booking;
	}
	
	private Booking populateBooking(ResultSet rs) throws SQLException{
		Booking booking = null;
		
		if (rs != null && rs.next()) {
			booking = new Booking();
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
        }
		
		return booking;
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
		boolean saved = false;
		
		try {
            if (booking != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [booking] ([booking_number], [customer_id], [room_id], [no_of_people], [check_in_date], [check_out_date], [purpose], [booking_date], [pin], [is_cancelled]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, booking.getBookingNumber());
                pstmt.setInt(2, booking.getCustomerId());
                pstmt.setInt(3, booking.getRoomId());
                pstmt.setInt(4, booking.getNoOfPeople());
                pstmt.setTimestamp(5, booking.getCheckInDate());
                pstmt.setTimestamp(6, booking.getCheckOutDate());
                pstmt.setString(7, booking.getPurpose());
                pstmt.setTimestamp(8, booking.getBookingDate());
                pstmt.setString(9, booking.getPin());
                pstmt.setBoolean(10, booking.isCancelled());
                
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
                
                DBHelper.close(con);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return saved;
	}
	
	@Override
	public boolean updateBooking(Booking booking) {
		boolean updated = false;
		
		try {
            if (booking != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("UPDATE [booking] SET [booking_number] = ?, [customer_id] = ?, [room_id] = ?, [no_of_people] = ?, [check_in_date] = ?, [check_out_date] = ?, [purpose] = ?, [booking_date] = ?, [pin] = ?, [is_cancelled] = ? WHERE [booking_id] = ?");
                pstmt.setInt(1, booking.getBookingNumber());
                pstmt.setInt(2, booking.getCustomerId());
                pstmt.setInt(3, booking.getRoomId());
                pstmt.setInt(4, booking.getNoOfPeople());
                pstmt.setTimestamp(5, booking.getCheckInDate());
                pstmt.setTimestamp(6, booking.getCheckOutDate());
                pstmt.setString(7, booking.getPurpose());
                pstmt.setTimestamp(8, booking.getBookingDate());
                pstmt.setString(9, booking.getPin());
                pstmt.setBoolean(10, booking.isCancelled());
                pstmt.setInt(11, booking.getBookingId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	updated = true;
                }
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return updated;
	}

	@Override
	public boolean deleteBooking(Booking booking) {
		boolean deleted = false;
		
		try {
            if (booking != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [booking] WHERE [booking_id] = ?");
                pstmt.setInt(1, booking.getBookingId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return deleted;
	}

}
