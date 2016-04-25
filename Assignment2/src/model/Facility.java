package model;

public class Facility {
	private Integer facilityId;
	private String name;
	private String category;
	
	public Integer getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(Integer facilityId) {
		this.facilityId = facilityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
