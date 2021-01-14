package com.kodark.news.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.kodark.news.interceptors.JwtInterceptor;
import com.kodark.news.interceptors.LogInterceptor;

/*
 * title : web mvc 환경
 * dec : servlet환경을 세팅.
 * 작성자 : 류제욱
 * 작성일 : 2020-01-06
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.kodark.news.controller", "com.kodark.news.interceptors" })
public class WebMvcContextConfiguration implements WebMvcConfigurer {
   private static final String VIEW_RESOLVER_PREFIX = "/WEB-INF/views/";
   private static final String VIEW_RESOLVER_SUFFIX = ".html";
   
   @Autowired
   private LogInterceptor logInterceptor;
   
   @Autowired
   private JwtInterceptor jwtInterceptor;
    
   @Override
   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
   }
   
   // controller를 부르지 않는 단순 데이터 요청을 처리. ex) image
   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(31556926);
      registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
      registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
      registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
   }
   
   // restcontroller에 대한 요청이아닌 vue.js 파일 요청을 처리
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
      registry.addViewController("/").setViewName("index");
      registry.addViewController("/ko/*").setViewName("index");
      registry.addViewController("/en/*").setViewName("index");
   }
   
   @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix(VIEW_RESOLVER_PREFIX);
        viewResolver.setSuffix(VIEW_RESOLVER_SUFFIX);
        return viewResolver;
   }

	// 인터셉터 : 요청과 응답을 기록하는 LogInterceptor와 인증을 검사하는 JwtInterceptor를 세팅
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(logInterceptor);
		
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/users")
			.addPathPatterns("/users/**")
			.addPathPatterns("/reporters")
			.addPathPatterns("/reporters/**")
			.addPathPatterns("/admin")
			.addPathPatterns("/admin/**")
			.excludePathPatterns("/users/sign-up");
		
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(10485760);
		return multipartResolver;
	}

    
}

