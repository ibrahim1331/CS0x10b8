package dao;

import java.util.List;

import model.Facility;
import sqlwhere.core.Where;

public interface FacilityDAO {
	Facility getFacility(int facilityId);
	
	Facility getFacility(String name);
	
	Facility getFacility(Where where);
	
	List<Facility> getAllHotelFacilities();
	
	List<Facility> getAllRoomFacilities();
	
	List<Facility> getFacilities(String category);
	
	List<Facility> getFacilities(Where where);
}
