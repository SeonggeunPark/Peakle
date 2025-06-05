package com.ssafy.mvc.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.mvc.model.dao.BoardReviewDao;
import com.ssafy.mvc.model.dto.BoardReview;

@Service
public class BoardReviewServiceImpl implements BoardReviewService{
	
	private final BoardReviewDao boardReviewDao;
	
	public BoardReviewServiceImpl(BoardReviewDao boardReviewDao) {
		this.boardReviewDao = boardReviewDao;
	}
	
	@Override
	public List<BoardReview> selectAll(int boardId, int userId) {
		return boardReviewDao.selectAll(boardId, userId);
	}

	@Override
	public BoardReview readBoardReview(int id) {
		return boardReviewDao.selectOne(id);
	}
	
	@Transactional
	@Override
	public boolean writeReview(BoardReview review) {
		return boardReviewDao.insertReview(review) == 1;
	}
	
	@Transactional
	@Override
	public boolean modifyReview(BoardReview review) {
		return boardReviewDao.updateReview(review) == 1;
	}

	@Override
	public boolean removeReview(int id) {
		return boardReviewDao.deleteReview(id) == 1;
	}
	
}
