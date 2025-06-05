package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Like;
import com.ssafy.mvc.model.dto.SearchCondition;

public interface LikeService {
	
	public boolean addLike(int userId, int facilId);
	
	public boolean removeLike(int userId, int facilId);
	
	public List<Like> getLikes(int userId);

}
