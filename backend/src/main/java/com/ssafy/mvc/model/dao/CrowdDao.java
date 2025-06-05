package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Crowd;

public interface CrowdDao {
	// 1시간 이내 혼잡도 평가 자료 반환
	public List<Crowd> getCrowdList();

	public int insertCrowd(Crowd crowd);

}
