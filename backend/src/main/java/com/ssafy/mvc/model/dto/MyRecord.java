package com.ssafy.mvc.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MyRecord {

	private int id;
    private int userId; // 참조 - User ID
    private int facilityId; // 참조 - Facility ID
    private String title; // 리뷰 제목
    private String writer; // 작성자 이름
    private String content; // 리뷰 내용
    private String record;
    private int rating;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;
    
    public MyRecord() {}

	public MyRecord(int id, int userId, int facilityId, String title, String writer, String content, String record,
			int rating, Date created) {
		super();
		this.id = id;
		this.userId = userId;
		this.facilityId = facilityId;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.record = record;
		this.rating = rating;
		this.created = created;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "MyRecord [id=" + id + ", userId=" + userId + ", facilityId=" + facilityId + ", title=" + title
				+ ", writer=" + writer + ", content=" + content + ", record=" + record + ", rating=" + rating
				+ ", created=" + created + "]";
	}
    
}
