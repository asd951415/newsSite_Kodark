package com.kodark.news.service;

import java.util.List;
import java.util.Map;

public interface ReportersProcedureService {

	public List<Map<String, Object>> execuReportersProcedure(Map<String, Object> params);

	public List<Map<String, Object>> getPubAndWaitArtlcles(String status);
	
	public Map<String, Object> execuReportersProcedureMap(Map<String, Object> params);
	
	public List<Map<String, Object>> execuReportersProcedureList(Map<String, Object> params);
	
	public Map<String, Object> getReporterInfo(Map<String, Object> params);
	
}
