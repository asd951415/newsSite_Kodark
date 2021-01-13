package com.kodark.news.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodark.news.dto.CategoryDto;
import com.kodark.news.service.ArticleProcedureService;
import com.kodark.news.service.ReportersProcedureService;

@RestController
@RequestMapping(path = "/article")
public class ArticleController {
	
	private ArticleProcedureService articleProcedureService;
	private ReportersProcedureService reportersProcedureService;

	@Autowired
	public ArticleController(ArticleProcedureService articleProcedureService,ReportersProcedureService reportersProcedureService) {
		this.articleProcedureService = articleProcedureService;
		this.reportersProcedureService = reportersProcedureService;
	}
	
	 /**
	    * 기사 상세 페이지
	    * 작성자 : 이푸름 
	    * 작성일 : 2021-01-06
	  */
	@GetMapping
	public ResponseEntity<Map<String, Object>> getArticleDetail(@RequestParam("articleId") String articleId, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> temp;
		List<Map<String,Object>>list = new ArrayList<>();
		Map<String, Object> reporter = new HashMap<>();;
		

		
		int _articleId = Integer.parseInt(articleId);
		params.put("_articleId", _articleId);
		
		try {
			list = articleProcedureService.getArticleDetail(params);
			params.remove("_articleId");
			params.put("articleId", list.get(0).get("articleId"));
			params.put("title", list.get(0).get("title"));
			params.put("content", list.get(0).get("content"));
			params.put("createdAt", list.get(0).get("createdAt"));
			params.put("editedAt", list.get(0).get("editedAt"));
			params.put("hit", list.get(0).get("hit"));
			reporter.put("id", list.get(0).get("reporterId"));
			reporter.put("name", list.get(0).get("name"));
			reporter.put("email", list.get(0).get("email"));
			params.put("reporter", reporter);
			
			for(int i=0; i<list.size(); i++) {    
				temp = new HashMap<>();
				temp.put("image", list.get(i).get("image"));
				temp.put("imgDec", list.get(i).get("description"));
				temp.put("source", list.get(i).get("source"));
				
				list.set(i, temp);
			}	
			params.put("images", list);
			
			response.setHeader("Links",
					"</article/emotion?id\">; rel=\"emotion\""
							+"</article/comment?id\">; rel=\"comment\"");
			
		} catch (Exception e) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.NOT_FOUND);//404
		}
		return new ResponseEntity<Map<String, Object>> (params, HttpStatus.OK);
	}
	
	/**
	 * 기사 감정 데이터
	 * 날짜 : 2021-01-07
	 * 작성자 : 이종현
	 */
	@GetMapping(path ="/emotion")
	public ResponseEntity<List<Map<String, Object>>> 
	getEmotionInfo(@RequestParam("articleId") int articleId, HttpServletResponse response){
		
		List<Map<String, Object>> params = null;
		Map<String, Object> map = null;
		StringBuffer sb = null;
		int pSize = 0;
		
		try {
			params = articleProcedureService.getEmotionInfo(articleId);
			sb = new StringBuffer();
			pSize = params.size();
			
			for(int i=0; i<pSize; i++) {
				map = new HashMap<String, Object>();
				map.put("href","/article/emotion?articleId=" + articleId + "&emotion="+params.get(i).get("emotion"));
				map.put("method", "put");
				map.put("rel", params.get(i).get("emotion"));		
				params.get(i).put("_link", map);
				} 
			for(int i=0; i<pSize; i++) {
				sb.append("</article/emotion?articleId="+articleId
						+"&emotion="+params.get(i).get("emotion")
						+">;" 
						+"rel="+ params.get(i).get("emotion")
						);
				if(pSize>1) {
					sb.insert(sb.length(), ",");
				}
			}
			if(sb.toString().substring(sb.length()-1).equals(",")) {
				String str = sb.toString().substring(0, sb.length()-1);
				sb = new StringBuffer(str);			
			}
			response.setHeader("Links", sb.toString());	
			
			if(params.isEmpty()) {
				return new ResponseEntity<List<Map<String, Object>>>(params,HttpStatus.NO_CONTENT); //404
			}
			return new ResponseEntity<List<Map<String, Object>>>(params,HttpStatus.OK); //200
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String, Object>>>(HttpStatus.INTERNAL_SERVER_ERROR); //500
		}
	}
	
	/**
	 * 대댓글 데이터
	 * 날짜 : 2021-01-07
	 * 작성자 : 이종현
	 */
	@GetMapping(path ="/comment/reply")
	public ResponseEntity<List<Map<String,Object>>> getCommentReply(
			@RequestParam("articleId") int articleId, @RequestParam("commentStartId") int commentStartId){
		
		List<Map<String, Object>> list = articleProcedureService.getCommentReply(articleId, commentStartId);
		List<Map<String, Object>> tempList = null;
		Map<String,Object> map = null;
		Map<String,Object> temp = null;
		try {
			tempList = new ArrayList<Map<String,Object>>();
			for(int i=0; i<list.size(); i++) {
				map = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				map.put("user", list.get(i).get("userId"));
				map.put("email", list.get(i).get("email"));
				map.put("nickName", list.get(i).get("nickName"));
				map.put("local", list.get(i).get("local"));
				
				temp.put("user", map);
				temp.put("id", list.get(i).get("id"));
				temp.put("content", list.get(i).get("content"));
				temp.put("createdAt", list.get(i).get("createdAt"));
				temp.put("delFlag", list.get(i).get("delFlag"));
				tempList.add(temp);
			}
			
			for(int i=0; i<list.size(); i++) {
				map = new HashMap<String, Object>();
				temp = new HashMap<String, Object>();
				
				map.put("recommend", list.get(i).get("recommend"));
				map.put("decommend", list.get(i).get("decommend"));
				temp.put("reputation", map);
				tempList.add(temp);
			}
		} catch (Exception e) {
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
		return new ResponseEntity<List<Map<String,Object>>>(tempList,HttpStatus.OK);//200
	}

	/**
	 * 메인네비 정보 
	 * 작성자 : 최현지 
	 * 작성일 : 2021-01-06
	 */
	@GetMapping(path = "/navigation")
	public ResponseEntity<Map<String, Object>> mainNavi(HttpServletResponse response) {

		response.setHeader("Links",
				"</auth/sign-in>; 		rel=\"signIn\"," + "</>; 	   				rel=\"home\","
						+ "</section/politics>;	rel=\"politics\"," + "</section/economy>;  	rel=\"economy\","
						+ "</section/society>;  	rel=\"society\"," + "</section/tech>; 		rel=\"tech\","
						+ "</section/world>;  	rel=\"world\"," + "</section/sports>;  	rel=\"sports\","
						+ "</weather>;  			rel=\"weather\"," + "</help>;  				rel=\"help\","
						+ "</introduce>;  		rel=\"introduce\"");

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204

	}

	/**
	 * 핫 뉴스 (popular) 
	 * 작성자 : 최현지 
	 * 작성일 : 2021-01-07
	 */
	@GetMapping(path = "/popular")
	public ResponseEntity<Map<String, Object>> hotNews(HttpServletResponse response) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> temp;
		Map<String, Object> link;
		List<Map<String, Object>> list = new ArrayList<>();

		try {
			list = articleProcedureService.hotNews();

			for (int i = 0; i < list.size(); i++) {
				temp = new HashMap<>();
				link = new HashMap<String, Object>();

				link.put("rel", "article");
				link.put("href", "article?articleId=" + list.get(i).get("id"));
				link.put("method ", "get");

				temp.put("id", list.get(i).get("id"));
				temp.put("title", list.get(i).get("title"));
				temp.put("content", list.get(i).get("content"));
				temp.put("image", list.get(i).get("image"));
				temp.put("imgDec", list.get(i).get("imgDec"));
				temp.put("_link", link);

				list.set(i, temp);
			}

			params.put("category", "all");
			params.put("type", "popular");
			params.put("data", list);
			response.setHeader("Links", "rel : \"article\"," + "href : \"/article?articleId\"," + "method : \"get\"");

		} catch (Exception e) {
			return new ResponseEntity<Map<String, Object>>(params, HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}
		return new ResponseEntity<Map<String, Object>>(params, HttpStatus.OK);// 200
	}

	/**
	 * 섹션별 최신기사(10개) 
	 * 작성자 : 최윤수 
	 * 작성일 : 2021-01-06
	 */
	@GetMapping(path = "/latest")
	public ResponseEntity<Map<String, Object>> latest(@RequestParam String category) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> data;
		Map<String, Object> link;
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> list2 = new ArrayList<>();		
		params.put("_category", category);
		try {
			list = articleProcedureService.execuLatestProcedure(params);
			if(params.get("result_set").equals("200")) {
				for (int i = 0; i < list.size(); i++) {
					map = new HashMap<>();
					data = new HashMap<>();
					link = new HashMap<>();
					map.put("category", category);
					map.put("type", "latest");
					data.put("id", list.get(i).get("id"));
					data.put("title", list.get(i).get("title"));
					data.put("subTitle", list.get(i).get("sub_title"));
					data.put("image", list.get(i).get("image"));
					data.put("imgDec", list.get(i).get("imgDec"));
					link.put("rel", "article");
					link.put("href", "/article?articleId="+list.get(i).get("id"));
					link.put("method", "get");
					data.put("_link", link);
					list2.add(data);				
				}
				map.put("data", list2);	
			}else if(params.get("result_set").equals("404")) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}
		return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);// 200
	}

	/**
	 * 카테고리 정보 
	 * 작성자 : 최현지 
	 * 작성일 : 2021-01-06
	 */
	// 카테고리 정보
	@GetMapping(path = "/category")
	public ResponseEntity<List<CategoryDto>> categoryInfo() {
		List<CategoryDto> categoryInfo = articleProcedureService.categoryInfo();
		return new ResponseEntity<List<CategoryDto>>(categoryInfo, HttpStatus.OK);// 200
	}

	/**
	 * title :19.기사댓글 데이터 
	 * author : 최윤수 
	 * date : 2021-01-07
	 */
	@GetMapping(path = "/comment")
	public ResponseEntity<List<Map<String, Object>>> comment(
			@RequestParam(value = "articleId") int articleId,
			@RequestParam(value = "commentStartId") int commentStartId,
			HttpServletResponse response) {
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> user;
		Map<String, Object> reputation;
		Map<String, Object> comment;
		List<Map<String, Object>> list = new ArrayList<>();
		params.put("_article_id", articleId);
		params.put("_start_id", commentStartId-1);		
//		try {
			list = articleProcedureService.execuCommentProcedure(params);
			System.out.println("list:"+list);
			for(int i=0;i<list.size();i++) {
				user = new HashMap<>();
				reputation = new HashMap<>();
				comment = new HashMap<>();
				comment.put("id", list.get(i).get("id"));
				comment.put("content", list.get(i).get("content"));
				comment.put("createdAt", list.get(i).get("createdAt"));
				comment.put("delFlag", list.get(i).get("delFlag"));
				user.put("id", list.get(i).get("userId"));
				user.put("email", list.get(i).get("email"));
				user.put("nickName", list.get(i).get("nickName"));
				user.put("local", list.get(i).get("local"));
				comment.put("user", user);
				reputation.put("recommend", list.get(i).get("recommend"));
				reputation.put("decommend", list.get(i).get("decommend"));
				comment.put("reputation", reputation);
				list.set(i, comment);
				response.setHeader("links", "</article/comment/reply?id>; rel=\"reply\"");
			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
//		}		
		return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);// 200
	}
	/**
	 * title : 38.구독기자의 기사목록
	 * desc : 
	 * author : 최윤수
	 * date : 2021-01-11
	 * @param : reporterId
	 */
	@GetMapping(path = "/reporters")
	public ResponseEntity<Map<String, Object>> reportersArticleList(@RequestParam int reporterId){
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> params2 = new HashMap<>();
		Map<String, Object> info = new HashMap<>();
		Map<String, Object> reporter = new HashMap<>();
		Map<String, Object> articles;
		List<Map<String,Object>>list = new ArrayList<>();		
		params.put("_reporter_id", reporterId);
		params.put("result_set", "get_list");
		params.put("_id", reporterId);
		try {	
			params2 = reportersProcedureService.getReporterInfo(params);				
			System.out.println("params:"+params2.toString());
			reporter.put("id", params2.get("id"));
			reporter.put("email", params2.get("email"));
			reporter.put("nickName", params2.get("nick_name"));
			reporter.put("name", params2.get("name"));
			reporter.put("image", params2.get("image"));
			info.put("reporter", reporter);
			list = reportersProcedureService.reportersArticleList(params);
			for(int i=0;i<list.size();i++) {
				articles = new HashMap<>();
				articles.put("title", list.get(i).get("title"));
				articles.put("subTitle", list.get(i).get("sub_title"));
				articles.put("image", list.get(i).get("image"));
				articles.put("imgDec", list.get(i).get("description"));
				articles.put("link", "/article?="+list.get(i).get("id"));
				list.set(i, articles);
			}
			info.put("articles", list);
		} catch (Exception e) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);//500
		}
		return new ResponseEntity<Map<String,Object>>(info,HttpStatus.OK);//200
	}


}
