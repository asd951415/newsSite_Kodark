package com.kodark.news.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

/*
 * title : auth_procedure 실행
 * dec : 인증과 관련된 요청을 처리하는 auth_procedure을 실행한다.
 * 작성자 : 류제욱
 * 작성일 : 2020-01-06
 */
@Mapper
public interface AuthProcedureMapper {
	@Select(value = "{CALL auth_procedure("
			+ "#{_switch, mode=IN, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_email, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_auth_string, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_pwd, mode=INOUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{_id, mode=OUT, jdbcType=INTEGER, javaType=java.lang.Integer}"
			+ ",#{_auth, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"
			+ ",#{result_set, mode=OUT, jdbcType=VARCHAR, javaType=java.lang.String}"			
			+ ")}")
	@Options(statementType = StatementType.CALLABLE)
	public void authProcedure(Map<String, Object> params);

	// 
	@Select(value = "{CALL admin_repoters_procedure}")
	@Options(statementType = StatementType.CALLABLE)
	public List<Map<String, Object>> getInfoReporters();
}

