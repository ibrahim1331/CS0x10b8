package dao;

import java.util.List;

import model.Bed;
import sqlwhere.core.Where;

public interface BedDAO {
	List<Bed> getAllBeds();
	
	Bed getBed(Integer bedId);
	
	Bed getBed(Where where);
	
	List<Bed> getBeds(Where where);
}
