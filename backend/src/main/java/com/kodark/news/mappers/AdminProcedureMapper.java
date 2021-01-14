package com.kodark.news.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface AdminProcedureMapper {


	@Select(value = "{CALL admin_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_input, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_email, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_pwd, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_nickName, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_name, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_local, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_birth, mode=INOUT, jdbcType=DATE, javaType=java.util.Date}"
			+ ",#{_gender, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_image, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_auth, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_commentId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_commentReportId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_delFlag, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_delFlag, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_status, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public void adminProcedure(Map<String, Object> params);
	
	@Select(value = "{CALL admin_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_email, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_pwd, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_nickName, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_name, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_local, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_birth, mode=INOUT, jdbcType=DATE, javaType=java.util.Date}"
			+ ",#{_gender, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_image, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_auth, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_commentId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_commentReportId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_delFlag, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_delFlag, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_status, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public Map<String, Object> adminProcedureMap(Map<String, Object> params);
	
	@Select(value = "{CALL admin_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_email, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_pwd, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_nickName, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_name, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_local, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_birth, mode=INOUT, jdbcType=DATE, javaType=java.util.Date}"
			+ ",#{_gender, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_image, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_auth, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_commentId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_commentReportId, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_doneFlag, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_delFlag, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_status, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> adminProcedureList(Map<String, Object> params);
	
	@Select(value = "{CALL admin_getWaitArticle(#{_status})}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> getWaitArticle(@Param("_status") String _status);

	@Select(value = "{CALL admin_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=IN, jdbcType=INTEGER, javaType=java.lang.Integer }"
			+ ",#{_input, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_email, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_pwd, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_nickName, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_name, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_local, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_birth, mode=INOUT, jdbcType=DATE, javaType=java.util.Date}"
			+ ",#{_gender, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{_image, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_auth, mode=INOUT, jdbcType=CHAR, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> getArticleList(Map<String, Object> params);
	
	@Insert("INSERT INTO users (email, pwd, auth) VALUES(#{_email}, #{_pwd}, 'reporter')")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public void insertReporter(Map<String, Object> params);
	
	@Insert("INSERT INTO user_detail (user_id, nick_name, name, local, birth, gender, image)"
			+ " VALUES(#{id}, #{_nickName}, #{_name}, #{_local}, #{_birth}, #{_gender}, #{_image})")
	public void insertReporterDetail(Map<String, Object> params);
	
}
