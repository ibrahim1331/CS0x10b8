package model;

import java.io.Serializable;

public class TempBooking extends Booking{
	private static final long serialVersionUID = 1L;
	private String roomNo;
	private String hotelName;

	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
}
