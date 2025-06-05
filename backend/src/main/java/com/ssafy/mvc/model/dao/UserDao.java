package com.ssafy.mvc.model.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.mvc.model.dto.User;

public interface UserDao {

	List<User> selectAll();

	int insertUser(User user);

	User signUp(Map<String, String> info);

	int updateUser(User user);

	List<String> selectAllId();

	User selectOne(int id);
	
}