package com.ssafy.mvc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.Board;
import com.ssafy.mvc.model.dto.BoardReview;
import com.ssafy.mvc.model.service.BoardReviewService;

@RestController
@RequestMapping("/boardreview")
@CrossOrigin("http://localhost:5173")
public class BoardReviewRestController {
	
	private final BoardReviewService boardReviewService;
	
	public BoardReviewRestController(BoardReviewService boardReviewService) {
		this.boardReviewService = boardReviewService;
	}
	
	// 게시글 모든 리뷰 조회
	@GetMapping("/{boardId}/{userId}")
	public ResponseEntity<?> list(@PathVariable("boardId") int boardId, @PathVariable("userId") int userId) {
		
		List<BoardReview> list = boardReviewService.selectAll(boardId, userId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BoardReview>>(list, HttpStatus.OK);
	}
	
	// 게시글 댓글 상세 보기
	@GetMapping("/{id}")
	public ResponseEntity<BoardReview> detail(@PathVariable int id) {
		BoardReview review = boardReviewService.readBoardReview(id);
		if (review != null)
			return new ResponseEntity<BoardReview>(review, HttpStatus.OK);
		return new ResponseEntity<BoardReview>(HttpStatus.NOT_FOUND);
	}

	// 댓글 등록 (JSON형태로 보낸다)
	@PostMapping("")
	public ResponseEntity<String> write(@RequestBody BoardReview review) {
		if (boardReviewService.writeReview(review)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add review");
	}

	// 댓글 수정 (JSON형태로 보낸다)
	@PutMapping("")
	public ResponseEntity<String> update(@RequestBody BoardReview review) {
		System.out.println(review);
		if (boardReviewService.modifyReview(review))
			return ResponseEntity.ok("Review updated successfully");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update review");
	}

	// 댓글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		if (boardReviewService.removeReview(id))
			return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Review");
	}
}
