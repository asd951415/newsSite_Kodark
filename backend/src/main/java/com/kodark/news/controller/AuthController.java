package com.kodark.news.controller;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.kodark.news.dto.Mail;

import com.kodark.news.service.AuthProcedureService;
import com.kodark.news.service.MailService;
import com.kodark.news.utils.PasswordEncoderImpl;
import com.kodark.news.utils.Util;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

	Environment env;
	MailService mailService;
	AuthProcedureService authProcedureService;
	Util util;
	PasswordEncoderImpl passwordEncoder;

	@Autowired
	public AuthController(Environment env, MailService mailService, AuthProcedureService authProcedureService,
			Util util, PasswordEncoderImpl passwordEncoder) {
		this.env = env;
		this.mailService = mailService;
		this.authProcedureService = authProcedureService;
		this.util = util;
		this.passwordEncoder = passwordEncoder;
	}

	/*
	 * title : 인증번호 요청 
	 * desc : 요청한 이메일이 가입된 이메일인지 확인하고, 그렇지 않다면 인증번호를 보낸다. 
	 * author : 류제욱
	 * date : 2020-01-06
	 */
	@PostMapping
	public ResponseEntity<String> auth(@RequestBody Map<String, Object> body) {

		String email = (String) body.get("email");
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "create_auth");
		params.put("_email", email);
		authProcedureService.execuAuthProcedure(params);

		if (params.get("result_set").equals("success")) {
			Mail mail = new Mail();
			mail.setMailFrom(env.getProperty("email.username"));
			mail.setMailTo((String) params.get("_email"));
			mail.setMailSubject("The Kodark Times's verification code");
			mail.setMailContent("<h1>Kodark Times's verification code</h1><span>code :</span><span>"
					+ (String) params.get("_auth_string") + "</span>");

			mailService.sendMail(mail);

			return new ResponseEntity<>(HttpStatus.CREATED); // 201
		} else if (params.get("result_set").equals("conflict")) {
			return new ResponseEntity<>(HttpStatus.CONFLICT); // 409
		} else if (params.get("result_set").equals("not content")) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 409
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
		}
	}

	/**
	 * verify 
	 * 작성자 : 최윤수 
	 * 작성일 : 2020-12-27
	 */
	@PatchMapping(path = "/verify")
	public ResponseEntity<String> verify(@RequestBody Map<String, Object> body, HttpServletResponse response) {
		String _auth_string = (String) body.get("authString");
		String email = (String) body.get("email");
		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "confirm_verify");
		params.put("_email", email);
		params.put("_auth_string", _auth_string);
		authProcedureService.execuAuthProcedure(params);
		
		if (params.get("result_set").equals("success")) {
			response.setHeader("Links", "</users/sign-up>; rel='self',"
								+ "</ko/auth/signIn>; rel='next'");
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);// 204
		} else if (params.get("result_set").equals("fail")) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);// 401
		} else if (params.get("result_set").equals("expiration")) {
			return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);// 408
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);// 500
		}

	}

	/*
	 * title : 로그인
	 * desc : 가입된 이메일인지 확인하고, 비밀번호를 비교한 후 jwt를 발급한다. 
	 * author : 류제욱 
	 * date : 2020-01-06
	 */
	@PostMapping(path = "/sign-in")
	public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, Object> body,
			HttpServletResponse response) {
		String email = (String) body.get("email");
		String pwd = (String) body.get("pwd");

		Map<String, Object> params = new HashMap<>();
		params.put("_switch", "sign_in");
		params.put("_email", email);
		authProcedureService.execuAuthProcedure(params);

		if (params.get("result_set").equals("success")) {
			String encodedPwd = (String) params.get("_pwd");

			if (passwordEncoder.matches(pwd, encodedPwd)) {
				Cookie cookie = util.makeJwtCookie("jwt", params);

				response.addCookie(cookie);
				response.setHeader("Links", "</auth/sign-in>; rel='self', </>; rel='next'");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
			}
		}

		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401
	}

	/*
	 * title : 로그아웃 
	 * desc : jwt 쿠키를 제거한다. 
	 * author : 류제욱 
	 * date : 2020-01-06
	 */
	@DeleteMapping(path = "/sign-out")
	public ResponseEntity<Map<String, Object>> signOut(HttpServletResponse response) {
		Cookie jwt = new Cookie("jwt", "");
		jwt.setMaxAge(0);
		jwt.setHttpOnly(true);
		jwt.setPath("/");
		response.addCookie(jwt);

		return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
	}

	/*
	 * title : 구글 oauth 
	 * desc : 구글 oauth 요청을 받고 구글 로그인 link를 보낸다. 
	 * author : 류제욱 
	 * date : 2020-01-07
	 */
	@PostMapping(path = "/google")
	public ResponseEntity<Map<String, Object>> google(HttpServletResponse response) {
		String link = "<https://accounts.google.com/o/oauth2/v2/auth?" + "client_id="
				+ env.getProperty("oauth.google.id") + "&redirect_uri=http://localhost:8090/auth/google/redirect"
				+ "&response_type=code" + "&scope=email"
//			        + "&scope=email%20profile%20openid"
//			        + "&scope=https://www.googleapis.com/auth/userinfo.email
				+ "&access_type=offline>";
		response.setHeader("Links", link + "; rel=\"next\"");
		return new ResponseEntity<>(HttpStatus.FOUND); // 302
	}

	/*
	 * title : 구글 oauth redirect 
	 * desc : 구글로그인 후 토큰을 받고 회원가입 및 로그인 처리를 한다. 
	 * author : 류제욱
	 * 작성일 : 2020-01-08
	 */
	@GetMapping(path = "/google/redirect")
	public void googleRedirect(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "error", required = false) String error, HttpServletResponse response)
			throws IOException {
		if (error != null && error.equals("access_denied")) {
			response.sendRedirect("http://localhost:8081/ko/auth/signIn");
			return;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", code);
		params.add("client_id", env.getProperty("oauth.google.id"));
		params.add("client_secret", env.getProperty("oauth.google.secret"));
		params.add("redirect_uri", "http://localhost:8090/auth/google/redirect");
		params.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(params, headers);

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create("https://www.googleapis.com/oauth2/v4/token");
		ResponseEntity<String> restResponse = restTemplate.postForEntity(uri, restRequest, String.class);

		String bodys = restResponse.getBody();

		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(Include.NON_NULL);

		Map<String, String> map = mapper.readValue(bodys, new TypeReference<Map<String, String>>() {
		});
		String googleJwt = map.get("id_token");
		String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
				.queryParam("id_token", googleJwt).encode().toUriString();
		String resultJson = restTemplate.getForObject(requestUrl, String.class);
		Map<String, String> userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, String>>() {
		});
		String email = userInfo.get("email");

		Map<String, Object> parameter = new HashMap<>();
		parameter.put("_switch", "oauth");
		parameter.put("_email", email);
		authProcedureService.execuAuthProcedure(parameter);
		String resultSet = (String) parameter.get("result_set");
		System.out.println(resultSet);
		if (!resultSet.equals("sign_up") || !resultSet.equals("exist")) {
			// throw error
		}
		Cookie jwt = util.makeJwtCookie("jwt", parameter);

		response.addCookie(jwt);
		response.sendRedirect("http://localhost:8081/ko/home");
	}

	/*
	 * title : 카카오 oauth 
	 * desc : 카카오 oauth 요청을 받고 카카오 로그인 link를 보낸다. 
	 * author : 류제욱 
	 * date : 2020-01-08
	 */
	@PostMapping(path = "/kakao")
	public ResponseEntity<Map<String, Object>> kakao(HttpServletResponse response) {
		String link = "<https://kauth.kakao.com/oauth/authorize?" + "response_type=code" + "&client_id="
				+ env.getProperty("oauth.kakao.id") + "&redirect_uri=http://localhost:8090/auth/kakao/redirect" + ">";
		response.setHeader("Links", link + "; rel=\"next\"");
		return new ResponseEntity<>(HttpStatus.FOUND); // 302
	}

	@GetMapping(path = "/kakao/redirect")
	public void kakaoRedirect(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "error", required = false) String error, HttpServletResponse response)
			throws IOException {
		if (error != null && error.equals("access_denied")) {
			response.sendRedirect("http://localhost:8081/ko/auth/signIn");
			return;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", code);
		params.add("client_id", env.getProperty("oauth.kakao.id"));
		params.add("client_secret", env.getProperty("oauth.kakao.secret"));
		params.add("redirect_uri", "http://localhost:8090/auth/kakao/redirect");
		params.add("grant_type", "authorization_code");

		HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(params, headers);

		RestTemplate restTemplate = new RestTemplate();
		URI uri = URI.create("https://kauth.kakao.com/oauth/token");
		ResponseEntity<String> restResponse = restTemplate.postForEntity(uri, restRequest, String.class);

		String bodys = restResponse.getBody();

		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(Include.NON_NULL);

		Map<String, String> map = mapper.readValue(bodys, new TypeReference<Map<String, String>>() {
		});
		String accessToken = map.get("access_token");
		headers.set("Authorization", accessToken);

		uri = URI.create("https://kauth.kakao.com/v2/user/me");
		restRequest = new HttpEntity<>(headers);

		ResponseEntity<String> restResponse2 = restTemplate.exchange(uri, HttpMethod.GET, restRequest, String.class);
		Map<String, Object> map2 = mapper.readValue(restResponse2.getBody(), new TypeReference<Map<String, Object>>() {
		});
		System.out.println(map2);
	}
}
