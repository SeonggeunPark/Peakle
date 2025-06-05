package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Like;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface LikeDao {
	// 찜 등록
	public boolean insertLike(int userId, int facilId);
	
	// 찜 삭제
	public boolean deleteLike(int userId, int facilId);
	
	// 찜 목록 보기
	public List<Like> selectUserLike(int id);
	
}
