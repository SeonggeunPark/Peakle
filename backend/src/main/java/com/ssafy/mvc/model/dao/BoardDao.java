package com.ssafy.mvc.model.dao;

import java.util.List;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardSearchCondition;

public interface BoardDao {

	void updateViewCnt(int id);

	Board selectOne(int id);

	int insertBoard(Board board);

	int deleteBoard(int id);

	int updateBoard(Board board);

	List<Board> search(BoardSearchCondition searchCondition);

}
