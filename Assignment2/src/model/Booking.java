package model;

import java.sql.Timestamp;

public class Booking {
	private Integer bookingId;
	private Integer bookingNumber;
	private Integer customerId;
	private Integer roomId;
	private Integer noOfPeople;
	private Timestamp checkInDate;
	private Timestamp checkOutDate;
	private Timestamp bookingDate;
	private String purpose;
	private String pin;
	private Boolean isCancelled;
	
	public Integer getBookingId() {
		return bookingId;
	}
	
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	
	public Integer getBookingNumber() {
		return bookingNumber;
	}
	
	public void setBookingNumber(Integer bookingNumber) {
		this.bookingNumber = bookingNumber;
	}
	
	public Integer getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getNoOfPeople() {
		return noOfPeople;
	}

	public void setNoOfPeople(Integer noOfPeople) {
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

	public Timestamp getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}
