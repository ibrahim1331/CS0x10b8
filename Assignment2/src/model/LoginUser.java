package model;

public class LoginUser extends User{
	private boolean isNormalUser = true;
	private boolean isManager = false;
	private boolean isChiefManager = false;
	
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
