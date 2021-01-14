  
package com.kodark.news.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodark.news.dao.ReportersProcedureDao;
import com.kodark.news.service.ReportersProcedureService;

@Service
public class ReportersProcedureServiceImpl implements ReportersProcedureService {

	@Autowired
	private ReportersProcedureDao reportersProcedureDao;
	
	@Override
	public List<Map<String, Object>> execuReportersProcedure(Map<String, Object> params) {
		return reportersProcedureDao.execuReportersProcedure(params);
	}

	@Override
	public Map<String, Object> execuReportersProcedureMap(Map<String, Object> params) {
		return reportersProcedureDao.execuReportersProcedureMap(params);
	}
	

	@Override
	public List<Map<String, Object>> execuReportersProcedureList(Map<String, Object> params) {
		return reportersProcedureDao.execuReportersProcedureList(params);
	}

	@Override
	public List<Map<String, Object>> getPubAndWaitArtlcles(String status) {
		return reportersProcedureDao.getPubAndWaitArtlcles(status);
	}

	@Override
	public Map<String, Object> getReporterInfo(Map<String, Object> params) {		
		return reportersProcedureDao.execuReportersProcedureMap(params);
	}

}

