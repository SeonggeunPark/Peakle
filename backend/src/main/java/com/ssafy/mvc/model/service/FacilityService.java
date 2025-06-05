package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Facility;
import com.ssafy.mvc.model.dto.FacilityDetail;
import com.ssafy.mvc.model.dto.FacilityFeature;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface FacilityService {
	// 전체 암장 조회
	public List<Facility> getFacilityList();
	
	// 1개 암장만 조회
	public Facility getFacility(int id);
	
	// 암장 1개 상세정보 조회
	public FacilityDetail getFacilityDetail(int id);
	
	// 암장 수정
	public boolean modifyFacility(Facility facility);
	
	// 암장 등록
	public boolean registFacility(Facility facility);
	
	// 암장 삭제
	public boolean removeFacility(int id);

	public List<Facility> searchFacility(String keyword);
}
