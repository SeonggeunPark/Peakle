package com.ssafy.mvc.model.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Review {
	
	private int id;
    private int userId; // 참조 - User ID
    private int facilityId; // 참조 - Facility ID
    private int rating; // 별점 - 0 ~ 5점
    private String title; // 리뷰 제목
    private String writer; // 작성자 이름
    private String content; // 리뷰 내용
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date created;

    // 기본 생성자
    public Review() {
	}

    // 종합 생성자
	public Review(int id, int userId, int facilityId, int rating, String title, String writer, String content,
			Date created) {
		super();
		this.id = id;
		this.userId = userId;
		this.facilityId = facilityId;
		this.rating = rating;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.created = created;
	}

	// Getter & Setter ...
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
	
	// toString
	@Override
	public String toString() {
		return "Review [id=" + id + ", userId=" + userId + ", facilityId=" + facilityId + ", rating=" + rating
				+ ", title=" + title + ", writer=" + writer + ", content=" + content + ", created=" + created + "]";
	}

    
}
