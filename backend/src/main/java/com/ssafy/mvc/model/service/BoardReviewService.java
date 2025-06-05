package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.BoardReview;

public interface BoardReviewService {

	BoardReview readBoardReview(int id);

	boolean writeReview(BoardReview review);

	boolean modifyReview(BoardReview review);

	boolean removeReview(int id);

	List<BoardReview> selectAll(int boardId, int userId);

}
