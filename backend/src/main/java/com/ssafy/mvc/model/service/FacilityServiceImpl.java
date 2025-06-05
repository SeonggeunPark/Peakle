package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.FacilityDao;
import com.ssafy.mvc.model.dto.Facility;
import com.ssafy.mvc.model.dto.FacilityDetail;
import com.ssafy.mvc.model.dto.FacilityFeature;
import com.ssafy.mvc.model.dto.SearchCondition;

@Service
public class FacilityServiceImpl implements FacilityService{
	
	@Autowired
	FacilityDao facilityDao;
	// 암장리스트 조회
	@Override
	public List<Facility> getFacilityList() {
		return facilityDao.selectAll();
	}
	
	// 암장 1개 조회
	@Override
	public Facility getFacility(int id) {
		return facilityDao.selectOne(id);
	}

	// 암장 1개 상세조회
	@Override
	public FacilityDetail getFacilityDetail(int id) {
		Facility facility = facilityDao.selectOne(id);
		FacilityFeature facilityFeature = facilityDao.selectOneDetail(id);
		
		FacilityDetail facilityDetail = new FacilityDetail(facility, facilityFeature);
		
		return facilityDetail;
	}
	
	// 암장 정보 수정 (외래키 처리 필요)
	@Override
	public boolean modifyFacility(Facility facility) {
		if (facilityDao.updateFacility(facility)>=1) {
			return true;
		}
		return false;
	}
	
	// 암장 신규 등록
	@Override
	public boolean registFacility(Facility facility) {
		if (facilityDao.insertFacility(facility)>=1) {
			return true;
		}
		return false;
	}

	// 암장 제거 (외래키 처리 필요)
	@Override
	public boolean removeFacility(int id) {
		if (facilityDao.deleteFacility(id)>=1) {
			return true;
		}
		return false;
	}
	
	// 암장 검색
	@Override
	public List<Facility> searchFacility(String keyword) {
		return facilityDao.searchByKeyword(keyword);
	}

}
