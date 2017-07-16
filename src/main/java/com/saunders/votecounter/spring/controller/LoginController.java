/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saunders.votecounter.vote.service.LoginService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest resource for login
 *
 * @author davidsa1
 *
 */
@RestController
@RequestMapping("/")
public class LoginController {

	@Autowired
	LoginService loginService;

	/**
	 * Persists the userId and websocket session Id for use in broadcasting vote
	 * success/ failure responses
	 *
	 * @param userId
	 *            email-address
	 * @param sessionId
	 *            websocket session Id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "users/{userId}/sessions/{sessionId}")
	@ApiOperation(value = "Register user", notes = "Persists the userId and websocket sessionId for use in broadcasting vote success/ failure responses")
	@ApiResponses({ @ApiResponse(code = 201, message = "Request created") })
	public ResponseEntity<?> register(
			@ApiParam(value = "User's e-mail address", required = true) @PathVariable String userId,
			@ApiParam(value = "Id of the websocket session", required = true) @PathVariable String sessionId) {

		loginService.register(sessionId, userId);
		return new ResponseEntity<>("Request created", HttpStatus.CREATED);
	}

}
