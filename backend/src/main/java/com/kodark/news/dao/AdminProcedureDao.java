package com.kodark.news.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kodark.news.mappers.AdminProcedureMapper;

@Repository
public class AdminProcedureDao {

	@Autowired
	AdminProcedureMapper adminProcedureMapper;

	public List<Map<String, Object>> excuAdminProcedure(Map<String, Object> params) {
		return adminProcedureMapper.adminProcedure(params);
	}

	public List<Map<String, Object>> getWaitArticles(String _status) {
		return adminProcedureMapper.getWaitArticle(_status);
	}

	public void insertReporter(Map<String, Object> params) {
		adminProcedureMapper.insertReporter(params);
	}
	
	public void insertReporterDetail(Map<String, Object> params) {
		adminProcedureMapper.insertReporterDetail(params);
	}

}
