/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.saunders.votecounter.vote.service.VoteService;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest resource for voting
 *
 * @author davidsa1
 *
 */
@RestController
@RequestMapping("/users/{userId}/sessions/{sessionId}/votes")
public class VoteController {

	@Autowired
	VoteService voteService;

	@Value("#{'${candidates.ids}'.split(',')}")
	private List<String> candidateIds;

	@RequestMapping(method = RequestMethod.POST, value = "/{candidateId}")
	@ApiOperation(value = "Vote for a candidate", notes = "Votes for a candidate, with a maximum of 3 votes per user permitted")
	@ApiResponses({ @ApiResponse(code = 201, message = "Success"),
			@ApiResponse(code = 400, message = "Invalid candidate") })
	public ResponseEntity<?> vote(
			@ApiParam(value = "User's e-mail address", required = true) @PathVariable String userId,
			@ApiParam(value = "Id of the websocket session", required = true) @PathVariable String sessionId,
			@ApiParam(value = "The id of the candidate to vote for", required = true) @PathVariable String candidateId) {

		if (!candidateIds.contains(candidateId)) {
			return new ResponseEntity<>("Invalid candidate", HttpStatus.BAD_REQUEST);
		}

		voteService.vote(userId, sessionId, candidateId);

		return new ResponseEntity<>("", HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Retrieve user's votes", notes = "Retrieve a list of candidates the user had voted for")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<?> results(
			@ApiParam(value = "User's e-mail address", required = true) @PathVariable String userId) {

		List<String> votes = voteService.getUserVotes(userId);

		return new ResponseEntity<>(votes, HttpStatus.OK);
	}

}
