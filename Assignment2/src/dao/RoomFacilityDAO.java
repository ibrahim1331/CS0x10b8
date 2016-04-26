package dao;

import java.util.List;

import model.Room;
import model.RoomFacility;
import sqlwhere.core.Where;

public interface RoomFacilityDAO {
	List<RoomFacility> getAllRoomFacilities();
	
	List<RoomFacility> getRoomFacilities(Room room);
	
	List<RoomFacility> getRoomFacilities(Where where);
	
	RoomFacility getRoomFacility(Where where);
	
	boolean createRoomFacility(RoomFacility roomFacility);
	
	boolean deleteRoomFacility(RoomFacility roomFacility);
}
