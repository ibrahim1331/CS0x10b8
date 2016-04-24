package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Hotel;

public class HotelDAOImpl implements HotelDAO{

	@Override
	public ArrayList<Hotel> getAllHotels() {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		
		try{
			Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [hotel] ORDER BY [hotel_id] ASC");
            
            while(rs != null && rs.next()){
            	Hotel hotel = new Hotel();
            	hotel.setHotelId(rs.getInt("hotel_id"));
            	hotel.setName(rs.getString("name"));
            	hotel.setLocation(rs.getInt("location"));
            	hotel.setAddress(rs.getString("address"));
            	hotel.setNoOfRooms(rs.getInt("no_of_rooms"));
            	hotel.setRating(rs.getFloat("rating"));
            	hotel.setDescription(rs.getString("description"));
            	hotel.setDateJoined(rs.getTimestamp("join_date"));
    			hotels.add(hotel);
    		}
            
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
            
		} catch (SQLException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
	
		return hotels;
	}

	@Override
	public Hotel getHotelById(int id) {
		Hotel hotel = null;
		
		try{
			Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
	        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [hotel] WHERE [hotel_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            if (rs != null && rs.next()) {
            	hotel = new Hotel();
            	hotel.setHotelId(rs.getInt("hotel_id"));
            	hotel.setName(rs.getString("name"));
            	hotel.setLocation(rs.getInt("location"));
            	hotel.setAddress(rs.getString("address"));
            	hotel.setNoOfRooms(rs.getInt("no_of_rooms"));
            	hotel.setRating(rs.getFloat("rating"));
            	hotel.setDescription(rs.getString("description"));
            	hotel.setDateJoined(rs.getTimestamp("join_date"));
            }
            
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
            	pstmt.close();
            }
            if (con != null) {
                con.close();
            }
            
		}catch(SQLException e){
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return hotel;
	}
	
	public Hotel getHotelByName(String name) {
		Hotel hotel = null;
		
		try{
			Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
            Connection con = ds.getConnection();
	        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [hotel] WHERE [name] = ?");
            pstmt.setString(1, name);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            if (rs != null && rs.next()) {
            	hotel = new Hotel();
            	hotel.setHotelId(rs.getInt("hotel_id"));
            	hotel.setName(rs.getString("name"));
            	hotel.setLocation(rs.getInt("location"));
            	hotel.setAddress(rs.getString("address"));
            	hotel.setNoOfRooms(rs.getInt("no_of_rooms"));
            	hotel.setRating(rs.getFloat("rating"));
            	hotel.setDescription(rs.getString("description"));
            	hotel.setDateJoined(rs.getTimestamp("join_date"));
            }
            
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
            	pstmt.close();
            }
            if (con != null) {
                con.close();
            }
            
		}catch(SQLException e){
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return hotel;
	}
	
	@Override
	public boolean createHotel(Hotel hotel) {
		boolean saved = false;
		
		try{
			if(hotel != null){
				Context initCtx = new InitialContext();
                Context envCtx = (Context)initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
                Connection con = ds.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [hotel] ([name], [location], [address], [no_of_rooms], [rating], [description], [join_date]) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, hotel.getName());
                pstmt.setInt(2, hotel.getLocation());
                pstmt.setString(3, hotel.getAddress());
                pstmt.setInt(4, hotel.getNoOfRooms());
                pstmt.setFloat(5, hotel.getRating());
                pstmt.setString(6, hotel.getDescription());
                pstmt.setTimestamp(7, hotel.getDateJoined());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
                
                if (pstmt != null) {
                	pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
			}
		}catch(SQLException e){
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return saved;
	}

	@Override
	public boolean updateHotel(Hotel hotel) {
		boolean updated = false;
		
		try{
			if(hotel != null){
				Context initCtx = new InitialContext();
                Context envCtx = (Context)initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
                Connection con = ds.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("UPDATE [hotel] SET [name]= ?, [location]= ?, [address]= ?, [no_of_rooms]= ?, [rating]= ?, [description]= ?, [join_date]= ? WHERE [hotel_id] = ?");
                pstmt.setString(1, hotel.getName());
                pstmt.setInt(2, hotel.getLocation());
                pstmt.setString(3, hotel.getAddress());
                pstmt.setInt(4, hotel.getNoOfRooms());
                pstmt.setFloat(5, hotel.getRating());
                pstmt.setString(6, hotel.getDescription());
                pstmt.setTimestamp(7, hotel.getDateJoined());
                pstmt.setInt(8, hotel.getHotelId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	updated = true;
                }
                
                if (pstmt != null) {
                	pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
			}
		}catch(SQLException e){
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return updated;
	}

	@Override
	public boolean deleteHotel(Hotel hotel) {
		boolean deleted = false;
		
		try{
			if(hotel != null){
				Context initCtx = new InitialContext();
                Context envCtx = (Context)initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource)envCtx.lookup("jdbc/hotelbooking");
                Connection con = ds.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("DELETE FROM [hotel] WHERE [hotel_id] = ?");
                pstmt.setInt(1, hotel.getHotelId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
                
                if (pstmt != null) {
                	pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
			}
		}catch(SQLException e){
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return deleted;
	}

}
