package com.ssafy.mvc.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.mvc.model.dto.Crowd;
import com.ssafy.mvc.model.dto.Facility;
import com.ssafy.mvc.model.dto.FacilityDetail;
import com.ssafy.mvc.model.dto.Review;
import com.ssafy.mvc.model.service.CrowdService;
import com.ssafy.mvc.model.service.FacilityService;

@RestController
@RequestMapping("/facility")
@CrossOrigin("http://localhost:5173")
public class FacilityRestController {
	// 의존성 주입
	@Autowired
	FacilityService facilityService;
	@Autowired
	CrowdService crowdService;

	// 전체 조회
	@GetMapping("")
	public ResponseEntity<List<Facility>> list() {
		List<Facility> list = facilityService.getFacilityList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// 암장 1개 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> detail(@PathVariable("id") int id) {
		Facility facility = facilityService.getFacility(id);
		if (facility != null) {
			return new ResponseEntity<>(facility, HttpStatus.OK);
		}
		return new ResponseEntity<>("failed to get Facility", HttpStatus.BAD_REQUEST);
	}
	
	// 암장 1개 상세조회
	@GetMapping("/detail/{id}")
	public ResponseEntity<?> feature(@PathVariable("id") int id) {
		FacilityDetail facilityDetail = facilityService.getFacilityDetail(id);

		if (facilityDetail != null) {
			return new ResponseEntity<>(facilityDetail, HttpStatus.OK);
		}
		return new ResponseEntity<>("failed to get Facility", HttpStatus.BAD_REQUEST);
	}

	// 암장 수정
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable("id") int id, @RequestBody Facility facility) {
		facility.setId(id);
		facility.setUpdated(null); // 현재시간으로 수정일 바꾸기 위해 null로 변경
		boolean result = facilityService.modifyFacility(facility);

		if (result) {
			return new ResponseEntity<Void>(HttpStatus.OK); //
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST); //
	}

	// 암장 등록
	@PostMapping("")
	public ResponseEntity<?> regist(@RequestBody Facility facility) {
		// 시설 등록 요청
		boolean result = facilityService.registFacility(facility);

		int id = facility.getId();

		// 등록완료되면 해당 시설의 상세페이지로 이동하기 위해 id를 반환
		if (result)
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		return new ResponseEntity<>("failed to regist facility", HttpStatus.BAD_REQUEST);
	}
	
	// 암장 검색
	@PostMapping("/search")
	public ResponseEntity<?> searchFacility(@RequestBody Map<String, String> body) {
		String keyword = body.get("keyword");
		List<Facility> list = facilityService.searchFacility(keyword);
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Facility>>(list, HttpStatus.OK);
	}

	// 1시간마다 혼잡도 계산 메서드
	Map<Integer, Double> crowdMap = new HashMap<>(); // 혼잡도 데이터(1시간마다 갱신)

	// 혼잡도 계산 & 갱신 (5분마다)
	@Scheduled(fixedDelay = 1000*60*3)
	public void updateCrowdList() {
		crowdMap = crowdService.getCrowds();
	}

	// 혼잡도 반환
	@GetMapping("/crowd")
	public ResponseEntity<?> getCrowdList() {
		return new ResponseEntity<>(crowdMap, HttpStatus.OK);
	}
	
	// 혼잡도 등록
	@PostMapping("/crowd")
	public ResponseEntity<?> writeCroewd(@RequestBody Crowd crowd) {
		if (crowdService.writeCrowd(crowd)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Crowd added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add crowd");
	}
}
