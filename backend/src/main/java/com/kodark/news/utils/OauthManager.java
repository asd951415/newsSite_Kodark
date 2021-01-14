package com.kodark.news.utils;

import java.net.URI;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Component
public class OauthManager {
	
	@Autowired
	Environment env;
	
	public Map<String, String> getOauthToken(String company, String code) throws JsonMappingException, JsonProcessingException {
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("client_id", env.getProperty("oauth." + company + ".id"));
		params.add("client_secret", env.getProperty("oauth." + company + ".secret"));
		params.add("redirect_uri", "http://localhost:8090/auth/" + company + "/redirect");
		params.add("code", code);
		params.add("grant_type", "authorization_code");
		URI uri = URI.create(env.getProperty("oauth." + company + ".url.token"));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(params, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> restResponse = restTemplate.postForEntity(uri, restRequest, String.class);
		String bodys = restResponse.getBody();

		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		mapper.setSerializationInclusion(Include.NON_NULL);
		Map<String, String> map = mapper.readValue(bodys, new TypeReference<Map<String, String>>() {});
		
		return map;
	}
	
	public Map<String, Object> getUserInfo(String company, String token) throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		RestTemplate restTemplate = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> userInfo = null;
		if(company.equals("google")) {
			String requestUrl = UriComponentsBuilder.fromHttpUrl("https://oauth2.googleapis.com/tokeninfo")
					.queryParam("id_token", token).encode().toUriString();
			String resultJson = restTemplate.getForObject(requestUrl, String.class);
			
			userInfo = mapper.readValue(resultJson, new TypeReference<Map<String, Object>>() {});
		} else if(company.equals("kakao")) {
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.set("Authorization", "Bearer " + token);
			HttpEntity<MultiValueMap<String, String>> restRequest = new HttpEntity<>(headers);
			UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://kapi.kakao.com/v2/user/me");
			HttpEntity<String> response2 = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, restRequest, String.class);
			
			userInfo = mapper.readValue(response2.getBody(), new TypeReference<Map<String, Object>>() {});
			
		}
		
		return userInfo;
	}
}
