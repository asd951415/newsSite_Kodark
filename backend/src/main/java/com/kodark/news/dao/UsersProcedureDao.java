package com.kodark.news.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kodark.news.mappers.UsersProcedureMapper;

@Repository
public class UsersProcedureDao {

	@Autowired
	UsersProcedureMapper usersProcedureMapper;

	public void execuUsersProcedure(Map<String, Object> params) {
		usersProcedureMapper.usersProcedure(params);
	}
	
	public List<Map<String, Object>> execuUsersProcedureList(Map<String, Object> params) {
		return usersProcedureMapper.usersProcedureList(params);
	}

	public Map<String,Object> execuCommentMapProcedure(Map<String, Object> params) {
		return usersProcedureMapper.execuCommentMapProcedure(params);
	}
	
	public List<Map<String,Object>> execuCommentListProcedure(Map<String,Object> params) {
		return usersProcedureMapper.execuCommentListProcedure(params);
	}

	public Map<String, Object> myPage(Map<String, Object> params) {
		return usersProcedureMapper.getMyPage(params);
	}
	
	public List<Map<String, Object>> subList(Map<String, Object> params){
		return usersProcedureMapper.subList(params);
	}

	public Map<String, Object> myPageDetail(Map<String, Object> params) {
		return usersProcedureMapper.getMyPageDetail(params);
	}
}
