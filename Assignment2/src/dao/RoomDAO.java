package dao;

import java.util.List;

import model.Room;
import sqlwhere.core.Select;
import sqlwhere.core.Where;

public interface RoomDAO {
	Room getRoomById(int id);
	
	Room getRoomByNo(String roomNo);
	
	Room getRoom(Where where);
	
	List<Room> getRooms(Where where);
	
	List<Room> getRooms(Where where, List<Select.OrderBy> orderBys);
	
	boolean createRoom(Room room);
	
	boolean updateRoom(Room room);
	
	boolean deleteRoom(Room room);
}
