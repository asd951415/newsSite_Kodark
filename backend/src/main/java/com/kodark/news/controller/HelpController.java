package com.kodark.news.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodark.news.service.HelpProcedureService;

@RestController
@RequestMapping(path = "/help")
public class HelpController {

	HelpProcedureService helpProcedureService;

	@Autowired
	public HelpController(HelpProcedureService helpProcedureService) {
		this.helpProcedureService = helpProcedureService;
	}
	
	/**
	 * title : 고객센터(40)
	 * desc : 고객센터 메인 페이지
	 * author : 최현지
	 * date :	2021-01-10
	 */
	@GetMapping
	public ResponseEntity<List<Map<String, Object>>> ServiceCenter(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		params.put("_switch", "service_center");
		
		list = helpProcedureService.execuHelpProcedure(params);
		
		for(int i =0; i<list.size(); i++) {
			int id = (int)list.get(i).get("id");
			String title = (String) list.get(i).get("title");
			String content = (String) list.get(i).get("content");
			
			Map<String, Object> temp = new HashMap<>();
			temp.put("id", id);
			temp.put("title", title);
			temp.put("content", content);
			
			list.set(i, temp);
		}
				
		response.setHeader("Links", "</help/question-list>; rel=\"myQuestionList\"");
		
		return new ResponseEntity<>(list, HttpStatus.OK);// 200
	}
	

	/**
	 * title : 내 문의글(41)
	 * desc :
	 * author : 최현지
	 * date : 2021-01-11
	 */
	@GetMapping(path = "/question-list")
	public ResponseEntity<List<Map<String, Object>>> questionList(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = new HashMap<>();
		Map<String, Object>link; 
		
		int id = (int) request.getAttribute("id");
		
		params.put("_switch", "question_list");
		params.put("_user_id", id);
		list = helpProcedureService.execuHelpProcedure(params);
		
		for(int i=0; i<list.size(); i++) {
			link = new HashMap<String, Object>();  

			link.put("rel", "questionDetail");
			link.put("href", "/help/question?questionId=" + list.get(i).get("id"));
			link.put("method ", "get");
			
			Map<String, Object> temp = new HashMap<>();
			temp.put("id", list.get(i).get("id"));
			temp.put("title",  list.get(i).get("title"));
			temp.put("createdAt",  list.get(i).get("created_at"));
			temp.put("doneFlag", list.get(i).get("done_flag"));
			temp.put("_link", link);
			
			list.set(i, temp);
		}

		response.setHeader("Links",	"</help/question>; rel=\"questionPage\"");
		
		if (params.get("result_set").equals("200")) {
				return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.OK);// 200
		}else {
			return new ResponseEntity<List<Map<String, Object>>>(list, HttpStatus.NOT_FOUND);// 404
		}
	
	}	

	/**
	 * 문의글 상세 페이지
	 * author : 이종현
	 * date : 2021-01-11
	 */
	@GetMapping(path = "/question")
	public ResponseEntity<Map<String, Object>> getQuestion(@RequestParam int questionId, HttpServletResponse response){
		Map<String, Object> params = null;
		Map<String, Object> map = null;
		try {
			params = new HashMap<String, Object>();
			map = new HashMap<String, Object>();
			params.put("_switch", "question_info");
			params.put("_id", questionId);
			map = helpProcedureService.execuHelpProcedureMap(params);
			
			response.setHeader("Links", "</help/question>;rel=\"questionPage\"");
		} catch (Exception e) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	/**
	 * 문의글 작성
	 * author : 이종현
	 * date : 2021-01-11
	 */
	@PostMapping(path = "/question")
	public ResponseEntity<Map<String, Object>> writeQuestion(@RequestBody Map<String, Object> body, HttpServletResponse response){
		Map<String, Object> params = null;
		try {
			params = new HashMap<String, Object>();
			params.put("_switch", "question_write");
			params.put("_id", 4); //작정자 아이디는 일단 임의로 지정함
			params.put("_title", body.get("title"));
			params.put("_content", body.get("content"));
			helpProcedureService.execuHelpProcedureMap(params);
			
			response.setHeader("Links", "</help/question-list>;rel=\"myQuestionList\"");
		} catch (Exception e) {
			return new ResponseEntity<Map<String,Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String,Object>>(HttpStatus.OK);
	}

}
