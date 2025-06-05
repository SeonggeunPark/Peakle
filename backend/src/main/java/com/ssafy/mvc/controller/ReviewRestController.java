package com.ssafy.mvc.controller;

import java.util.Arrays;
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

import com.ssafy.mvc.model.dto.Review;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/review")
@CrossOrigin("http://localhost:5173")
public class ReviewRestController {
	
	private final ReviewService reviewService;
	
	public ReviewRestController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}
	
	// 암장, 유저 id 기반 모든 리뷰 조회
	@GetMapping("/{facilityId}/{userId}")
	public ResponseEntity<?> list(@PathVariable("facilityId") int facilityId, @PathVariable("userId") int userId) {
		List<Review> list = reviewService.selectAll(facilityId, userId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
	}
	
	// 암장 id 기반 모든 리뷰 조회
	@GetMapping("/{facilityId}")
	public ResponseEntity<?> list(@PathVariable("facilityId") int facilityId) {
		List<Review> list = reviewService.selectAllFacilReview(facilityId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Review>>(list, HttpStatus.OK);
	}
	
	// 리뷰 작성
	@PostMapping("")
	public ResponseEntity<?> write(@RequestBody Review review) {
		if (reviewService.writeReview(review)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add review");
	}
	
	// 리뷰 수정
	@PutMapping("")
	public ResponseEntity<String> update(@RequestBody Review review) {
		if (reviewService.modifyReview(review)) {
			return ResponseEntity.ok("Review updated successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Review");
	}
	
	// 리뷰 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		if (reviewService.removeReview(id)) {
			return ResponseEntity.status(HttpStatus.OK).body("Review deleted successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete Review");
	}
}
