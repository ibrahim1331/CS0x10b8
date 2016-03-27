package model;

public class LoginUser {
	private String username;
	private boolean isNormalUser = true;
	private boolean isManager = false;
	private boolean isChiefManager = false;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isNormalUser() {
		return isNormalUser;
	}
	public void setNormalUser(boolean isNormalUser) {
		this.isNormalUser = isNormalUser;
	}
	public boolean isManager() {
		return isManager;
	}
	public void setManager(boolean isManager) {
		this.isManager = isManager;
	}
	public boolean isChiefManager() {
		return isChiefManager;
	}
	public void setChiefManager(boolean isChiefManager) {
		this.isChiefManager = isChiefManager;
	}
}
