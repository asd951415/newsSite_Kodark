package com.kodark.news.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import com.kodark.news.dto.CategoryDto;

@Mapper
public interface ArticleProcedureMapper {

	@Select(value = "{CALL article_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_article_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_reporter_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"	
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	List<Map<String, Object>> articleProcedure(Map<String, Object> params);

	@Select(value = "{CALL article_getArticleDetail("
			+ "#{_articleId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> getArticleDetail(Map<String, Object> params);
	
	@Select(value = "{CALL article_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_commentId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> articleProcedureList(Map<String, Object> params);	


	@Select(value = "{CALL article_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=INOUT, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_title, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_subTitle, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_content, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_createdAt, mode=OUT, jdbcType=DATE, javaType=java.util.Date}"
			+ ",#{_editedAt, mode=OUT, jdbcType=DATE, javaType=java.util.Date}"
			+ ",#{_hit, mode=OUT, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_image, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_imageDec, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_source, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_report_id, mode=OUT, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_email, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_name, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_category, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String }"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> articleProcedure_2(Map<String, Object> params);

	@Select(value = "{CALL latest_procedure("
			+ "#{_category, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> latestProcedure(Map<String, Object> params);
	
	@Select(value = "{CALL article_procedure("
	         + "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
	         + ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
	         + ",#{_commentId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
	         + ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
	         + ")}")
	   @Options(statementType = StatementType.CALLABLE)
	   public Map<String, Object> articleProcedureMap(Map<String, Object> params);

}
