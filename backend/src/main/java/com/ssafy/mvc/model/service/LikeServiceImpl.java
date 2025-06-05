package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.LikeDao;
import com.ssafy.mvc.model.dto.Like;
import com.ssafy.mvc.model.dto.SearchCondition;

@Service
public class LikeServiceImpl implements LikeService{
	
	@Autowired
	LikeDao likeDao;
	
	@Override
	public boolean addLike(int userId, int facilId) {
		return likeDao.insertLike(userId, facilId);
	}

	@Override
	public boolean removeLike(int userId, int facilId) {
		return likeDao.deleteLike(userId, facilId);
	}

	@Override
	public List<Like> getLikes(int userId) {
		return likeDao.selectUserLike(userId);
	}
	
}
