package com.ssafy.mvc.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Crowd {
    private int id;
    private int rating; // 혼잡도 (1~3)
    private int manager; // 유저의 id와 맞춰야 함. 1-매니저 2-일반사용자
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date created;
    private int facilityId; // 참조 - Facility ID
    private int userId; // 참조 - User ID
    
    
    public Crowd(int id, int rating, int manager, Date created, int facilityId, int userId) {
    	super();
    	this.id = id;
    	this.rating = rating;
    	this.manager = manager;
    	this.created = created;
    	this.facilityId = facilityId;
    	this.userId = userId;
    }
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public int getManager() {
		return manager;
	}
	public void setManager(int manager) {
		this.manager = manager;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Crowd [id=" + id + ", rating=" + rating + ", manager=" + manager + ", created=" + created
				+ ", facilityId=" + facilityId + ", userId=" + userId + "]";
	}
    
}
