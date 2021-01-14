package com.kodark.news.service;

import java.util.List;
import java.util.Map;

public interface UsersProceduerService {
	public void execuUsersProcedure(Map<String, Object> params);
	
	public List<Map<String, Object>> execuUsersProcedureList(Map<String, Object> params);
	
	public Map<String,Object> execuCommentMapProcedure(Map<String, Object> params);
	
	public List<Map<String,Object>> execuCommentListProcedure(Map<String,Object> params);

	public Map<String, Object> mypageDetail(Map<String, Object> params);

	public Map<String, Object> myPage(Map<String, Object> params);
	
	public List<Map<String, Object>> subList(Map<String, Object> params);

}
