package com.ssafy.mvc.model.dto;

public class FacilityFeature {
    private int difficulty; // 난이도
    private int bouldering; // 볼더링 여부
    private int leads; // 리드 여부
    private int footwash; // 발 세척 시설 여부
    private int bodywash; // 샤워 시설 여부
    private int facilityId; // 참조 - Facility ID
    
    public FacilityFeature() {
		// TODO Auto-generated constructor stub
	}

	public FacilityFeature(int difficulty, int bouldering, int lead, int footwash, int bodywash,
			int facilityId) {
		super();
		this.difficulty = difficulty;
		this.bouldering = bouldering;
		this.leads = lead;
		this.footwash = footwash;
		this.bodywash = bodywash;
		this.facilityId = facilityId;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public int getBouldering() {
		return bouldering;
	}

	public void setBouldering(int bouldering) {
		this.bouldering = bouldering;
	}

	public int getLead() {
		return leads;
	}

	public void setLead(int lead) {
		this.leads = lead;
	}

	public int getFootwash() {
		return footwash;
	}

	public void setFootwash(int footwash) {
		this.footwash = footwash;
	}

	public int getBodywash() {
		return bodywash;
	}

	public void setBodywash(int bodywash) {
		this.bodywash = bodywash;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	@Override
	public String toString() {
		return "FacilityFeature [difficulty=" + difficulty + ", bouldering=" + bouldering + ", lead="
				+ leads + ", footwash=" + footwash + ", bodywash=" + bodywash + ", facilityId=" + facilityId + "]";
	}
    
    
}
