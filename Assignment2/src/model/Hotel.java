package model;

import java.sql.Timestamp;

public class Hotel {
	private int hotelId;
	private String name;
	private int location;
	private String address;
	private String description;
	private int noOfRooms;
	private float rating;
	private Timestamp dateJoined;
	
	public int getHotelId() {
		return hotelId;
	}
	public String getName() {
		return name;
	}
	public int getLocation() {
		return location;
	}
	public String getAddress() {
		return address;
	}
	public int getNoOfRooms() {
		return noOfRooms;
	}
	public float getRating() {
		return rating;
	}
	
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLocation(int location) {
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getDateJoined() {
		return dateJoined;
	}
	public void setDateJoined(Timestamp dateJoined) {
		this.dateJoined = dateJoined;
	}
		
}
