package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.NamingException;

import model.Search;
import sqlwhere.core.Select;
import sqlwhere.core.Where;
import utils.DBHelper;

public class SearchDAOImpl implements SearchDAO {

	@Override
	public List<Search> search(Where where) {
		ArrayList<Search> result = new ArrayList<>();
		
		try{
			Connection con = DBHelper.getConnection();
	        Select select = new Select("*").from("searchView").where(where);
	        
	        Logger.getLogger(this.getClass().getName()).log(Level.INFO, select.getStatement());
	        System.out.println(select.getIndexMap());
	        PreparedStatement pstmt = con.prepareStatement(select.getStatement(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        for(Entry<Integer, Object> es: select.getIndexMap().entrySet()){
	        	pstmt.setObject(es.getKey(), es.getValue());
	        }
	        ResultSet rs = pstmt.executeQuery();
	        
	        this.populateSearchArray(result, rs);
	        
	        DBHelper.close(con);
            DBHelper.close(pstmt);
            DBHelper.close(rs);
		}catch(SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	private void populateSearchArray(ArrayList<Search> result, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Search searchResult = new Search();
			searchResult.setHotelId(rs.getInt("hotel_id"));
			searchResult.setHotelName(rs.getString("hotel_name"));
			searchResult.setHotelAddress(rs.getString("address"));
			searchResult.setNoOfRooms(rs.getInt("no_of_rooms"));
			searchResult.setHotelRating(rs.getInt("rating"));
			searchResult.setHotelDescription(rs.getString("description"));
			searchResult.setHotelDateJoined(rs.getTimestamp("join_date"));
			searchResult.setRoomId(rs.getInt("room_id"));
			searchResult.setRoomType(rs.getString("type"));
			searchResult.setRoomPrice(rs.getInt("room_price"));
			searchResult.setRoomCapacity(rs.getInt("room_capacity"));
			searchResult.setRoomSize(rs.getInt("room_size"));
			searchResult.setRoomNo(rs.getString("room_no"));
			searchResult.setBelongsTo(rs.getInt("belongs_to"));
			searchResult.setDiscount(rs.getInt("discount"));
			searchResult.setRecommended(rs.getInt("recommended"));
			searchResult.setLocationId(rs.getInt("location_id"));
			searchResult.setLocationName(rs.getString("location_name"));
			searchResult.setCountry(rs.getString("country"));
			result.add(searchResult);
		}
	}
}
