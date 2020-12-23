package com.kodark.news.dto;

import java.util.Date;

public class ArticleReportDto {
	private int id;
	private String reason;
	private Date createdAt;
	private String doneFlag;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Date getCreated_at() {
		return createdAt;
	}
	public void setCreated_at(Date created_at) {
		this.createdAt = created_at;
	}
	public String getDone_flag() {
		return doneFlag;
	}
	public void setDone_flag(String done_flag) {
		this.doneFlag = done_flag;
	}
	@Override
	public String toString() {
		return "ArticleReportDto [id=" + id + ", reason=" + reason + ", createdAt=" + createdAt + ", doneFlag="
				+ doneFlag + "]";
	}
	
	
}
