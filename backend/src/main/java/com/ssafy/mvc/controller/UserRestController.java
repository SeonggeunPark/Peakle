package com.ssafy.mvc.controller;

import java.io.Console;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ssafy.mvc.jwt.JwtUtil;
import com.ssafy.mvc.model.dto.Facility;
import com.ssafy.mvc.model.dto.Like;
import com.ssafy.mvc.model.dto.MyRecord;
import com.ssafy.mvc.model.dto.User;
import com.ssafy.mvc.model.service.LikeService;
import com.ssafy.mvc.model.service.MyRecordService;
import com.ssafy.mvc.model.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin("http://localhost:5173")
public class UserRestController {

	private final LikeService likeService;
	private final UserService userService;
	private final MyRecordService myRecordService;
	private final JwtUtil jwtUtil;

	public UserRestController(UserService userService, JwtUtil jwtUtil, LikeService likeService, MyRecordService myRecordService) {
		this.likeService = likeService;
		this.userService = userService;
		this.myRecordService = myRecordService;
		this.jwtUtil = jwtUtil;
	}

	// 유저 목록 불러오기
	@GetMapping("/users")
	public ResponseEntity<?> userList() {
		List<User> list = userService.getUserList();
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(list, HttpStatus.OK);
	}
	
	// 유저 정보 가져오기
	@GetMapping("/{id}")
	public ResponseEntity<?> getUser(@PathVariable("id") int id) {
		User user = userService.getUser(id);
		if (user == null) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// 유저 회원가입
	@PostMapping("/signup")
	public ResponseEntity<String> write(@RequestBody User user) {
		if (userService.signup(user)) {
			return ResponseEntity.status(HttpStatus.CREATED).body("User added");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed User add");
	}

	// 유저 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
		HttpStatus status = null;
		Map<String, Object> result = new HashMap<>();
		User loginUser = userService.login(user.getUserId(), user.getPassword());
		if (loginUser != null) {
			result.put("message", "로그인 성공");
			System.out.println(loginUser);
			result.put("access-token",
					jwtUtil.createToken(loginUser.getName(), loginUser.getId(), loginUser.getManager()));
			status = HttpStatus.ACCEPTED;
		} else {
			status = HttpStatus.NO_CONTENT;
		}
		return new ResponseEntity<>(result, status);
	}

	// 유저 정보 수정
	@PutMapping("")
	public ResponseEntity<String> update(@RequestBody User user) {
		if (userService.modifyUser(user)) {
			return ResponseEntity.ok("User updated");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed update");
	}

	// 유저 아이디 목록 조회
	@GetMapping("/users-id")
	public ResponseEntity<?> userIdList() {
		List<String> list = userService.getUserIdList();
		if (list == null || list.size() == 0) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<String>>(list, HttpStatus.OK);
	}

	// 암장 찜 등록
	@PostMapping("/facility")
	public ResponseEntity<?> addLike(@RequestBody Like like) {
		if (likeService.addLike(like.getUserId(), like.getFacilityId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body("like added");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed like add");
	}

	// 암장 찜 삭제
	@DeleteMapping("/facility")
	public ResponseEntity<?> removeLike(@RequestBody Like like) {
		if (likeService.removeLike(like.getUserId(), like.getFacilityId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body("like removed");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed like remove");
	}

	// 내 찜 목록
	@GetMapping("/facility/{id}")
	public ResponseEntity<?> getMyLikes(@PathVariable("id") int id) {
		List<Like> list = likeService.getLikes(id);
		if (list != null && list.size() > 0) {
			System.out.println(list);
			return new ResponseEntity<List<Like>>(list, HttpStatus.OK);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Failed to get Likes");
	}
}
