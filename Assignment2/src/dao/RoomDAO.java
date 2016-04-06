package dao;

import java.util.List;

import model.Hotel;
import model.Room;

public interface RoomDAO {
	List<Room> getAllRooms();
	
	List<Room> getAllRoomsofHotel(Hotel hotel);
	
	Room getRoomById(int id);
	
	List<Room> getAllRecommendedRooms();
	
	List<Room> getRecommendedRooms(Hotel hotel);
	
	List<Room> getRooms(String filter, String orderBy);
	
	List<Room> getBedrooms(Room room);
	
	boolean createRoom(Room room);
	
	boolean updateRoom(Room room);
	
	boolean deleteRoom(Room room);
}