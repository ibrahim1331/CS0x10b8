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

import model.Booking;
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
		
		Connection con;
		ResultSet rs;
		PreparedStatement pstmt;
		try{
			con = DBHelper.getConnection();
			Select select = new Select("*").from("booking").where(where).orderBy(Columns.Table.Booking.BOOKING_ID, true);
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
	        System.out.println(select.getIndexMap());
			pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			rs = pstmt.executeQuery();
			
			this.populateBookingArray(bookings, rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
	}

	@Override
	public Booking getBookingById(Integer id) {
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
			booking.setBookingId(rs.getInt(Columns.Table.Booking.BOOKING_ID));
			booking.setBookingNumber(rs.getInt(Columns.Table.Booking.BOOKING_NUMBER));
			booking.setCustomerId(rs.getInt(Columns.Table.Booking.CUSTOMER_ID));
			if(rs.wasNull()){
				booking.setCustomerId(null);
			}
			booking.setRoomId(rs.getInt(Columns.Table.Booking.ROOM_ID));
			booking.setNoOfPeople(rs.getInt(Columns.Table.Booking.NO_OF_PEOPLE));
			booking.setCheckInDate(rs.getTimestamp(Columns.Table.Booking.CHECK_IN_DATE));
			booking.setCheckOutDate(rs.getTimestamp(Columns.Table.Booking.CHECK_OUT_DATE));
			booking.setPurpose(rs.getString(Columns.Table.Booking.PURPOSE));
			booking.setBookingDate(rs.getTimestamp(Columns.Table.Booking.BOOKING_DATE));
			booking.setPin(rs.getString(Columns.Table.Booking.PIN));
			booking.setIsCancelled(rs.getBoolean(Columns.Table.Booking.IS_CANCELLED));
			booking.setPrice(rs.getInt(Columns.Table.Booking.PRICE));
        }
		
		return booking;
	}
	
	private void populateBookingArray(ArrayList<Booking> bookings, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Booking booking = new Booking();
			booking.setBookingId(rs.getInt(Columns.Table.Booking.BOOKING_ID));
			booking.setBookingNumber(rs.getInt(Columns.Table.Booking.BOOKING_NUMBER));
			booking.setCustomerId(rs.getInt(Columns.Table.Booking.CUSTOMER_ID));
			if(rs.wasNull()){
				booking.setCustomerId(null);
			}
			booking.setRoomId(rs.getInt(Columns.Table.Booking.ROOM_ID));
			booking.setNoOfPeople(rs.getInt(Columns.Table.Booking.NO_OF_PEOPLE));
			booking.setCheckInDate(rs.getTimestamp(Columns.Table.Booking.CHECK_IN_DATE));
			booking.setCheckOutDate(rs.getTimestamp(Columns.Table.Booking.CHECK_OUT_DATE));
			booking.setPurpose(rs.getString(Columns.Table.Booking.PURPOSE));
			booking.setBookingDate(rs.getTimestamp(Columns.Table.Booking.BOOKING_DATE));
			booking.setPin(rs.getString(Columns.Table.Booking.PIN));
			booking.setIsCancelled(rs.getBoolean(Columns.Table.Booking.IS_CANCELLED));
			booking.setPrice(rs.getInt(Columns.Table.Booking.PRICE));
			bookings.add(booking);
		}
	}
	
	@Override
	public boolean createBooking(Booking booking) {
		boolean saved = false;
		
		try {
            if (booking != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [booking] ([booking_number], [customer_id], [room_id], [no_of_people], [check_in_date], [check_out_date], [purpose], [booking_date], [pin], [is_cancelled], [price]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, booking.getBookingNumber());
                if(booking.getCustomerId()!=null){
                	pstmt.setInt(2, booking.getCustomerId());
                } else {
                	pstmt.setNull(2, java.sql.Types.INTEGER);
                }
                pstmt.setInt(3, booking.getRoomId());
                pstmt.setInt(4, booking.getNoOfPeople());
                pstmt.setTimestamp(5, booking.getCheckInDate());
                pstmt.setTimestamp(6, booking.getCheckOutDate());
                pstmt.setString(7, booking.getPurpose());
                pstmt.setTimestamp(8, booking.getBookingDate());
                pstmt.setString(9, booking.getPin());
                pstmt.setBoolean(10, booking.getIsCancelled());
                pstmt.setInt(11, booking.getPrice());
                
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
                
                DBHelper.close(pstmt);
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
                PreparedStatement pstmt = con.prepareStatement("UPDATE [booking] SET [booking_number] = ?, [customer_id] = ?, [room_id] = ?, [no_of_people] = ?, [check_in_date] = ?, [check_out_date] = ?, [purpose] = ?, [booking_date] = ?, [pin] = ?, [is_cancelled] = ?, [price] = ? WHERE [booking_id] = ?");
                pstmt.setInt(1, booking.getBookingNumber());
                if(booking.getCustomerId()!=null){
                	pstmt.setInt(2, booking.getCustomerId());
                } else {
                	pstmt.setNull(2, java.sql.Types.INTEGER);
                }
                pstmt.setInt(3, booking.getRoomId());
                pstmt.setInt(4, booking.getNoOfPeople());
                pstmt.setTimestamp(5, booking.getCheckInDate());
                pstmt.setTimestamp(6, booking.getCheckOutDate());
                pstmt.setString(7, booking.getPurpose());
                pstmt.setTimestamp(8, booking.getBookingDate());
                pstmt.setString(9, booking.getPin());
                pstmt.setBoolean(10, booking.getIsCancelled());
                pstmt.setInt(11, booking.getPrice());
                pstmt.setInt(12, booking.getBookingId());
                
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