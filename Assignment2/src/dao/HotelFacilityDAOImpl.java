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

import model.Hotel;
import model.HotelFacility;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class HotelFacilityDAOImpl implements HotelFacilityDAO{

	@Override
	public List<HotelFacility> getAllHotelFacilities() {
		ArrayList<HotelFacility> hotelFacilities = new ArrayList<HotelFacility>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [hotelFacilities]");
           
            // populate the locations ArrayList
            this.populateHotelFacilitiesArray(hotelFacilities, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return hotelFacilities;
	}

	@Override
	public List<HotelFacility> getHotelFacilities(Hotel hotel) {
		ArrayList<HotelFacility> hotelFacilities = new ArrayList<HotelFacility>();
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [hotelFacilities] WHERE [hotel] = ?");
            pstmt.setInt(1, hotel.getHotelId());
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            // populate the locations ArrayList
            this.populateHotelFacilitiesArray(hotelFacilities, rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return hotelFacilities;
	}
	
	public HotelFacility getHotelFacility(Where where){
		HotelFacility hotelFacility = new HotelFacility();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("hotelFacilities").where(where);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			hotelFacility = this.populateHotelFacility(rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return hotelFacility;
	}
	
	@Override
	public List<HotelFacility> getHotelFacilities(Where where) {
		ArrayList<HotelFacility> hotelFacilities = new ArrayList<>();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("hotelFacilities").where(where);
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
				pstmt.setObject(es.getKey(), es.getValue());
			}
			
			ResultSet rs = pstmt.executeQuery();
			
			this.populateHotelFacilitiesArray(hotelFacilities, rs);
			
			DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return hotelFacilities;
	}

	@Override
	public boolean createHotelFacility(HotelFacility hotelFacility) {
		boolean saved = false;
		
		try {
            if (hotelFacility != null) {
            	Connection con = DBHelper.getConnection();
            	PreparedStatement pstmt = con.prepareStatement("INSERT INTO [hotelFacilities] ([hotel], [facility]) VALUES (?, ?)");
                pstmt.setInt(1, hotelFacility.getHotel());
            	pstmt.setInt(2, hotelFacility.getFacility());
                
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
	public boolean deleteHotelFacility(HotelFacility hotelFacility) {
		boolean deleted = false;
		
		try {
            if (hotelFacility != null) {
            	Connection con = DBHelper.getConnection();
                PreparedStatement pstmt = con.prepareStatement("DELETE FROM [hotelFacilities] WHERE [hotel] = ? AND [facility] = ?");
                pstmt.setInt(1, hotelFacility.getHotel());
                pstmt.setInt(2, hotelFacility.getFacility());
                
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

	private void populateHotelFacilitiesArray(ArrayList<HotelFacility> hotelFacilities, ResultSet rs) throws SQLException{
		while(rs != null && rs.next()){
			HotelFacility hotelFacility = new HotelFacility();
			hotelFacility.setFacility(rs.getInt(Columns.Table.HotelFacility.FACILITY));
			hotelFacility.setHotel(rs.getInt(Columns.Table.HotelFacility.HOTEL));
			hotelFacilities.add(hotelFacility);
		}
	}
	
	private HotelFacility populateHotelFacility(ResultSet rs) throws SQLException{
		HotelFacility hotelFacility = null;
		
		if (rs != null && rs.next()) {
			hotelFacility = new HotelFacility();
			hotelFacility.setFacility(rs.getInt(Columns.Table.HotelFacility.FACILITY));
			hotelFacility.setHotel(rs.getInt(Columns.Table.HotelFacility.HOTEL));
        }
		
		return hotelFacility;
	}
}
