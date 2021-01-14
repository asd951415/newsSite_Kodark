package com.kodark.news.controller.advice.exceptions;

import javax.servlet.http.HttpServletRequest;

public class SQLConflictException extends RuntimeException {
	
	public SQLConflictException() {
		super(ErrorMessage.getSQLConflictMessage());
	}
	
	public SQLConflictException(Exception e) {
		super(e);
	}
}
