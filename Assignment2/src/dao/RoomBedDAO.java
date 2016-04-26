package dao;

import java.util.List;

import model.Room;
import model.RoomBed;
import sqlwhere.core.Where;

public interface RoomBedDAO {
	List<RoomBed> getAllRoomBeds();
	
	List<RoomBed> getRoomBeds(Room room);
	
	List<RoomBed> getRoomBeds(Where where);
	
	RoomBed getRoomBed(Where where);
	
	boolean createRoomBed(RoomBed roomBed);
	
	boolean deleteRoomBed(RoomBed roomBed);
}
