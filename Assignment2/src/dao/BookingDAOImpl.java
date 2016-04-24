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
import utils.DBHelper;

import service.ValidationService;

public class BookingDAOImpl implements BookingDAO{
	ValidationService validServ = new ValidationService();
	
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
			booking.setIsCancelled(rs.getBoolean("is_cancelled"));
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
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return bookings;
	}

	@Override
	public List<Booking> getBookings(String filter, String orderBy) {
		try{
			ArrayList<Booking> bookings = new ArrayList<Booking>();
			
			JsonParser parser = new JsonParser();
			JsonObject filterObj = null;
			JsonObject orderByObj = null;
			String queryString = "SELECT * FROM [booking]";
			Connection con = DBHelper.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			if(!filter.equals(""))
				filterObj = parser.parse(filter).getAsJsonObject();
			if(!orderBy.equals(""))
				orderByObj = parser.parse(orderBy).getAsJsonObject();
			
			if(filterObj != null){
				queryString += " WHERE ";
				Set<Entry<String, JsonElement>> filterEntrySet = filterObj.entrySet();
				int i = 0;
				for(Entry<String, JsonElement> filterEntry : filterEntrySet){
					if(i == filterEntrySet.size() - 1)
						queryString += filterEntry.getKey() + " = ?";
					else
						queryString += filterEntry.getKey() + " = ? AND ";
					i++;
				}

				if(orderByObj != null){
					queryString += " ORDER BY ";
					Set<Entry<String, JsonElement>> orderByEntrySet = orderByObj.entrySet();
					i = 0;
					for(Entry<String, JsonElement> orderByEntry : orderByEntrySet){
						if(i == orderByEntrySet.size() - 1)
							queryString += orderByEntry.getKey() + " " + orderByEntry.getValue().getAsString();
						else
							queryString += orderByEntry.getKey() + " " + orderByEntry.getValue().getAsString() + ", ";
					}
				}
				
				queryString += ";";
				
				pstmt = con.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				i = 1;
	            for(Entry<String, JsonElement> filterEntry : filterEntrySet){
	            	if(validServ.isInteger(filterEntry.getValue().getAsString())){
	            		pstmt.setInt(i, filterEntry.getValue().getAsInt());
	            	}
	            	else{
	            		pstmt.setString(i, filterEntry.getValue().getAsString());
	            	}
	            	i++;
	            }
			}else{
				pstmt = con.prepareStatement(queryString, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			}
			
			// execute the SQL statement
            rs= pstmt.executeQuery();
			
        	populateBookingArray(bookings, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
            return bookings;
		} catch (SQLException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
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
			booking.setIsCancelled(rs.getBoolean("is_cancelled"));
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
                pstmt.setBoolean(10, booking.getIsCancelled());
                
                
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
                pstmt.setBoolean(10, booking.getIsCancelled());
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
