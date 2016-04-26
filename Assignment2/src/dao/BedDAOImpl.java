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

import model.Bed;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.Columns;
import utils.DBHelper;

public class BedDAOImpl implements BedDAO{

	@Override
	public List<Bed> getAllBeds() {
		ArrayList<Bed> beds = new ArrayList<Bed>();
		
		try {
            Connection con = DBHelper.getConnection();
	        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * FROM [bed] ORDER BY [bed_id] ASC");
           
            // populate the bookings ArrayList
            this.populateBedArray(beds, rs);
            
            DBHelper.close(con);
            DBHelper.close(stmt);
            DBHelper.close(rs);
            
        } catch (SQLException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return beds;
	}

	@Override
	public Bed getBed(Integer bedId) {
		Bed bed = null;
		
		try {
            Connection con = DBHelper.getConnection();
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM [bed] WHERE [bed_id] = ?");
            pstmt.setInt(1, bedId);
            
            // execute the SQL statement
            ResultSet rs= pstmt.executeQuery();
            
            bed = this.populateBed(rs);
            
            DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
        } catch (SQLException ex) {
        	bed = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        	bed = null;
        	Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
		
		return bed;
	}

	@Override
	public Bed getBed(Where where) {
		Bed bed = null;
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("bed").where(where);
			
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
			
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        bed = this.populateBed(rs);
		} catch (SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return bed;
	}

	@Override
	public List<Bed> getBeds(Where where) {
		ArrayList<Bed> beds = new ArrayList<Bed>();
		
		try{
			Connection con = DBHelper.getConnection();
			Select select = new Select("*").from("bed").where(where);
			
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
			
			PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        this.populateBedArray(beds, rs);
	        
	        DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		} catch (SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return beds;
	}
	
	private Bed populateBed(ResultSet rs) throws SQLException{
		Bed bed = null;
		
		if (rs != null && rs.next()) {
			bed = new Bed();
			bed.setBedId(rs.getInt(Columns.Table.Bed.BED_ID));
			bed.setSize(rs.getString(Columns.Table.Bed.SIZE));
			bed.setType(rs.getString(Columns.Table.Bed.TYPE));
		}
		
		return bed;
	}
	
	private void populateBedArray(ArrayList<Bed> beds, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Bed bed = new Bed();
			bed.setBedId(rs.getInt(Columns.Table.Bed.BED_ID));
			bed.setSize(rs.getString(Columns.Table.Bed.SIZE));
			bed.setType(rs.getString(Columns.Table.Bed.TYPE));
			beds.add(bed);
		}
	}
}
