package model;

import java.util.List;

public class BookingMeta {
	private String pin;
	private List<TempBooking> failed;
	private boolean allFailed;
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public List<TempBooking> getFailed() {
		return failed;
	}
	public void setFailed(List<TempBooking> failed) {
		this.failed = failed;
	}
	public boolean isAllFailed() {
		return allFailed;
	}
	public void setAllFailed(boolean allFailed) {
		this.allFailed = allFailed;
	}
}
