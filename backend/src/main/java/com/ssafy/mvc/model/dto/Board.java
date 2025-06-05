package com.ssafy.mvc.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Board {
	private int id;
	private int userId;
	private String title;
	private String writer;
	private String content;
	private int viewCnt;
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;
	
	public Board() {}

	public Board(int id, int userId, String title, String writer, String content, int viewCnt, Date created) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.viewCnt = viewCnt;
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

	public int getViewCnt() {
		return viewCnt;
	}

	public void setView_cnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	@Override
	public String toString() {
		return "Board [id=" + id + ", user_id=" + userId + ", title=" + title + ", writer=" + writer + ", content="
				+ content + ", view_cnt=" + viewCnt + ", created=" + created + "]";
	}
	
	
}
