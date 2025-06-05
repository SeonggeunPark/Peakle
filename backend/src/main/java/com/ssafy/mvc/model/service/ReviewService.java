package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Review;

public interface ReviewService {

	List<Review> selectAll(int facilityId, int userId);
	
	List<Review> selectAllFacilReview(int facilityId);

	boolean writeReview(Review review);

	Review selectOne(int id);

	boolean modifyReview(Review review);

	boolean removeReview(int id);

}
