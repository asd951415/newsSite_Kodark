package com.kodark.news.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kodark.news.mappers.HelpProcedureMapper;

@Repository
public class HelpProcedureDao {

	@Autowired
	HelpProcedureMapper helpProcedureMapper;

	public List<Map<String, Object>> execuHelpProcedure(Map<String, Object> params) {
		return helpProcedureMapper.helpProcedure(params);
	}
	
	public Map<String, Object> execuHelpProcedureMap(Map<String, Object> params) {
		return helpProcedureMapper.helpProcedureMap(params);
	}
}

