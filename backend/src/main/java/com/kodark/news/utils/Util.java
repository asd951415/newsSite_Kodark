package com.kodark.news.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Util {
	@Autowired
	JwtManager jwtManager;
	
	public Cookie makeJwtCookie(String key, Map<String, Object> params) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", params.get("_id"));
		claims.put("auth", params.get("_auth"));
		String token = jwtManager.createJwt("userInfo", claims, (100 * 1000 * 60));			
		
        Cookie cookie = new Cookie(key, token);
        cookie.setMaxAge(100 * 24 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        
        return cookie;
	}
	
	public String saveImage(MultipartFile imageFile, HttpServletRequest request) {
		String fileName = imageFile.getOriginalFilename();
		
		if (fileName != null && !fileName.equals("")) {
			fileName = "/img/" + System.currentTimeMillis() + "_" + fileName;
			ServletContext context = request.getServletContext();
			String path = context.getRealPath("");
			
			try (	FileOutputStream fos = new FileOutputStream(path + fileName);
					InputStream is = imageFile.getInputStream();) {
				
				int readCount = 0;
				byte[] buffer = new byte[1024];
				while((readCount = is.read(buffer)) != -1) {
					fos.write(buffer, 0, readCount);
				}
				
			} catch(Exception e) {
				throw new RuntimeException("file save error");
			}
			
		} else {
			throw new RuntimeException("file save error");
		}
		
		return fileName;
	}
}
