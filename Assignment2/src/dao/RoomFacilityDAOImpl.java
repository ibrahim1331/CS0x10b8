package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Room;
import model.RoomFacility;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class RoomFacilityDAOImpl implements RoomFacilityDAO{

	@Override
	public List<RoomFacility> getAllRoomFacilities() {
		ArrayList<RoomFacility> roomFacilities = new ArrayList<RoomFacility>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [roomFacilities]");
           
            // populate the locations ArrayList
            this.populateRoomFacilitiesArray(roomFacilities, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomFacilities;
	}

	@Override
	public List<RoomFacility> getRoomFacilities(Room room) {
		ArrayList<RoomFacility> roomFacilities = new ArrayList<RoomFacility>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [roomFacilities] WHERE [room] = ?");
            pstmt.setInt(1, room.getRoomId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the locations ArrayList
            this.populateRoomFacilitiesArray(roomFacilities, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomFacilities;
	}

	@Override
	public List<RoomFacility> getRoomFacilities(Where where) {
		ArrayList<RoomFacility> roomFacilities = new ArrayList<RoomFacility>();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("roomFacilities").where(where);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			this.populateRoomFacilitiesArray(roomFacilities, rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomFacilities;
	}

	@Override
	public RoomFacility getRoomFacility(Where where) {
		RoomFacility roomFacility = new RoomFacility();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("roomFacilities").where(where);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			roomFacility = this.populateRoomFacility(rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomFacility;
	}

	@Override
	public boolean createRoomFacility(RoomFacility roomFacility) {
		boolean saved = false;
		
		try {
            if (roomFacility != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [roomFacilities] ([room], [facility]) VALUES (?, ?)");
                pstmt.setInt(1, roomFacility.getRoom());
            	pstmt.setInt(2, roomFacility.getFacility());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	saved = true;
                }
                
                DBHelper.close(con);
                DBHelper.close(pstmt);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return saved;
	}

	@Override
	public boolean deleteRoomFacility(RoomFacility roomFacility) {
		boolean deleted = false;
		
		try {
            if (roomFacility != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [roomFacilities] WHERE [room] = ? AND [facility] = ?");
                pstmt.setInt(1, roomFacility.getRoom());
                pstmt.setInt(2, roomFacility.getFacility());
                
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
	
	private void populateRoomFacilitiesArray(ArrayList<RoomFacility> roomFacilities, ResultSet rs) throws SQLException{
		while(rs != null && rs.next()){
			RoomFacility roomFacility = new RoomFacility();
			roomFacility.setFacility(rs.getInt(Columns.Table.RoomFacility.FACILITY));
			roomFacility.setRoom(rs.getInt(Columns.Table.RoomFacility.ROOM));
			roomFacilities.add(roomFacility);
		}
	}
	
	private RoomFacility populateRoomFacility(ResultSet rs) throws SQLException{
		RoomFacility roomFacility = null;
		
		if (rs != null && rs.next()) {
			roomFacility = new RoomFacility();
			roomFacility.setFacility(rs.getInt(Columns.Table.RoomFacility.FACILITY));
			roomFacility.setRoom(rs.getInt(Columns.Table.RoomFacility.ROOM));
        }
		
		return roomFacility;
	}
}
