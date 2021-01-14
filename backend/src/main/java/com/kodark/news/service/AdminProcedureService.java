package com.kodark.news.service;

import java.util.List;
import java.util.Map;

public interface AdminProcedureService {

	public void execuAdminProcedure(Map<String, Object> params);
	
	public Map<String, Object> execuAdminProcedureMap(Map<String, Object> params);
	
	public List<Map<String, Object>> execuAdminProcedureList(Map<String, Object> params);
	
	public List<Map<String, Object>> getWaitArticles(String _status);
	
	public void createReporter(Map<String, Object> params);
	
	public List<Map<String, Object>> getArticleList(Map<String, Object> params);
}
