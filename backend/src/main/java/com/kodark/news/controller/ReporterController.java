package com.kodark.news.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kodark.news.service.ReportersProcedureService;
import com.kodark.news.service.StatisticsService;
import com.kodark.news.utils.Util;

@RestController
@RequestMapping(path = "/reporters")
public class ReporterController {

	private ReportersProcedureService reportersProcedureService;
	private StatisticsService statisticsService;
	private Util util;

	@Autowired
	public ReporterController(ReportersProcedureService reportersProcedureService,
			StatisticsService statisticsService, Util util) {

		this.reportersProcedureService = reportersProcedureService;
		this.statisticsService = statisticsService;
		this.util = util;
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
			params.put("_id", 5); //임의로 지정
			params.put("_switch", "reporter_profile");
			
			map = reportersProcedureService.execuReportersProcedureMap(params);

			if (map.isEmpty()) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
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
	 * title : 69.기사댓글통계데이터 
	 * desc : 기사별 성별&연령대 통계 데이터
	 * author : 최윤수 
	 * date : 2021-01-07
	 * @param : articleId
	 * @return : gender(num), age(num)
	 */
	@GetMapping(path = "/article/statistics")
	public ResponseEntity<Map<String, Object>> statistics(@RequestParam(value = "articleId") int articleId) {
		int id = articleId;		
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
	 * title : 68.발행된 기사 블라인드 
	 * desc : 발행전에 올라온 기사 삭제
	 * author : 최윤수 
	 * date : 2021-01-07
	 * @param : articleId
	 */
	@PatchMapping(path = "/article")
	public ResponseEntity<String> articleBlind(@RequestBody Map<String, Object> body,HttpServletRequest request) {
		int articleId = Integer.valueOf((String) body.get("articleId"));
		int reporterId = 4;//request.getAttribute("id");
		String status = (String)body.get("status");
		Map<String, Object> params = new HashMap<>();

		params.put("_article_id", articleId);
		params.put("_reporter_id", reporterId);
		params.put("result_set", status);
		try {
			reportersProcedureService.execuReportersProcedureList(params);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}

		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);// 205
	}

		/**
	 * title : 67.발행대기전 기사 삭제 
	 * author : 최윤수 
	 * date : 2021-01-07
	 * @param : articleId
	 */
	@DeleteMapping(path = "/article")
	public ResponseEntity<String> articleDelete(@RequestParam(value = "articleId") String param,HttpServletRequest request) {
		int articleId = Integer.parseInt(param);
		int reporterId = 4; //request.getAttribute("id");
		Map<String, Object> params = new HashMap<>();
		params.put("_article_id", articleId);
		params.put("_reporter_id", reporterId);
		params.put("_switch", "unpublish");
		try {
			reportersProcedureService.execuReportersProcedure(params);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}
		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);// 205
	}


	/**
	 * 기사작성
	 * 작성자 : 최윤수 
	 * 작성일 : 2021-01-07
	 * 수정 : 류제욱 2021=01-14
	 */
	@PostMapping(path = "/article")
	public ResponseEntity<String> articleModify(HttpServletRequest request, @RequestBody Map<String, Object> body) {
		int id = (int)request.getAttribute("id");
		int categoryId = (int) body.get("categoryId");
		String title = (String) body.get("title");
		String subTitle = (String) body.get("subTitle");
		String content = (String) body.get("content");
		
		@SuppressWarnings("unchecked")
		Map<String, Object> mainImage = (Map<String, Object>) body.get("mainImage");
		String mainImageUrl = (String) mainImage.get("url");
		String mainImageSource = (String) mainImage.get("source");
		String mainImageDescription = (String) mainImage.get("description");
		
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "new_post");
		params.put("_reporter_id", id);
		params.put("_category_id", categoryId);
		params.put("_title", title);
		params.put("_sub_title", subTitle);
		params.put("_content", content);
		params.put("_main_image_url", mainImageUrl);
		params.put("_main_image_source", mainImageSource);
		params.put("_main_image_description", mainImageDescription);
		reportersProcedureService.execuReportersProcedure(params);
		
		String resultSet = (String)params.get("result_set");
		if(resultSet.equals("success")) {
			return new ResponseEntity<String>(HttpStatus.CREATED); // 201
		} else {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}
	
	/**
	    * title : 66.기사수정
	    * author : 최윤수 
	    * date : 2021-01-14
	    */
	   @PutMapping(path = "/article")
	   public ResponseEntity<String> articleModify(@RequestBody Map<String, Object> body
	         ,HttpServletResponse response,HttpServletRequest request) {
	      int id = (int)request.getAttribute("id");
	      int articleId = (int) body.get("articleId");
	      int categoryId = (int) body.get("categoryId");
	      String title = (String) body.get("title");
	      String subTitle = (String) body.get("subTitle");
	      String content = (String) body.get("content");
	        
	      @SuppressWarnings("unchecked")
	      Map<String, Object> mainImage = (Map<String, Object>) body.get("mainImage");
	      String mainImageUrl = (String) mainImage.get("url");
	      String mainImageSource = (String) mainImage.get("source");
	      String mainImageDescription = (String) mainImage.get("description");
	      
	      Map<String, Object> params = new HashMap<>();
	      params.put("_switch", "modify");
	      params.put("_reporter_id", id);
	      params.put("_article_id", articleId);
	      params.put("_category_id", categoryId);
	      params.put("_title", title);
	      params.put("_sub_title", subTitle);
	      params.put("_content", content);
	      params.put("_main_image_url", mainImageUrl);
	      params.put("_main_image_source", mainImageSource);
	      params.put("_main_image_description", mainImageDescription);
	      reportersProcedureService.execuReportersProcedure(params);      
	      if(params.get("result_set").equals("500")) {
	         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500 DB error
	      }else
	         response.setHeader("links", "</reporters/new-post>; rel=\"self\",\r\n"
	                                         + "</ko/reporters/article?status=\"waiting\">; rel=\"next\"");
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
	   }


	/**
	 * title : 65.발행/발행대기 기사 
	 * author : 이푸름 
	 * date : 2021-01-06
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
			link1.put("href", "/en/reporters/article");
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
				"</en/reporters/article>; 					rel=\"editArticleForm\","
						+ "</reporters/article?status=\"deleted\">; 	rel=\"blindArticle\","
						+ "</reporters/article?articleId\">; rel=\"deleteArticle\","
						+ "</reporters/article/statics\"> ;  rel=\"articlestatics\","
						+ "</en/article?articleId\">; rel=\"article\",");

		if (reportersProcedureService.getPubAndWaitArtlcles(_status).get(1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
		}
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK); // 200
	}
	
	/**
	 * title : 이미지 업로드(기사작성) 
	 * author : 류제욱 
	 * date : 2020-01-13
	 * @param 
	 * @return
	 */
	@PostMapping(path = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Map<String, Object>> createReporter(
			MultipartHttpServletRequest multiRequest, HttpServletRequest request) {
		
		MultipartFile imageFile = multiRequest.getFile("image");
		String fileName = util.saveImage(imageFile, request);
		Map<String, Object> map = new HashMap<>();
		map.put("imageUrl", fileName);
		
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
	}

}

