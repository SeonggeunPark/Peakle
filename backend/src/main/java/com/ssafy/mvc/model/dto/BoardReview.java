package com.ssafy.mvc.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class BoardReview {
	private int id;
	private int userId;
	private int boardId;
	private String writer;
	private String content;
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date created;
	
	public BoardReview() {}

	public BoardReview(int id, int userId, int boardId, String writer, String content, Date created) {
		super();
		this.id = id;
		this.userId = userId;
		this.boardId = boardId;
		this.writer = writer;
		this.content = content;
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

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
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

	@Override
	public String toString() {
		return "BoardReview [id=" + id + ", user_id=" + userId + ", board_id=" + boardId + ", writer=" + writer
				+ ", content=" + content + ", created=" + created + "]";
	}
	
}
