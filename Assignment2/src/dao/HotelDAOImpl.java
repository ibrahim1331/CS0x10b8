package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Hotel;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class HotelDAOImpl implements HotelDAO{
	
	@Override
	public ArrayList<Hotel> getHotels(Where where){
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		
		try{
			Connection conn = DBHelper.getConnection();
			Select select = new Select("*").from("hotel").where(where);
			PreparedStatement pstmt = conn.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			ResultSet rs = pstmt.executeQuery();
			
			this.populateHotelArray(hotels, rs);
			
			DBHelper.close(conn);
			DBHelper.close(pstmt);
			DBHelper.close(rs);
            
		} catch (SQLException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return hotels;
	}

	@Override
	public ArrayList<Hotel> getAllHotels() {
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		
		try{
			Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [hotel] ORDER BY [hotel_id] ASC");
            
            this.populateHotelArray(hotels, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
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
			Connection con = DBHelper.getConnection();
	        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [hotel] WHERE [hotel_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            hotel = this.populateHotel(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
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
			Connection con = DBHelper.getConnection();
	        PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [hotel] WHERE [name] like ?");
            pstmt.setString(1, "%"+name+"%");
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();

            hotel = this.populateHotel(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
            
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
				Connection con = DBHelper.getConnection();
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
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
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
				Connection con = DBHelper.getConnection();
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
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
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
				Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("DELETE FROM [hotel] WHERE [hotel_id] = ?");
                pstmt.setInt(1, hotel.getHotelId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
			}
		}catch(SQLException e){
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		} catch (NamingException e) {
			Logger.getLogger(HotelDAOImpl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		return deleted;
	}
	
	private void populateHotelArray(ArrayList<Hotel> hotels, ResultSet rs) throws SQLException{
		while(rs!=null && rs.next()){
			Hotel hotel = new Hotel();
			hotel.setAddress(rs.getString(Columns.Table.Hotel.ADDRESS));
			hotel.setDateJoined(rs.getTimestamp(Columns.Table.Hotel.JOIN_DATE));
			hotel.setDescription(rs.getString(Columns.Table.Hotel.DESCRIPTION));
			hotel.setHotelId(rs.getInt(Columns.Table.Hotel.HOTEL_ID));
			hotel.setLocation(rs.getInt(Columns.Table.Hotel.LOCATION));
			hotel.setName(rs.getString(Columns.Table.Hotel.NAME));
			hotel.setNoOfRooms(rs.getInt(Columns.Table.Hotel.NO_OF_ROOMS));
			hotel.setRating(rs.getInt(Columns.Table.Hotel.RATING));
			hotels.add(hotel);
		}
	}
	
	private Hotel populateHotel(ResultSet rs) throws SQLException{
		Hotel hotel = null;
		
		if(rs!=null && rs.next()){
			hotel = new Hotel();
			hotel.setAddress(rs.getString(Columns.Table.Hotel.ADDRESS));
			hotel.setDateJoined(rs.getTimestamp(Columns.Table.Hotel.JOIN_DATE));
			hotel.setDescription(rs.getString(Columns.Table.Hotel.DESCRIPTION));
			hotel.setHotelId(rs.getInt(Columns.Table.Hotel.HOTEL_ID));
			hotel.setLocation(rs.getInt(Columns.Table.Hotel.LOCATION));
			hotel.setName(rs.getString(Columns.Table.Hotel.NAME));
			hotel.setNoOfRooms(rs.getInt(Columns.Table.Hotel.NO_OF_ROOMS));
			hotel.setRating(rs.getInt(Columns.Table.Hotel.RATING));
		}
		
		return hotel;
	}

}
