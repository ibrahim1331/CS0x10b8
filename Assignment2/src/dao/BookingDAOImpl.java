package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.json.JSONException;
import org.json.JSONObject;

import model.Booking;
import model.Hotel;
import model.User;
import utils.DBHelper;

public class BookingDAOImpl implements BookingDAO{

	@Override
	public List<Booking> getAllBookings() {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [booking] ORDER BY [booking_id] ASC");
           
            // populate the bookings ArrayList
            populateBookingArray(bookings, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	booking = null;
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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

	@Override
	public List<Booking> getBookingByBookingNumber(int booking_number) {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [booking] WHERE [booking_number] = ?");
            pstmt.setInt(1, booking_number);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the bookings ArrayList
            populateBookingArray(bookings, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
	}

	@Override
	public List<Booking> getBookingsOfCustomer(User customer) {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [booking] WHERE [customer_id] = ?");
            pstmt.setInt(1, customer.getUserId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the bookings ArrayList
            populateBookingArray(bookings, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
	}

	@Override
	public List<Booking> getBookingsOfHotel(Hotel hotel) {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		try {
            Connection con = DBHelper.getConnection();
            
            //	Get all the rooms of the hotel            
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [room] WHERE [hotel_id] = ?");
            pstmt.setInt(1, hotel.getHotelId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // Create a room list string
            String room_list = "";
            int count = 0;
            while(rs != null && rs.next()){
    			if(count != 0){
    				room_list += ", ";
    			}
    			
    			room_list += rs.getInt("room_id");
    			
    			count++;
    		}
            
            rs = null;

            //	Get all the bookings of the hotel
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM [booking] WHERE [room_id] IN ("+room_list+") ORDER BY [booking_id] ASC");
            
            // populate the bookings ArrayList
            populateBookingArray(bookings, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(stmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
	}

	@Override
	public List<Booking> getBookingsOfLocation(String location) {
		ArrayList<Booking> bookings = new ArrayList<Booking>();
		
		try {
            Connection con = DBHelper.getConnection();
            
            //	Get all hotels in location            
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [hotel] WHERE [location] = ?");
            pstmt.setString(1, location);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // Create a room list string
            String hotel_list = "";
            int count = 0;
            while(rs != null && rs.next()){
    			if(count != 0){
    				hotel_list += ", ";
    			}
    			
    			hotel_list += rs.getInt("hotel_id");
    			
    			count++;
    		}
            
            rs = null;
            
            //	Get all the rooms of the hotel            
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM [room] WHERE [hotel_id] IN ("+hotel_list+") ORDER BY [room_id] ASC");
            
            // execute the SQL statement
            rs= pstmt.executeQuery();
            
            // Create a room list string
            String room_list = "";
            count = 0;
            while(rs != null && rs.next()){
    			if(count != 0){
    				room_list += ", ";
    			}
    			
    			room_list += rs.getInt("room_id");
    			
    			count++;
    		}
            
            rs = null;
            stmt = null;
            
            //	Get all the bookings of the hotel
            stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery("SELECT * FROM [booking] WHERE [room_id] IN ("+room_list+") ORDER BY [booking_id] ASC");
            
            // populate the bookings ArrayList
            populateBookingArray(bookings, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(stmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
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
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(BookingDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		return deleted;
	}

}
