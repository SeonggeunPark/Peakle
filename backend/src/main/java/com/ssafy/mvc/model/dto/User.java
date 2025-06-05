package com.ssafy.mvc.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class User {
    private int id;	// 식별번호
    private String userId; // 로그인 ID
    private String password;// 비밀번호
    private String name;
    private String email;
    private Date birth;		// 생년월일
    private String phoneNumber;
    private int manager; // 관리자 여부 (0: 관리자, 1: 직원, 2: 일반 사용자)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date created;
	
    public User() {
		// TODO Auto-generated constructor stub
	}

	public User(int id, String userId, String password, String name, String email, Date birth, String phoneNumber,
			int manager, Date created) {
		super();
		this.id = id;
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.email = email;
		this.birth = birth;
		this.phoneNumber = phoneNumber;
		this.manager = manager;
		this.created = created;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getManager() {
		return manager;
	}

	public void setManager(int manager) {
		this.manager = manager;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", password=" + password + ", name=" + name + ", email="
				+ email + ", birth=" + birth + ", phoneNumber=" + phoneNumber + ", manager=" + manager + ", created="
				+ created + "]";
	}
    
}
