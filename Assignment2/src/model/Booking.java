package model;

import java.sql.Timestamp;

public class Booking {
	private int bookingId;
	private int bookingNumber;
	private int customerId;
	private int roomId;
	private int noOfPeople;
	private Timestamp checkInDate;
	private Timestamp checkOutDate;
	private Timestamp bookingDate;
	private String purpose;
	private String pin;
	private boolean isCancelled;
	
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
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
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
	public boolean isCancelled() {
		return isCancelled;
	}
	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}
