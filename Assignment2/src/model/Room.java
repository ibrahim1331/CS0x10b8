package model;

public class Room {
	private int roomId;
	private String roomNo;
	private String type;
	private int price;
	private int hotelId;
	private int capacity;
	private int size;
	private int discount;
	private int belongs_to;
	private int recommended;
	
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getBelongs_to() {
		return belongs_to;
	}
	public void setBelongs_to(int belongs_to) {
		this.belongs_to = belongs_to;
	}
	public int getRecommended() {
		return recommended;
	}
	public void setRecommended(int recommended) {
		this.recommended = recommended;
	}
	
}
