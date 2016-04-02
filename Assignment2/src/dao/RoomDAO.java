package dao;

import java.util.List;

import model.Hotel;
import model.Room;

public interface RoomDAO {
	List<Room> getAllRooms(Hotel hotel);
	
	Room getRoomById(int id);
	
	boolean createRoom(Room room);
	
	boolean updateRoom(Room room);
	
	boolean deleteRoom(Room room);
}
