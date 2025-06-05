package com.ssafy.mvc.model.dto;

public class Like {
    private int userId; // 참조 - User ID
    private int facilityId; // 참조 - Facility ID
    
    public Like() {
		// TODO Auto-generated constructor stub
	}
    
    public Like(int userId, int facilityId) {
    	super();
    	this.userId = userId;
    	this.facilityId = facilityId;
    }
    
	@Override
	public String toString() {
		return "Like [userId=" + userId + ", facilityId=" + facilityId + "]";
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public int getFacilityId() {
		return facilityId;
	}
	
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
    
}
