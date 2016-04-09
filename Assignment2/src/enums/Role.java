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
	
	public static Role fromInt(int i){
		for(Role r : Role.values()){
			if(r.value == i) return r;
		}
		return null;
	}
}