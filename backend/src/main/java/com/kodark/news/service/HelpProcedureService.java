package com.kodark.news.service;

import java.util.List;
import java.util.Map;

public interface HelpProcedureService {

	public List<Map<String, Object>> execuHelpProcedure(Map<String, Object> params);

	public  Map<String, Object> execuHelpProcedureMap(Map<String, Object> params);
}

