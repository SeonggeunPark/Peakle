package com.ssafy.mvc.controller;

import com.ssafy.mvc.model.dto.Crowd;
import com.ssafy.mvc.model.dto.Facility;
import com.ssafy.mvc.model.dto.FacilityDetail;
import com.ssafy.mvc.model.service.CrowdService;
import com.ssafy.mvc.model.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facility")
//@CrossOrigin("http://localhost:5173")
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

	// 혼잡도 반환 (캐싱)
	@GetMapping("/crowd")
	public ResponseEntity<?> getCrowdList() {

        Map<Integer, Double> crowdMap = crowdService.getCrowdSnapshot();
        return new ResponseEntity<>(crowdMap, HttpStatus.OK);
	}

    // 혼잡도 반환 (테스트용)
	@GetMapping("/crowd-calc")
	public ResponseEntity<?> getCrowdListNow() {
        Map<Integer, Double> nowCrowdMap = crowdService.getCrowds();
		return new ResponseEntity<>(nowCrowdMap, HttpStatus.OK);
	}

	// 혼잡도 등록
	@PostMapping("/crowd")
	public ResponseEntity<?> writeCrowd(@RequestBody Crowd crowd) {
		if (crowdService.writeCrowd(crowd)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Crowd added successfully");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add crowd");
	}
}
