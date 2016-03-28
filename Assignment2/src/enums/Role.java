package enums;

public enum Role {
	Customer(1),
	Manager(2),
	Chief_Manager(3);
	
	private final int value;
	
	Role(int value){
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}