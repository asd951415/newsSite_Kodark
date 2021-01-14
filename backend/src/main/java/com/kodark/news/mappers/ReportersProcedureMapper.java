package com.kodark.news.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface ReportersProcedureMapper {
   
	@Select(value = "{CALL reporter_procedure("
		+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
		+ ",#{_article_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
		+ ",#{_reporter_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
		+ ",#{_category_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
		+ ",#{_title, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_sub_title, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_content, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_main_image_url, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_main_image_source, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_main_image_description, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> reportersProcedure(Map<String, Object> params);

	@Select(value = "{CALL reporter_getPubAndWaitArtlcles(#{_status})}")
	public List<Map<String, Object>> getPubAndWaitArtlcles(@Param("_status") String status);
	
	@Select(value = "{CALL reporter_procedure("
		+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
		+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public Map<String, Object> reportersProcedureMap(Map<String, Object> params);
	   
	@Select(value = "{CALL reporter_procedure("
		+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
		+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
		+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> reportersProcedureList(Map<String, Object> params);

}
