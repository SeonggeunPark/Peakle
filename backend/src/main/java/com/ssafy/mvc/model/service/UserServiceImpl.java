package com.ssafy.mvc.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.mvc.model.dao.UserDao;
import com.ssafy.mvc.model.dto.User;
// 어노테이션으로 빈 등록해야됨!!
@Service
public class UserServiceImpl implements UserService{
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	// 전체 유저 조회
	@Override
	public List<User> getUserList() {
		return userDao.selectAll();
	}
	
	// 회원가입
	@Override
	public boolean signup(User user) {
		return userDao.insertUser(user) == 1;
	}
	
	// 유저 로그인
	@Override
	public User login(String userId, String password) {
		Map<String, String> info = new HashMap<>();
		info.put("userId", userId);
		info.put("password", password);
		User user = userDao.signUp(info);
		return user;
	}
	
	// 유저 수정
	@Override
	public boolean modifyUser(User user) {
		return userDao.updateUser(user) == 1;
	}
	
	// 유저 전체 아이디 조회
	@Override
	public List<String> getUserIdList() {
		return userDao.selectAllId();
	}
	@Override
	public User getUser(int id) {
		return userDao.selectOne(id);
	}

}
