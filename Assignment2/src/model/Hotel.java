package model;

public class Hotel {
	private int hotel_id;
	private String name;
	private String location;
	private String address;
	private int noOfRooms;
	private int rating;
	
	public int getHotelId() {
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
	public int getNoOfRooms() {
		return noOfRooms;
	}
	public int getRating() {
		return rating;
	}
	
	public void setHotelId(int hotel_id) {
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
	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
		
}
