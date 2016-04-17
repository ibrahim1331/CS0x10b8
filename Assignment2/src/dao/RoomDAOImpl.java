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

import model.Hotel;
import model.Room;
import utils.DBHelper;

public class RoomDAOImpl implements RoomDAO{

	@Override
	public List<Room> getAllRooms() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [room] ORDER BY [room_id] ASC");
           
            // populate the bookings ArrayList
            populateRoomArray(rooms, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return rooms;
	}
	
	@Override
	public Room getRoomById(int id) {
		Room room = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [room] WHERE [room_id] = ?");
            pstmt.setInt(1, id);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            room = this.populateRoom(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	room = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	room = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return room;
	}
	
	@Override
	public List<Room> getAllRoomsofHotel(Hotel hotel) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [room] WHERE [hotel_id] = ?");
            pstmt.setInt(1, hotel.getHotelId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the bookings ArrayList
            populateRoomArray(rooms, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return rooms;
	}

	@Override
	public List<Room> getAllRecommendedRooms() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [room] WHERE [recommended] IS NOT NULL ORDER BY [recommended] DESC");
           
            // populate the bookings ArrayList
            populateRoomArray(rooms, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return rooms;
	}

	@Override
	public List<Room> getRecommendedRooms(Hotel hotel) {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [room] WHERE [hotel_id] = ? AND [recommended] IS NOT NULL ORDER BY [recommended] DESC");
            pstmt.setInt(1, hotel.getHotelId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the bookings ArrayList
            populateRoomArray(rooms, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return rooms;
	}

	@Override
	public List<Room> getRooms(String filter, String orderBy) {
		
		return null;
	}
	
	public List<Room> getBedrooms(Room room){
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [room] WHERE [belongs_to] = ? [room_id] ASC");
            pstmt.setInt(1, room.getRoomId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the bookings ArrayList
            populateRoomArray(rooms, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return rooms;
	}
	
	private void populateRoomArray(ArrayList<Room> rooms, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Room room = new Room();
			room.setRoomId(rs.getInt("room_id"));
			room.setType(rs.getString("type"));
			room.setPrice(rs.getInt("price"));
			room.setHotelId(rs.getInt("hotel_id"));
			room.setCapacity(rs.getInt("capacity"));
			room.setSize(rs.getInt("size"));
			room.setRoomNo(rs.getString("room_no"));
			room.setBelongsTo(rs.getInt("belongs_to"));
			room.setDiscount(rs.getInt("discount"));
			room.setRecommended(rs.getInt("recommended"));
			rooms.add(room);
		}
	}
	
	private Room populateRoom(ResultSet rs) throws SQLException{
		Room room = null;
		
		if (rs != null && rs.next()) {
			room = new Room();
			room.setRoomId(rs.getInt("room_id"));
			room.setType(rs.getString("type"));
			room.setPrice(rs.getInt("price"));
			room.setHotelId(rs.getInt("hotel_id"));
			room.setCapacity(rs.getInt("capacity"));
			room.setSize(rs.getInt("size"));
			room.setRoomNo(rs.getString("room_no"));
			room.setBelongsTo(rs.getInt("belongs_to"));
			room.setDiscount(rs.getInt("discount"));
			room.setRecommended(rs.getInt("recommended"));
        }
		
		return room;
	}
	
	@Override
	public boolean createRoom(Room room) {
		boolean saved = false;
		
		try {
            if (room != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [room] ([type], [price], [hotel_id], [capacity], [size], [room_no], [belongs_to], [discount], [recommended]) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setString(1, room.getType());
                pstmt.setInt(2, room.getPrice());
                pstmt.setInt(3, room.getHotelId());
                pstmt.setInt(4, room.getCapacity());
                pstmt.setInt(5, room.getSize());
                pstmt.setString(6, room.getRoomNo());
                pstmt.setInt(7, room.getBelongsTo());
                pstmt.setInt(8, room.getDiscount());
                pstmt.setInt(9, room.getRecommended());
                
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
	public boolean updateRoom(Room room) {
		boolean updated = false;
		
		try {
            if (room != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("UPDATE [room] SET [type] = ?, [price] = ?, [hotel_id] = ?, [capacity] = ?, [size] = ?, [room_no] = ?, [belongs_to] = ?, [discount] = ?, [recommended] = ? WHERE [room_id] = ?");
                pstmt.setString(1, room.getType());
                pstmt.setInt(2, room.getPrice());
                pstmt.setInt(3, room.getHotelId());
                pstmt.setInt(4, room.getCapacity());
                pstmt.setInt(5, room.getSize());
                pstmt.setString(6, room.getRoomNo());
                pstmt.setInt(7, room.getBelongsTo());
                pstmt.setInt(8, room.getDiscount());
                pstmt.setInt(9, room.getRecommended());
                pstmt.setInt(10, room.getRoomId());
                
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
	public boolean deleteRoom(Room room) {
		boolean deleted = false;
		
		try {
            if (room != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [room] WHERE [room_id] = ?");
                pstmt.setInt(1, room.getRoomId());
                
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
