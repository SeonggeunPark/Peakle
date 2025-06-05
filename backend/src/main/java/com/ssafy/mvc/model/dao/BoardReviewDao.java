package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardReview;

public interface BoardReviewDao {

	List<BoardReview> selectAll(int boardId, int userId);

	BoardReview selectOne(int id);

	int insertReview(BoardReview review);

	int updateReview(BoardReview review);

	int deleteReview(int id);


}
