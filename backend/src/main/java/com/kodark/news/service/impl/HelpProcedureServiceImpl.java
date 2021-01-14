package com.kodark.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodark.news.dao.HelpProcedureDao;
import com.kodark.news.service.HelpProcedureService;

@Service
public class HelpProcedureServiceImpl implements HelpProcedureService {

	@Autowired
	HelpProcedureDao helpProcedureDao;
	
	@Override
	public List<Map<String, Object>> execuHelpProcedure(Map<String, Object> params) {
		return helpProcedureDao.execuHelpProcedure(params);
	}

	@Override
	public Map<String, Object> execuHelpProcedureMap(Map<String, Object> params) {
		return helpProcedureDao.execuHelpProcedureMap(params);
	}

	
}
