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
import utils.Columns;

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
		} catch(SQLException ex){
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		} catch (NamingException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		}
		
		return result;
	}
	
	private void populateSearchArray(ArrayList<Search> result, ResultSet rs) throws SQLException {
		while(rs != null && rs.next()){
			Search searchResult = new Search();
			searchResult.setHotelId(rs.getInt(Columns.View.SearchView.HOTEL_ID));
			searchResult.setHotelName(rs.getString(Columns.View.SearchView.HOTEL_NAME));
			searchResult.setHotelAddress(rs.getString(Columns.View.SearchView.ADDRESS));
			searchResult.setNoOfRooms(rs.getInt(Columns.View.SearchView.NO_OF_ROOMS));
			searchResult.setHotelRating(rs.getFloat(Columns.View.SearchView.RATING));
			searchResult.setHotelDescription(rs.getString(Columns.View.SearchView.DESCRIPTION));
			searchResult.setHotelDateJoined(rs.getTimestamp(Columns.View.SearchView.JOIN_DATE));
			searchResult.setRoomId(rs.getInt(Columns.View.SearchView.ROOM_ID));
			searchResult.setRoomType(rs.getString(Columns.View.SearchView.TYPE));
			searchResult.setRoomPrice(rs.getInt(Columns.View.SearchView.ROOM_PRICE));
			searchResult.setRoomCapacity(rs.getInt(Columns.View.SearchView.ROOM_CAPACITY));
			searchResult.setRoomSize(rs.getInt(Columns.View.SearchView.ROOM_SIZE));
			searchResult.setRoomNo(rs.getString(Columns.View.SearchView.ROOM_NO));
			searchResult.setDiscount(rs.getInt(Columns.View.SearchView.DISCOUNT));
			searchResult.setRecommended(rs.getInt(Columns.View.SearchView.RECOMMENDED));
			searchResult.setLocationId(rs.getInt(Columns.View.SearchView.LOCATION_ID));
			searchResult.setLocationName(rs.getString(Columns.View.SearchView.LOCATION_NAME));
			searchResult.setCountry(rs.getString(Columns.View.SearchView.COUNTRY));
			result.add(searchResult);
		}
	}
}
