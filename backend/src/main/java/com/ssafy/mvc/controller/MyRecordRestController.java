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

import com.ssafy.mvc.model.dto.MyRecord;
import com.ssafy.mvc.model.service.MyRecordService;

@RestController
@RequestMapping("/myrecord")
@CrossOrigin("http://localhost:5173")
public class MyRecordRestController {

	private final MyRecordService myRecordService;
	
	public MyRecordRestController(MyRecordService myRecordService) {
		this.myRecordService = myRecordService;
	}
	
	// 암장, 유저 id 기반 모든 기록 조회
	@GetMapping("/{facilityId}/{userId}")
	public ResponseEntity<?> list(@PathVariable("facilityId") int facilityId, @PathVariable("userId") int userId) {
		List<MyRecord> list = myRecordService.selectAll(facilityId, userId);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MyRecord>>(list, HttpStatus.OK);
	}
	
	// 기간, 유저 id 기반 모든 기록 조회
	@GetMapping("/date/{userId}/{date}")
	public ResponseEntity<?> dateList(@PathVariable("userId") int userId, @PathVariable("date") int date) {
		List<MyRecord> list = myRecordService.selectByDate(userId, date);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MyRecord>>(list, HttpStatus.OK);
	}
	
	// 리뷰 작성
	@PostMapping("")
	public ResponseEntity<?> write(@RequestBody MyRecord myRecord) {
		if (myRecordService.writeMyRecord(myRecord)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("MyRecord added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add myRecord");
	}
	
	// 리뷰 1개 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> myRecord(@PathVariable("id") int id) {
		MyRecord myRecord = myRecordService.selectOne(id);
		if (myRecord == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<MyRecord>(myRecord, HttpStatus.OK);
	}
	
	// 리뷰 수정
	@PutMapping("")
	public ResponseEntity<String> update(@RequestBody MyRecord myRecord) {
		if (myRecordService.modifyMyRecord(myRecord)) {
			return ResponseEntity.ok("MyRecord updated successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update MyRecord");
	}
	
	// 리뷰 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		if (myRecordService.removeMyRecord(id)) {
			return ResponseEntity.status(HttpStatus.OK).body("MyRecord deleted successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete MyRecord");
	}
}
