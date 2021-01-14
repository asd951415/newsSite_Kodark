package com.kodark.news.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kodark.news.mappers.ReportersProcedureMapper;

@Repository
public class ReportersProcedureDao {

	@Autowired
	ReportersProcedureMapper reportersProcedureMapper;


	public Map<String, Object> execuReportersProcedureMap(Map<String, Object> params) {
		return reportersProcedureMapper.reportersProcedureMap(params);
	}

	public List<Map<String, Object>> execuReportersProcedure(Map<String, Object> params) {
		return reportersProcedureMapper.reportersProcedure(params);
	}
	
	public List<Map<String, Object>> execuReportersProcedureList(Map<String, Object> params) {
		return reportersProcedureMapper.reportersProcedureList(params);
	}

	// 이푸름
	public List<Map<String, Object>> getPubAndWaitArtlcles(String status) {
		return reportersProcedureMapper.getPubAndWaitArtlcles(status);
	}

}

