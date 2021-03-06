package com.kodark.news.service;

import java.util.List;
import java.util.Map;

import com.kodark.news.dto.CategoryDto;

public interface ArticleProcedureService {

	public void execuArticleProcedure(Map<String, Object> params);
	
	public Map<String, Object> excuArticleProcedureMap(Map<String, Object> params);
	
	public List<Map<String, Object>> excuArticleProcedureList(Map<String, Object> params);
	
	public List<Map<String, Object>> getArticleDetail(Map<String, Object> params);

	public List<CategoryDto> categoryInfo();

	public List<Map<String, Object>> hotNews();

	public List<Map<String, Object>> execuLatestProcedure(Map<String, Object> params);

	public List<Map<String, Object>> execuCommentProcedure(int i);

}
