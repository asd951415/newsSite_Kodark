package com.kodark.news.service;

import java.util.List;
import java.util.Map;

public interface ArticleProcedureService {

	public List<Map<String, Object>> execuArticleProcedure(Map<String, Object> params);
	
	public Map<String, Object> excuArticleProcedureMap(Map<String, Object> params);
	
	public List<Map<String, Object>> excuArticleProcedureList(Map<String, Object> params);

	public List<Map<String, Object>> execuArticleProcedure_2(Map<String, Object> params);

	public List<Map<String, Object>> execuLatestProcedure(Map<String, Object> params);
	
	public List<Map<String, Object>> getArticleDetail(Map<String, Object> params);


}
