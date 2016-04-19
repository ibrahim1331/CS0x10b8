package dao;

import java.util.List;

import model.Search;
import sqlwhere.core.Where;

public interface SearchDAO {
	List<Search> search(Where where);
}
