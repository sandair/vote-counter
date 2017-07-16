/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.saunders.votecounter.spring.controller.LoginController;
import com.saunders.votecounter.spring.controller.VoteController;

import java.time.LocalDate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = { LoginController.class, VoteController.class })
public class SwaggerConfig {

	@Bean
	public Docket voteCounterApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(false).apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Vote Counter API")
				.description("API that allows for logging in, casting a vote and retrieving the vote results")
				.version("1.0").build();
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", // url
				"none", // docExpansion => none | list
				"alpha", // apiSorter => alpha
				"schema", // defaultModelRendering => schema
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, // enableJsonEditor
																			// =>
																			// true
																			// |
																			// false
				true, // showRequestHeaders => true | false
				60000L); // requestTimeout => in milliseconds, defaults to null
							// (uses jquery xh timeout)
	}
}
