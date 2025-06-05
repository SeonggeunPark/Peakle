package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardSearchCondition;

public interface BoardService {

	List<Board> searchBoard(BoardSearchCondition condition);

	Board readBoard(int id);

	boolean writeBoard(Board board);

	boolean modifyBoard(Board board);

	boolean removeBoard(int id);

}
