package com.kodark.news.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodark.news.service.ReportersProcedureService;
import com.kodark.news.service.StatisticsService;

@RestController
@RequestMapping(path = "/reporters")
public class ReporterController {

	private ReportersProcedureService reportersProcedureService;
	private StatisticsService statisticsService;

	@Autowired
	public ReporterController(ReportersProcedureService reportersProcedureService,
			StatisticsService statisticsService) {

		this.reportersProcedureService = reportersProcedureService;
		this.statisticsService = statisticsService;
	}

	/**
	 * 기자 프로필 
	 * 날짜 : 2021-01-07 
	 * 작성자 : 이종현
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> getInfoProfile(HttpServletRequest request) {
		Map<String, Object> params = null;
		Map<String, Object> map = null;
		try {
			params = new HashMap<String, Object>();
			map = new HashMap<String, Object>();
			//params.put("_id", request.getAttribute("id")); 미사용
			params.put("_id", 3); //임의로 지정
			params.put("_switch", "reporter_profile");
			
			map = reportersProcedureService.execuReportersProcedureMap(params);

			
			if(params.get("result_set").equals("200")) {
				return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			}else {
				return new ResponseEntity<Map<String,Object>>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	/**
	 * title : 기사네비정보 
	 * author : 최현지 
	 * date : 2020-01-06
	 * @param response
	 * @return
	 */
	@GetMapping(path = "/navigation")
	public ResponseEntity<Map<String, Object>> reporterNavi(HttpServletResponse response) {

		response.setHeader("Links",
				"</repoters?reporterId>; 					rel=\"reporterList\","
						+ "</reporters/new-post>; 	   				rel=\"articlePostForm\","
						+ "</reporters/article?status=\"published\">; rel=\"publishedArticleList\","
						+ "</reporters/article?status=\"waiting\"> ;  rel=\"waitingArticleList\"");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204

	}

	/**
	 * 기사댓글통계데이터 
	 * 작성자 : 최윤수 
	 * 작성일 : 2021-01-07
	 */
	@GetMapping(path = "/article/statistics")
	public ResponseEntity<Map<String, Object>> statistics(
			@RequestParam(value = "articleId", required = false, defaultValue = "2") String articleId) {
		int id = Integer.parseInt(articleId);
		id = 2;
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> gender = new HashMap<>();
		Map<String, Object> age = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		try {
			list = statisticsService.execuArticleStatisticsProcedure(id);
			for (int i = 0; i < list.size(); i++) {
				gender = new HashMap<>();
				age = new HashMap<>();
				gender.put("maleNum", list.get(i).get("maleNum"));
				gender.put("femaleNum", list.get(i).get("femaleNum"));
				age.put("age10", list.get(i).get("age10"));
				age.put("age20", list.get(i).get("age20"));
				age.put("age30", list.get(i).get("age30"));
				age.put("age40", list.get(i).get("age40"));
				age.put("elseAge", list.get(i).get("elseAge"));
				params.put("gender", gender);
				params.put("age", age);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
		}
		return new ResponseEntity<Map<String, Object>>(params, HttpStatus.OK);// 200
	}

	/**
	 * 발행된 기사 블라인드 
	 * 작성자 : 최윤수 
	 * 작성일 : 2021-01-07
	 */
	@PatchMapping(path = "/article")
	public ResponseEntity<String> articleBlind(@RequestBody Map<String, Object> body) {
		int articleId = Integer.valueOf((String) body.get("articleId"));
		int reporterId = Integer.valueOf((String) body.get("reporterId"));
		Map<String, Object> params = new HashMap<>();

		params.put("_article_id", articleId);
		params.put("_reporter_id", reporterId);
		params.put("result_set", "blind");
		try {
			reportersProcedureService.execuReportersProcedure(params);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}

		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);// 205
	}

	/**
	 * 발행대기전 기사 삭제 
	 * 작성자 : 최윤수 
	 * 작성일 : 2021-01-07
	 */
	@DeleteMapping(path = "/article")
	public ResponseEntity<String> articleDelete(@RequestParam(value = "articleId") String param) {
		int articleId = Integer.parseInt(param);
		int reporterId = 1;
		Map<String, Object> params = new HashMap<>();
		params.put("_article_id", articleId);
		params.put("_reporter_id", reporterId);
		params.put("result_set", "delete");
		try {
			reportersProcedureService.execuReportersProcedure(params);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}
		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);// 205
	}

	/**
	 * 기사수정(일단보류2021-01-07) 
	 * 작성자 : 최윤수 
	 * 작성일 : 2021-01-07
	 */
	@PutMapping(path = "/new-post")
	public ResponseEntity<String> articleModify(@RequestBody Map<String, Object> body) {
		int reporterId = Integer.valueOf((String) body.get("reporterId"));
		int categoryId = Integer.valueOf((String) body.get("categoryId"));
		String title = (String) body.get("title");
		String content = (String) body.get("content");

		Map<String, Object> params = new HashMap<>();
		params.put("_reporter_id", reporterId);
		params.put("_category_id", categoryId);
		params.put("result_set", "update");
		params.put("title", title);
		params.put("content", content);
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);// 204
	}

	/**
	 * 발행/발행대기 기사 
	 * 작성자 : 이푸름 
	 * 작성일 : 2021-01-06
	 */
	@GetMapping(path = "/article")
	public ResponseEntity<List<Map<String, Object>>> pubAndWaitArtlcles(@RequestParam("status") String status,
			HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> container;
		List<Map<String, Object>> linkList;
		Map<String, Object> link1;
		Map<String, Object> link2;
		Map<String, Object> link3;
		String _status = status;

		list = reportersProcedureService.getPubAndWaitArtlcles(_status);

		for (int i = 0; i < list.size(); i++) {
			container = new HashMap<String, Object>();

			container.put("id", list.get(i).get("id"));
			container.put("category", list.get(i).get("category"));
			container.put("title ", list.get(i).get("title "));
			container.put("createdAt ", list.get(i).get("createdAt"));
			container.put("editedAt ", list.get(i).get("editedAt"));
			container.put("hit", list.get(i).get("hit"));
			container.put("status ", list.get(i).get("status"));

			linkList = new ArrayList<Map<String, Object>>();
			link1 = new HashMap<String, Object>();
			link2 = new HashMap<String, Object>();
			link3 = new HashMap<String, Object>();

			link1.put("rel", "editArticleForm");
			link1.put("href", "/ko/reporters/article");
			link1.put("method ", "get");
			linkList.add(link1);

			link2.put("rel", "blindArticle");
			link2.put("href", "/reporters/article?status=deleted");
			link2.put("method ", "patch");
			linkList.add(link2);

			link3.put("rel", "deleteArticle");
			link3.put("href", "/reporters/article?articleId");
			link3.put("method ", "delete");

			linkList.add(link3);
			container.put("_links", linkList);
			list.set(i, container);
		}

		response.setHeader("Links",
				"</ko/reporters/article>; 					rel=\"editArticleForm\","
						+ "</reporters/article?status=\"deleted\">; 	rel=\"blindArticle\","
						+ "</reporters/article?articleId\">; rel=\"deleteArticle\","
						+ "</reporters/article/statics\"> ;  rel=\"articlestatics\","
						+ "</ko/article?articleId\">; rel=\"article\",");

		if (reportersProcedureService.getPubAndWaitArtlcles(_status).get(1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
		}
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK); // 200
	}

}