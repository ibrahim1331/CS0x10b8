package model;

import java.sql.Timestamp;

public class BookingView {
	private int bookingId;
	private int bookingNumber;
	private int customerId;
	private int noOfPeople;
	private Timestamp checkInDate;
	private Timestamp checkOutDate;
	private String purpose;
	private Timestamp bookingDate;
	private String pin;
	private boolean isCancelled;
	private int hotelId;
	private String hotelName;
	private String hotelAddress;
	private String hotelDescription;
	private int noOfRooms;
	private float hotelRating;
	private Timestamp hotelDateJoined;
	private int roomId;
	private String roomNo;
	private String roomType;
	private int roomPrice;
	private int roomCapacity;
	private int roomSize;
	private int belongsTo;
	private int discount;
	private int recommended;
	private int locationId;
	private String locationName;
	private String country;
	
	public int getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	
	public int getBookingNumber() {
		return bookingNumber;
	}
	
	public void setBookingNumber(int bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public int getNoOfPeople() {
		return noOfPeople;
	}
	
	public void setNoOfPeople(int noOfPeople) {
		this.noOfPeople = noOfPeople;
	}
	
	public Timestamp getCheckInDate() {
		return checkInDate;
	}
	
	public void setCheckInDate(Timestamp checkInDate) {
		this.checkInDate = checkInDate;
	}
	
	public Timestamp getCheckOutDate() {
		return checkOutDate;
	}
	
	public void setCheckOutDate(Timestamp checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	public String getPurpose() {
		return purpose;
	}
	
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public Timestamp getBookingDate() {
		return bookingDate;
	}
	
	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
	}
	
	public String getPin() {
		return pin;
	}
	
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	public int getHotelId() {
		return hotelId;
	}
	
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	
	public String getHotelName() {
		return hotelName;
	}
	
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	public String getHotelAddress() {
		return hotelAddress;
	}
	
	public void setHotelAddress(String hotelAddress) {
		this.hotelAddress = hotelAddress;
	}
	
	public String getHotelDescription() {
		return hotelDescription;
	}
	
	public void setHotelDescription(String hotelDescription) {
		this.hotelDescription = hotelDescription;
	}
	
	public int getNoOfRooms() {
		return noOfRooms;
	}
	
	public void setNoOfRooms(int noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	
	public float getHotelRating() {
		return hotelRating;
	}
	
	public void setHotelRating(float hotelRating) {
		this.hotelRating = hotelRating;
	}
	
	public Timestamp getHotelDateJoined() {
		return hotelDateJoined;
	}
	
	public void setHotelDateJoined(Timestamp hotelDateJoined) {
		this.hotelDateJoined = hotelDateJoined;
	}
	
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
	
	public String getRoomType() {
		return roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	
	public int getRoomPrice() {
		return roomPrice;
	}
	
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}
	
	public int getRoomCapacity() {
		return roomCapacity;
	}
	
	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}
	
	public int getRoomSize() {
		return roomSize;
	}
	
	public void setRoomSize(int roomSize) {
		this.roomSize = roomSize;
	}
	
	public int getBelongsTo() {
		return belongsTo;
	}
	
	public void setBelongsTo(int belongsTo) {
		this.belongsTo = belongsTo;
	}
	
	public int getDiscount() {
		return discount;
	}
	
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	
	public int getRecommended() {
		return recommended;
	}
	
	public void setRecommended(int recommended) {
		this.recommended = recommended;
	}
	
	public int getLocationId() {
		return locationId;
	}
	
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
	public String getLocationName() {
		return locationName;
	}
	
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}
