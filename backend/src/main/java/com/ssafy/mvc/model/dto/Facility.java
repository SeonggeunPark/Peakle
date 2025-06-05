package com.ssafy.mvc.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Facility {
    private int id;
    private String name;
    private double lat; // 위도
    private double lng; // 경도
    private String address;
    private String phoneNumber;	// (-) 생략
    private String img;
    private int isClosed; // 1: 열림, 0: 닫힘
    private Date created;
    private Date updated;
    private int userId;	// 시설 관리자 userId와 매칭
    
    public Facility() {
		// TODO Auto-generated constructor stub
	}

	public Facility(int id, String name, double lat, double lng, String address, String phoneNumber, String img,
			int isClosed, Date created, Date updated, int userId) {
		super();
		this.id = id;
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.img = img;
		this.isClosed = isClosed;
		this.created = created;
		this.updated = updated;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(int isClosed) {
		this.isClosed = isClosed;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Facility [id=" + id + ", name=" + name + ", lat=" + lat + ", lng=" + lng + ", address=" + address
				+ ", phoneNumber=" + phoneNumber + ", img=" + img + ", isClosed=" + isClosed + ", created=" + created
				+ ", updated=" + updated + ", userId=" + userId + "]";
	}
    
    

//	@Override
//	public String toString() {
//		return "Facility [id=" + id + ", name=" + name + ", lat=" + lat + ", lng=" + lng + ", address=" + address
//				+ ", phoneNumber=" + phoneNumber + ", isClosed=" + isClosed + ", created=" + created + ", updated="
//				+ updated + ", userId=" + userId + "]";
//	}
//
//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public double getLat() {
//		return lat;
//	}
//
//	public void setLat(double lat) {
//		this.lat = lat;
//	}
//
//	public double getLng() {
//		return lng;
//	}
//
//	public void setLng(double lng) {
//		this.lng = lng;
//	}
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getPhoneNumber() {
//		return phoneNumber;
//	}
//
//	public void setPhoneNumber(String phoneNumber) {
//		this.phoneNumber = phoneNumber;
//	}
//
//	public int getIsClosed() {
//		return isClosed;
//	}
//
//	public void setIsClosed(int isClosed) {
//		this.isClosed = isClosed;
//	}
//
//	public Date getCreated() {
//		return created;
//	}
//
//	public void setCreated(Date created) {
//		this.created = created;
//	}
//
//	public Date getUpdated() {
//		return updated;
//	}
//
//	public void setUpdated(Date updated) {
//		this.updated = updated;
//	}
//
//	public int getUserId() {
//		return userId;
//	}
//
//	public void setUserId(int userId) {
//		this.userId = userId;
//	}
//
//	public Facility(int id, String name, double lat, double lng, String address, String phoneNumber, int isClosed,
//			Date created, Date updated, int userId) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.lat = lat;
//		this.lng = lng;
//		this.address = address;
//		this.phoneNumber = phoneNumber;
//		this.isClosed = isClosed;
//		this.created = created;
//		this.updated = updated;
//		this.userId = userId;
//	}
}
