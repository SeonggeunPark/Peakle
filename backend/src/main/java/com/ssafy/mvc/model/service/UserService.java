package com.ssafy.mvc.model.service;

import java.util.List;

import com.ssafy.mvc.model.dto.User;

public interface UserService {

	List<User> getUserList();

	boolean signup(User user);

	User login(String userId, String password);

	boolean modifyUser(User user);

	List<String> getUserIdList();

	User getUser(int id);
}
