package service;

import java.sql.Timestamp;
import java.util.List;

import dao.HotelDAO;
import dao.HotelDAOImpl;
import model.Search;
import sqlwhere.core.Where;
import sqlwhere.operators.compare.Like;
import utils.Columns;

public class SearchService {
	HotelDAO hotelDAO = new HotelDAOImpl();
	
	public List<Search> search(String name, Timestamp fromDate, Timestamp toDate){
		Where where = new Where(new Like(Columns.View.SearchView.HOTEL_NAME, "%"+name+"%"));
		if(fromDate!=null){
			where.and(new Equal(Columns.View.SearchView.))
		}
	}
}
