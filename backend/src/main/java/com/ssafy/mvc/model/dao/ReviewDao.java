package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Review;

public interface ReviewDao {

	List<Review> selectAll(int facilityId, int userId);

	List<Review> selectAllFacilReview(int facilityId);

	int insertReview(Review review);

	Review selectOne(int id);

	int updateReview(Review review);

	int deleteReview(int id);


}
