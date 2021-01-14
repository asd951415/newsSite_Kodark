package com.kodark.news.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kodark.news.controller.advice.exceptions.SQLConflictException;
import com.kodark.news.dao.AdminProcedureDao;
import com.kodark.news.service.AdminProcedureService;

@Service
public class AdminProcedureServiceImpl implements AdminProcedureService {

	@Autowired
	AdminProcedureDao adminProcedureDao;

	@Override
	public List<Map<String, Object>> execuAdminProcedure(Map<String, Object> params) {
		return adminProcedureDao.excuAdminProcedure(params);
	}

	@Override
	public List<Map<String, Object>> getWaitArticles(String _status) {
		return adminProcedureDao.getWaitArticles(_status);
	}
	
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void createReporter(Map<String, Object> params) {
		adminProcedureDao.insertReporter(params);
		adminProcedureDao.insertReporterDetail(params);
		// java.sql.SQLIntegrityConstraintViolationException 포괄적인 sql 예외
		// org.springframework.dao.DuplicateKeyException 데이터 중복
		// org.springframework.dao.DataIntegrityViolationException 데이터 제약 위반
	}

}
