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

import com.saunders.votecounter.constants.ConstantStrings;
import com.saunders.votecounter.vote.service.CandidateService;
import com.saunders.votecounter.vote.service.VoteService;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Rest resource for retrieving candidate information
 *
 * @author davidsa1
 *
 */
@RestController
@RequestMapping("/users/{userId}/sessions/{sessionId}/candidates")
public class CandidateController {

	@Autowired
	CandidateService candidateService;

	@Autowired
	VoteService voteService;

	@Value("#{'${candidates.ids}'.split(',')}")
	private List<String> candidateIds;

	/**
	 * Get a list of candidates who can be voted for
	 *
	 * @return List of candidates
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get candidates", notes = "Get a list of candidates who can be voted for")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK") })
	public ResponseEntity<?> getCandidates() {

		List<String> candidates = candidateService.getCandidates();
		return new ResponseEntity<>(candidates, HttpStatus.CREATED);
	}

	/**
	 * Get the results for a particular candidate
	 *
	 * @param candidateId
	 *            candidate to get the results of, use 'all' to get results for
	 *            all candidates
	 * @return votes for the candidate/s
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{candidateId}/votes")
	@ApiOperation(value = "Retrieve candidate votes", notes = "Retrieve a summary of the votes for all candidates")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK"), @ApiResponse(code = 400, message = "Invalid candidate") })
	public ResponseEntity<?> getCandidateVotes(
			@ApiParam(value = "Candidate Id, use 'all' to get results for all candidates", required = true) @PathVariable String candidateId) {

		if (!ConstantStrings.ALL_CANDIDATES.equals(candidateId) && !candidateIds.contains(candidateId)) {
			return new ResponseEntity<>("Invalid candidate", HttpStatus.BAD_REQUEST);
		}

		Map<String, Integer> votes = voteService.getResults(candidateId);
		return new ResponseEntity<>(votes, HttpStatus.OK);
	}

}
