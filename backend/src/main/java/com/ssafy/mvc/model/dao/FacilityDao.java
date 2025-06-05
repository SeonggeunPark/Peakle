package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Facility;
import com.ssafy.mvc.model.dto.FacilityDetail;
import com.ssafy.mvc.model.dto.FacilityFeature;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface FacilityDao {
	// 전체 암장 조회
	public List<Facility> selectAll();
	
	// 암장 1개 조회
	public Facility selectOne(int id);
	
	// 암장 1개 상세정보 조회
	public FacilityFeature selectOneDetail(int id);

	// 암장 수정
	public int updateFacility(Facility facility);
	
	// 암장 등록
	public int insertFacility(Facility facility);
	
	// 암장 삭제
	public int deleteFacility(int id);
	
	// 암장 검색
	public List<Facility> searchByKeyword(String keyword);
	
}
