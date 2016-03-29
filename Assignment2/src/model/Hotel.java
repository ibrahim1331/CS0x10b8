package model;

public class Hotel {
	private int hotel_id;
	private String name;
	private String location;
	private String address;
	private int no_of_rooms;
	private int rating;
	
	public int getHotel_id() {
		return hotel_id;
	}
	public String getName() {
		return name;
	}
	public String getLocation() {
		return location;
	}
	public String getAddress() {
		return address;
	}
	public int getNo_of_rooms() {
		return no_of_rooms;
	}
	public int getRating() {
		return rating;
	}
	
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setNo_of_rooms(int no_of_rooms) {
		this.no_of_rooms = no_of_rooms;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
		
}
