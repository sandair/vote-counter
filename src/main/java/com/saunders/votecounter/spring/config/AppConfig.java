/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import com.saunders.votecounter.repository.UserRepository;
import com.saunders.votecounter.repository.impl.UserRepositoryImpl;
import com.saunders.votecounter.vote.service.CandidateService;
import com.saunders.votecounter.vote.service.LoginService;
import com.saunders.votecounter.vote.service.VoteService;
import com.saunders.votecounter.vote.service.impl.CandidateServiceImpl;
import com.saunders.votecounter.vote.service.impl.LoginServiceImpl;
import com.saunders.votecounter.vote.service.impl.VoteServiceImpl;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.saunders.votecounter.spring")
@PropertySource(value = "classpath:application.properties")
public class AppConfig extends WebMvcConfigurerAdapter {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public UserRepository userRepository() {
		return new UserRepositoryImpl();
	}

	@Bean
	public LoginService loginService() {
		return new LoginServiceImpl();
	}

	@Bean
	public VoteService voteService(@Value("#{'${candidates.ids}'.split(',')}") List<String> candidateIds) {
		return new VoteServiceImpl(candidateIds);
	}

	@Bean
	public CandidateService candidateService() {
		return new CandidateServiceImpl();
	}

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
