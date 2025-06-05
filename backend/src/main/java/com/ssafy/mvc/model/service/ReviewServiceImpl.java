package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.ReviewDao;
import com.ssafy.mvc.model.dto.Review;

@Service
public class ReviewServiceImpl implements ReviewService{
	
	private final ReviewDao reviewDao;
	
	public ReviewServiceImpl(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	// 암장 id, 유저 id 기반 모든 리뷰 조회
	@Override
	public List<Review> selectAll(int facilityId, int userId) {
		return reviewDao.selectAll(facilityId,userId);
	}

	// 암장 id 기반 모든 리뷰 조회
	@Override
	public List<Review> selectAllFacilReview(int facilityId) {
		return reviewDao.selectAllFacilReview(facilityId);
	}
	
	@Override
	public boolean writeReview(Review review) {
		return reviewDao.insertReview(review) == 1;
	}

	@Override
	public Review selectOne(int id) {
		return reviewDao.selectOne(id);
	}

	@Override
	public boolean modifyReview(Review review) {
		return reviewDao.updateReview(review) == 1;
	}

	@Override
	public boolean removeReview(int id) {
		return reviewDao.deleteReview(id) == 1;
	}

	
	
	
}
