package com.kodark.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodark.news.dao.UsersProcedureDao;
import com.kodark.news.service.UsersProceduerService;

@Service
public class UsersProceduerServiceImpl implements UsersProceduerService {

	@Autowired
	UsersProcedureDao usersProcedureDao;

	@Override
	public void execuUsersProcedure(Map<String, Object> params) {
		usersProcedureDao.execuUsersProcedure(params);
	}
	
	@Override
	public List<Map<String, Object>> execuUsersProcedureList(Map<String, Object> params) {
		return usersProcedureDao.execuUsersProcedureList(params);
	}

	@Override

	public Map<String,Object> execuCommentMapProcedure(Map<String, Object> params) {
		return usersProcedureDao.execuCommentMapProcedure(params);
	}
	
	@Override
	public List<Map<String,Object>> execuCommentListProcedure(Map<String,Object> params) {
		return usersProcedureDao.execuCommentListProcedure(params);
  }
  
  @Override
	public Map<String, Object> mypageDetail(Map<String, Object> params) {
		return usersProcedureDao.myPageDetail(params);
	}

	@Override
	public Map<String, Object> myPage(Map<String, Object> params) {
		return usersProcedureDao.myPage(params);
	}

	@Override
	public List<Map<String, Object>> subList(Map<String, Object> params) {		
		return usersProcedureDao.subList(params);
	}

}
