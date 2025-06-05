package com.ssafy.mvc.model.dto;

public class FacilityDetail {
	private Facility facility;
	private FacilityFeature facilityFeature;
	
	// 기본 생성자
	public FacilityDetail() {
	}
	
	// 추가 생성자
	public FacilityDetail(Facility facility, FacilityFeature facilityFeature) {
		super();
		this.facility = facility;
		this.facilityFeature = facilityFeature;
	}
	
	// Getter & Setter
	public Facility getFacility() {
		return facility;
	}
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	public FacilityFeature getFacilityFeature() {
		return facilityFeature;
	}
	public void setFacilityFeature(FacilityFeature facilityFeature) {
		this.facilityFeature = facilityFeature;
	}
	
	// toString
	@Override
	public String toString() {
		return "FacilityDetail [facility=" + facility + ", facilityFeature=" + facilityFeature + "]";
	}
}
