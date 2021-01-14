package com.kodark.news.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.kodark.news.mappers.UsersProcedureMapper;
import com.kodark.news.service.AuthProcedureService;
import com.kodark.news.service.MailService;
import com.kodark.news.service.UsersProceduerService;
import com.kodark.news.utils.PasswordEncoderImpl;
import com.kodark.news.utils.Util;

@RestController
@RequestMapping(path = "/users")
public class UserController {

	Environment env;
	MailService mailService;
	AuthProcedureService authProcedureService;
	UsersProceduerService usersProcedureService;
	PasswordEncoderImpl passwordEncoder;
	Util util;

	@Autowired
	public UserController(Environment env, MailService mailService, AuthProcedureService authProcedureService,
			UsersProceduerService usersProcedureService, PasswordEncoderImpl passwordEncoder, Util util) {
		this.env = env;
		this.mailService = mailService;
		this.authProcedureService = authProcedureService;
		this.usersProcedureService = usersProcedureService;
		this.passwordEncoder = passwordEncoder;
		this.util = util;
	}

	/**
	 * title : 로그인 정보
	 * desc :  
	 * author : 류제욱 
	 * date :
	 */
	@GetMapping
	public ResponseEntity<Map<String, Object>> userInfo(HttpServletRequest request, HttpServletResponse response) {
		int id = (int) request.getAttribute("id");
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "user_info");
		params.put("_id", id);
		usersProcedureService.execuUsersProcedure(params);
		String resultSet = (String) params.get("result_set");
		if (resultSet.equals("success")) {
			String auth = (String)params.get("_auth");
			Map<String, Object> map = new HashMap<>();
			map.put("email", params.get("_email"));
			map.put("auth", auth);
			
			if(auth.equals("user")) {
				response.setHeader("Links",
						"</users/my-page>; rel=\"myPage\""
								+ ", </users/my-page/detail>; rel=\"userDetail\""
								+ ", </users/my-page/subscribed-list>; rel=\"subscribedList\""
								+ ", </users/sign-out>; rel=\"signOut\"");
			}
			else if(auth.equals("reporter")) {
				response.setHeader("Links",
						"</reporters>; rel=\"reporterInfo\""
								+ ", </reporters/article>; rel=\"reporterArticle\""
								+ ", </users/sign-out>; rel=\"signOut\"");
			}
			else if(auth.equals("admin")) {
				response.setHeader("Links",
						"</admin/statistics>; rel=\"statistics\""
								+ ", </admin/users>; rel=\"userList\""
								+ ", </admin/reporters>; rel=\"reporterList\""
								+ ", </users/sign-out>; rel=\"signOut\"");
			}
			return new ResponseEntity<>(map, HttpStatus.OK);// 200
		} else
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
	}
	

	/**
	 * title : 회원 탈퇴(33)
	 * desc : 
	 * author : 최현지 
	 * date : 2021-01-12
	 */
	@DeleteMapping
	public ResponseEntity<Map<String, Object>> delete(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params= new HashMap<String, Object>();
		
		int id = (int)request.getAttribute("id");
		//int id = 3;
		
		params.put("_switch", "delete");
		params.put("_id", id);

		usersProcedureService.execuUsersProcedure(params);
		
		response.setHeader("Links", "</users/my-page>; rel=\"self\"," + "</>; rel=\"next\"");
				
		if(params.get("result_set").equals("204")){
			return  new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404
		}
	}

	/**
	 * title : 마이페이지(28)
	 * desc : 
	 * author : 최현지 
	 * date : 2021-01-07
	 */
	@GetMapping(path = "/my-page")
	public ResponseEntity<Map<String, Object>> myPage(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> temp = new HashMap<String, Object>();
		List<Map<String, Object>> linkList = new ArrayList<Map<String, Object>>();
		Map<String, Object> link1;
		Map<String, Object> link2;
		Map<String, Object> link3;

		int id = (int)request.getAttribute("id");

		params.put("_switch", "my_page");
		params.put("_id", id);
		usersProcedureService.myPage(params);

		String email = (String) params.get("_email");
		String auth = (String) params.get("_auth");

		temp.put("email", email);
		temp.put("auth", auth);

		link1 = new HashMap<String, Object>();
		link2 = new HashMap<String, Object>();
		link3 = new HashMap<String, Object>();

		link1.put("rel", "deleteUser");
		link1.put("href", "/users");
		link1.put("method ", "delete");
		linkList.add(link1);

		link2.put("rel", "editEmail");
		link2.put("href", "/auth");
		link2.put("method ", "patch");
		linkList.add(link2);

		link3.put("rel", "validation");
		link3.put("href", "/auth/verify");
		link3.put("method ", "patch");
		linkList.add(link3);

		temp.put("_links", linkList);
		
		response.setHeader("Links",
						"</users/my-page>; 						rel=\"self\","
						+ "</users/my-page/detail>;				rel=\"userDetail\","
						+ "</users/my-page/subscribed-list>;	rel=\"subscribedList\","
						+ "</auth>;  							rel=\"eidtEmail\","
						+ "</auth/verify>;  					rel=\"validation\","
						+ "</users>; 							rel=\"deleteUser\"");

		
		if (params.get("result_set").equals("200")) {
				return new ResponseEntity<Map<String, Object>>(temp, HttpStatus.OK);// 200
		}else {
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);// 404
		}
	}
	

	// 회원가입
	@PostMapping(path = "/sign-up")
	public ResponseEntity<String> signUp(@RequestBody Map<String, Object> body, HttpServletResponse response) {
		String email = (String) body.get("email");
		String pwd = (String) body.get("pwd");
		String encodedPwd = passwordEncoder.encode(pwd);
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "sign_up");
		params.put("_email", email);
		params.put("_pwd", encodedPwd);
		authProcedureService.execuAuthProcedure(params);

		response.setHeader("Links", "</users/sign-up>; rel=\"self\"," + "</en/auth/signIn>; rel=\"next\"");
		if (params.get("result_set").equals("success"))
			return new ResponseEntity<>(HttpStatus.CREATED);
		else
			return new ResponseEntity<>(HttpStatus.CONFLICT);

	}
	
	@PatchMapping(path = "/email")
	public ResponseEntity<String> emailUpdate(@RequestBody Map<String, Object> body, HttpServletRequest request) {
		int id = (int)request.getAttribute("id");
		String verifPwd = (String) body.get("verifPwd");
		String email = (String) body.get("email");
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "user_info");
		params.put("_id", id);
		usersProcedureService.execuUsersProcedure(params);
		String encodedPwd = (String) params.get("_pwd");
		
		if(encodedPwd != null && !passwordEncoder.matches(verifPwd, encodedPwd))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
		
		params.put("_switch", "update_email");
		params.put("_id", id);
		params.put("_email", email);
		usersProcedureService.execuUsersProcedure(params);
		String resultSet = (String)params.get("result_set");
		
		if(resultSet.equals("success")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
		} else if(resultSet.equals("conflict")) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}
	

	// 비밀번호 수정
	@PatchMapping(path = "/pwd")
	public ResponseEntity<String> pwdUpdate(@RequestBody Map<String, Object> body, HttpServletRequest request) {
		int id = (int)request.getAttribute("id");
		String pwd = (String) body.get("pwd");
		String verifPwd = (String) body.get("verifPwd");
		
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "user_info");
		params.put("_id", id);
		usersProcedureService.execuUsersProcedure(params);
		String encodedPwd = (String) params.get("_pwd");
		
		if(encodedPwd != null && !passwordEncoder.matches(verifPwd, encodedPwd))
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
		
		pwd = passwordEncoder.encode(pwd);
		params.put("_switch", "update_password");
		params.put("_id", id);
		params.put("_pwd", pwd);
		usersProcedureService.execuUsersProcedure(params);
		

		if (params.get("result_set").equals("204")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204
		} else if (params.get("result_set").equals("401")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);// 401
		} else if (params.get("result_set").equals("404")) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);// 404
		} else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
	}
	
	/**
	 * 댓글 입력
	 * 작성 날짜 : 2021-01-12 
	 * 작성자 : 이종현
	 */
	@PostMapping(path = "/comment")
	public ResponseEntity<Map<String, Object>> writeComment(@RequestParam("articleId") int articleId,
			@RequestBody Map<String, Object> body) {
		Map<String, Object> params = null;
		try {
			params = new HashMap<String, Object>();
			params.put("_switch", "comment_write");
			params.put("_id", articleId);
			params.put("_email", body.get("email"));
			params.put("_content", body.get("content"));

			usersProcedureService.execuCommentMapProcedure(params);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}
	
	/**
	 * 대댓글 입력
	 * 작성 날짜 : 2021-01-08 
	 * 작성자 : 이종현
	 */
	@PostMapping(path = "/comment/reply")
	public ResponseEntity<Map<String, Object>> writeCommentReply(@RequestParam("commentId") int commentId,
			@RequestBody Map<String, Object> body) {
		Map<String, Object> params = null;
		try {
			params = new HashMap<String, Object>();
			params.put("_switch", "comment_reply");
			params.put("_id", commentId);
			params.put("_email", body.get("email"));
			params.put("_content", body.get("content"));

			usersProcedureService.execuCommentMapProcedure(params);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}
	/**
	 * 댓글 신고
	 * 설명 : 댓글을 신고한다.
	 * 작성 날짜 : 2021-01-10 
	 * 작성자 : 이종현
	 */
	@PostMapping(path = "/comment/report")
	public ResponseEntity<String> commentReport(
			@RequestParam("commentId") int commentId, @RequestBody Map<String,Object> body){
		Map<String, Object> params = null;
		try {
			
			params = new HashMap<String, Object>();
			params.put("_switch", "comment_report");
			params.put("_id", commentId);
			params.put("_email", body.get("email"));
			params.put("_reason", body.get("reason"));
			usersProcedureService.execuCommentMapProcedure(params);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	/**
	 * title : 37.구독취소
	 * desc : 구독취소
	 * author : 최윤수
	 * date : 2021-01-11
	 * @param : reporterId
	 */
	@DeleteMapping(path = "/subscription")
	public ResponseEntity<String> cancelSubscription(@RequestParam int reporterId
			//,HttpServletRequest request
			){
		Map<String, Object> params = new HashMap<>();		
		//int id = (int) request.getAttribute("id");
		int id = 4;//test용
		params.put("_switch", "cancel_sub");
		params.put("_id", id);
		params.put("_reporter_id", reporterId);
		try {
			usersProcedureService.execuUsersProcedure(params);
			System.out.println("params:"+params);
			if(params.get("result_set").equals("404")) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500
		}		
		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);//205
	}
	/**
	 * title : 36.newsletter toggle
	 * desc :
	 * author : 최윤수
	 * date : 2021-01-11
	 * @param : reporterId
	 */
	@PatchMapping(path = "/reporters/letter-accepted")
	public ResponseEntity<String> newsletterToggle(@RequestParam int reporterId
			//,HttpServletRequest request
			){
		Map<String, Object> params = new HashMap<>();		
		System.out.println("start");
		//int id = (int) request.getAttribute("id");
		int id = 5;//test용
		params.put("_switch", "toggle");
		params.put("_id", id);
		params.put("_reporter_id", reporterId);
		try {
			usersProcedureService.execuUsersProcedure(params);
			System.out.println("params:"+params);
			if(params.get("result_set").equals("404")) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);//500
		}		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);//204
	}
	
	/**
	 * title : 35.구독목록
	 * desc : 
	 * author : 최윤수
	 * date : 2021-01-11
	 * 
	 */
	@GetMapping(path = "/my-page/subscribed-list")
	public ResponseEntity<List<Map<String,Object>>> subscribeList(HttpServletResponse response){
		int id = 6;	//test
		List<Map<String,Object>>list = new ArrayList<>();
		List<Map<String,Object>>link;
		Map<String, Object> params = new HashMap<>();
		Map<String, Object> map1;
		Map<String, Object> map2;	
		Map<String, Object> map3;	
		Map<String, Object> map4;
		params.put("_switch", "sub_list");
		params.put("_id", id);				
		list = usersProcedureService.subList(params);
		if(params.get("result_set").equals("404")) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);//404
		}else if(params.get("result_set").equals("200")){
			try {
				for(int i=0;i<list.size();i++) {
					link = new ArrayList<>();
					map1 = new HashMap<>();
					map2 = new HashMap<>();
					map3 = new HashMap<>();	
					map4 = new HashMap<>();
					map1.put("id", list.get(i).get("reporter_id"));
					map1.put("email", list.get(i).get("email"));
					map1.put("nickName", list.get(i).get("nick_name"));
					map1.put("name", list.get(i).get("name"));
					map1.put("image", list.get(i).get("image"));
					map1.put("letterAccepted", list.get(i).get("letter_accepted"));
					map2.put("rel", "reporterArticles");
					map2.put("href", "/users/reporter/article");
					map2.put("method", "get");
					map3.put("rel", "letterAccept");
					map3.put("href", "/users/reporters/letter-accepted");
					map3.put("method", "patch");
					map4.put("rel", "deleteSubscription");
					map4.put("href", "/users/subscription");
					map4.put("method", "delete");
					link.add(map2);
					link.add(map3);
					link.add(map4);
					map1.put("_links", link);
					list.set(i, map1);						
				}
			} catch (Exception e) {
				return new ResponseEntity<>(HttpStatus.OK);//500
			}
		}	
		response.setHeader("links",	  "</users/my-page>; rel=\"myPage\",\r\n"
									+ "</users/my-page/detail>; rel=\"userDetail\",\r\n"
									+ "</users/my-page/subscribed-list>; rel=\"self\",\r\n"
									+ "</users/detail>; rel=\"editUserDetail\",\r\n"
									+ "</users/reporters/article>; rel=\"reporterArticles\",\r\n"
									+ "</users/reporters/letter-accepted>; rel=\"letterAccept\",\r\n"
									+ "</users/subscription>; rel=\"deleteSubscription\"");	
		return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);//200
	}
	
	/**
	 * title : 26.기자구독하기
	 * desc : 
	 * author : 최윤수
	 * date :2021-01-12
	 * @param : userId
	 */
	@PostMapping(path = "/subscription")
	public ResponseEntity<String> subscription(@RequestBody Map<String, Object> body){
		int reporterId =Integer.valueOf((String)body.get("id"));//기자아이디
		int id = 4; //userId httpservletrequest
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "subs");
		params.put("_id", id);
		params.put("_reporter_id", reporterId);
		usersProcedureService.execuUsersProcedure(params);
		if(params.get("result_set").equals("409")) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);//409
		}else if(params.get("result_set").equals("201")) {
			return new ResponseEntity<>(HttpStatus.CREATED);//201
		}else
			return new ResponseEntity<>(HttpStatus.CREATED);//500		
	}
  
  /*
	 * 댓글 추천/비추천
	 * 설명 : 댓글을 추천/비추천 시 해당 내역을 업데이트 후 해당 댓글의 추천/비추천 수량을 가져온다.
	 * 작성 날짜 : 2021-01-10 
	 * 작성자 : 이종현
	 */
	@PostMapping(path = "/comment/reputation")
	public ResponseEntity<List<Map<String, Object>>> newReputation(
			@RequestParam("commentId") int commentId, @RequestBody Map<String,Object> body){
		List<Map<String, Object>> list = null;
		Map<String, Object> params = null;
		try {
			list = new ArrayList<Map<String,Object>>();
			params = new HashMap<String, Object>();
			params.put("_switch", "comment_reputation");
			params.put("_id", commentId);
			params.put("_email", body.get("email"));
			params.put("_reputation", body.get("reputation"));
		
			list = usersProcedureService.execuCommentListProcedure(params);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
	}
	/**
	 * 특정 회원의 모든 댓글 보기
	 * 설명 : 특정 회원의 Id를 입력 받으면 해당 회원의 모든 댓글 목록을 가져옴
	 * 작성 날짜 : 2021-01-10 
	 * 작성자 : 이종현
	 */
	@GetMapping(path = "/comment")
	public ResponseEntity<Map<String,Object>> testMapper(@RequestParam("userId") int userId){
		List<Map<String,Object>> list = null;
		Map<String, Object> map = null;
		Map<String, Object> temp = null;
		Map<String, Object> params = null;
		try {
			list = new ArrayList<Map<String,Object>>();
			map = new HashMap<String, Object>();
			temp = new HashMap<String, Object>();
			params = new HashMap<String, Object>();
			
			params.put("_switch", "comment_info_id");
			params.put("_userId", userId);
			temp = usersProcedureService.execuCommentMapProcedure(params);
			map.put("id", temp.get("id"));
			map.put("email", temp.get("email"));
			map.put("nickName", temp.get("nick_name"));
			map.put("local", temp.get("local"));
			params = new HashMap<String, Object>();
			params.put("_switch", "comment_info_list");
			params.put("_userId", userId);
			list = usersProcedureService.execuCommentListProcedure(params);
			map.put("comments", list);
			
			
		} catch (Exception e) {
			
		}
		
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	/**
	 * 감정 표현 선택
	 * 설명 : 기사글의 감정표현을 선택시 articleTB의 값을 업데이트를 하고 업데이트된 결과값을 반환한다.
	 * 작성 날짜 : 2021-01-12
	 * 작성자 : 이종현
	 */
	@PutMapping(path = "/emotion")
	public ResponseEntity<List<Map<String, Object>>> chooseEmotion(
			@RequestParam("articleId") int articleId, @RequestParam("emotion") String emotion){
		List<Map<String, Object>> list = null;
		Map<String, Object> params = null;
		
		try {
			list = new ArrayList<Map<String,Object>>();
			params = new HashMap<String, Object>();
			params.put("_switch","choose_emotion");
			params.put("_id", articleId);
			params.put("_userId", 3); //임시 이메일
			params.put("_emotion", emotion);
			list = usersProcedureService.execuUsersProcedureList(params);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<Map<String,Object>>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Map<String,Object>>>(list,HttpStatus.OK);
	}

	/**
	 * 회원정보
	 * 작성 날짜 : 2021-01-10
	 * 작성자 : 이푸름
	 */
	
	@GetMapping(path = "/my-page/detail")
	public ResponseEntity<Map<String, Object>> myPageDetail(HttpServletResponse response, HttpServletRequest request) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> link1;
		
		int id = (int)request.getAttribute("id");
		params.put("_id", id);

		params.put("_switch", "mypage_detail");
		usersProcedureService.execuUsersProcedure(params);
		
		String NickName= (String) params.get("_nickName");
		String name= (String) params.get("_name");
		String local= (String) params.get("_local");
		String birth = (String) params.get("_birth");
	
		String formatBirth = birth.substring(0, 10);
		String gender= (String) params.get("_gender");
		String image = (String) params.get("_image");
//		Date beforeFormatBirth= (Date) params.get("_birth"); 
//		String pattern = "yyyy-MM-dd";
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//		String birth = simpleDateFormat.format(beforeFormatBirth);
		
		Map<String, Object> result = new HashMap<String, Object>();
		link1 = new HashMap<String, Object>();
		link1.put("rel", "editUserDetail");
		link1.put("href", "/users/detail");
		link1.put("method ", "put");
		
		result.put("nickName", NickName);
		result.put("name", name);
		result.put("local", local);
		result.put("birth", formatBirth);
		result.put("gender", gender);
		result.put("image", image);
		result.put("_link", link1);
		

		response.setHeader("Links",
						"</users/my-page>; 						rel=\"myPage\","
						+ "</users/my-page/detail>;				rel=\"self\","
						+ "</users/my-page/subscribed-list>;	rel=\"subscribedList\","
						+ "</users/detail>;  					rel=\"editUserDetail\","
						);

		return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);// 200
	}
	
	/**
	 * 회원정보수정 34번
	 * 작성 날짜 : 2021-01-11
	 * 작성자 : 이푸름
	 */
	
	@PostMapping(path = "/detail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity <String> detailUpdate(MultipartHttpServletRequest multiRequest, HttpServletRequest request, HttpServletResponse response) throws ParseException{
		MultipartFile imageFile = multiRequest.getFile("image");
		String fileName = util.saveImage(imageFile, request);
		String name = request.getParameter("name");
		String nickName = request.getParameter("nickName");
		String local = request.getParameter("local");
		String gender = request.getParameter("gender");
		String birth = request.getParameter("birth");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("_name", name);
		params.put("_nickName", nickName);
		params.put("_local", local);
		params.put("_gender", gender);
		params.put("_birth", birth);
		params.put("_image", fileName);
		
		int id = (int)request.getAttribute("id");
		params.put("_id", id);
		params.put("_switch", "user_update");
		usersProcedureService.execuUsersProcedure(params);
		response.setHeader("Links",
				 	"</users/my-page/detail>;	rel=\"next\","
		);
		
		if (params.get("result_set").equals("200")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
		}
		else if(params.get("result_set").equals("409")) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
		}
		else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}

}

