package com.kodark.news.service;

import java.util.List;
import java.util.Map;

public interface AdminProcedureService {

	public List<Map<String, Object>> execuAdminProcedure(Map<String, Object> params);
	
	public List<Map<String, Object>> getWaitArticles(String _status);
	
	public void createReporter(Map<String, Object> params);
	
}
