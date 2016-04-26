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

import model.Facility;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class FacilityDAOImpl implements FacilityDAO{

	@Override
	public Facility getFacility(int facilityId) {
		Facility facility = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [facility] WHERE [facility_id] = ?");
            pstmt.setInt(1, facilityId);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            facility = this.populateFacility(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	facility = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	facility = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return facility;
	}

	@Override
	public Facility getFacility(String name) {
		Facility facility = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [facility] WHERE [name] = ?");
            pstmt.setString(1, name);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            facility = this.populateFacility(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	facility = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	facility = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return facility;
	}

	@Override
	public Facility getFacility(Where where) {
		Facility facility = null;
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("facility").where(where);
			
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
			
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        facility = this.populateFacility(rs);
		} catch (SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return facility;
	}

	@Override
	public List<Facility> getAllHotelFacilities() {
		ArrayList<Facility> facilities = new ArrayList<Facility>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [facility] WHERE [category] = 'Hotel' ORDER BY [facility_id] ASC");
           
            // populate the bookings ArrayList
            populateFacilityArray(facilities, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return facilities;
	}
	
	@Override
	public List<Facility> getAllRoomFacilities() {
		ArrayList<Facility> facilities = new ArrayList<Facility>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [facility] WHERE [category] = 'Room' ORDER BY [facility_id] ASC");
           
            // populate the bookings ArrayList
            populateFacilityArray(facilities, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return facilities;
	}

	@Override
	public List<Facility> getFacilities(Where where) {
		ArrayList<Facility> facilities = new ArrayList<Facility>();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("facility").where(where);
			
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
			
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        this.populateFacilityArray(facilities, rs);
	        
	        DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return facilities;
	}
	
	private Facility populateFacility(ResultSet rs) throws SQLException{
		Facility facility = null;
		
		if (rs != null && rs.next()) {
			facility = new Facility();
			facility.setFacilityId(rs.getInt(Columns.Table.Facility.FACILITY_ID));
			facility.setName(rs.getString(Columns.Table.Facility.NAME));
			facility.setCategory(Columns.Table.Facility.CATEGORY);
        }
		
		return facility;
	}
	
	private void populateFacilityArray(ArrayList<Facility> facilities, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Facility facility = new Facility();
			facility.setFacilityId(rs.getInt(Columns.Table.Facility.FACILITY_ID));
			facility.setName(rs.getString(Columns.Table.Facility.NAME));
			facility.setCategory(Columns.Table.Facility.CATEGORY);
			facilities.add(facility);
		}
	}
}
