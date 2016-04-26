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
import model.RoomBed;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class RoomBedDAOImpl implements RoomBedDAO{

	@Override
	public List<RoomBed> getAllRoomBeds() {
		ArrayList<RoomBed> roomBeds = new ArrayList<RoomBed>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [roomBed]");
           
            // populate the locations ArrayList
            this.populateRoomBedArray(roomBeds, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomBeds;
	}

	@Override
	public List<RoomBed> getRoomBeds(Room room) {
		ArrayList<RoomBed> roomBeds = new ArrayList<RoomBed>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [roomBed] WHERE [room] = ?");
            pstmt.setInt(1, room.getRoomId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the locations ArrayList
            this.populateRoomBedArray(roomBeds, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomBeds;
	}

	@Override
	public List<RoomBed> getRoomBeds(Where where) {
		ArrayList<RoomBed> roomBeds = new ArrayList<>();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("roomBed").where(where);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			this.populateRoomBedArray(roomBeds, rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomBeds;
	}

	@Override
	public RoomBed getRoomBed(Where where) {
		RoomBed roomBed = new RoomBed();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("roomBed").where(where);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			roomBed = this.populateRoomBed(rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return roomBed;
	}

	@Override
	public boolean createRoomBed(RoomBed roomBed) {
		boolean saved = false;
		
		try {
            if (roomBed != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [roomBed] ([room], [bed]) VALUES (?, ?)");
                pstmt.setInt(1, roomBed.getRoom());
            	pstmt.setInt(2, roomBed.getBed());
                
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
	public boolean deleteRoomBed(RoomBed roomBed) {
		boolean deleted = false;
		
		try {
            if (roomBed != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [roomBed] WHERE [id] = ?");
                pstmt.setInt(1, roomBed.getId());
                
                // execute the SQL statement
                int rows = pstmt.executeUpdate();

                if (rows > 0) {
                	deleted = true;
                }
                
                DBHelper.close(pstmt);
                DBHelper.close(con);
            }
        } catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return deleted;
	}
	
	private void populateRoomBedArray(ArrayList<RoomBed> roomBeds, ResultSet rs) throws SQLException{
		while(rs != null && rs.next()){
			RoomBed roomBed = new RoomBed();
			roomBed.setId(rs.getInt(Columns.Table.RoomBed.ID));
			roomBed.setBed(rs.getInt(Columns.Table.RoomBed.BED));
			roomBed.setRoom(rs.getInt(Columns.Table.RoomBed.ROOM));
			roomBeds.add(roomBed);
		}
	}
	
	private RoomBed populateRoomBed(ResultSet rs) throws SQLException{
		RoomBed roomBed = null;
		
		if (rs != null && rs.next()) {
			roomBed = new RoomBed();
			roomBed.setId(rs.getInt(Columns.Table.RoomBed.ID));
			roomBed.setBed(rs.getInt(Columns.Table.RoomBed.BED));
			roomBed.setRoom(rs.getInt(Columns.Table.RoomBed.ROOM));
        }
		
		return roomBed;
	}
}
