/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.vote.service;

import java.util.List;
import java.util.Map;

/**
 * Service to allow for the casting and retrieval of votes
 *
 * @author davidsa1
 *
 */
public interface VoteService {

	/**
	 * Cast a user's vote for a particular candidate
	 *
	 * @param userId
	 *            user's email address
	 * @param sessionId
	 *            websocket sessionId
	 * @param candidateId
	 *            candidate to vote for
	 */
	void vote(String userId, String sessionId, String candidateId);

	/**
	 * Get the count of votes for the candidate
	 *
	 * @param candidateId
	 *            use "all" to get all candidates
	 *
	 * @return Map of candidates and the number of votes cast for each one
	 */
	Map<String, Integer> getResults(String candidateId);

	/**
	 * Get a list of the candidates the user has already voted for
	 *
	 * @param userId
	 *            user who voted
	 * @return list of candidates
	 */
	List<String> getUserVotes(String userId);

}
