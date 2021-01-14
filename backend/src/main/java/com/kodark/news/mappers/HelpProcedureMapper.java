package com.kodark.news.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface HelpProcedureMapper {

	@Select(value = "{CALL help_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"			
			+ ",#{_user_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"			
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"			
			+ ",#{_title, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_content, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_createdAt, mode=IN, jdbcType=TIMESTAMP, javaType=java.lang.String}"
			+ ",#{_doneFlag, mode=IN, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> helpProcedure(Map<String, Object> params);
	
	@Select(value = "{CALL help_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"			
			+ ",#{_user_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"			
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"			
			+ ",#{_title, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_content, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_accepted, mode=IN, jdbcType=TINYINT, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public Map<String, Object> helpProcedureMap(Map<String, Object> params);

}
