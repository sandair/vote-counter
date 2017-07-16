/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.model;

import java.util.List;

/**
 * Response to the client for a voting action
 * 
 * @author davidsa1
 *
 */
public class VoteResponse {

	private VoteResult voteResult;
	private String candidateId;
	private String message;
	private List<String> currentVotes;

	/**
	 * Returns the result of casting a vote, either success or fail
	 * 
	 * @return {@link com.saunders.votecounter.model.VoteResult VoteResult}
	 */
	public VoteResult getVoteResult() {
		return voteResult;
	}

	/**
	 * Sets the result of casting a vote, either success or fail
	 * 
	 * @param voteResult
	 *            {@link com.saunders.votecounter.model.VoteResult VoteResult}
	 */
	public void setVoteResult(VoteResult voteResult) {
		this.voteResult = voteResult;
	}

	/**
	 * Get the optional message to the client
	 * 
	 * @return Optional message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set a message with extra information, optional
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the candidate the vote was cast for
	 * 
	 * @return candidate Id
	 */
	public String getCandidate() {
		return candidateId;
	}

	/**
	 * Set the candidate Id the vote was cast for
	 * 
	 * @param candidate
	 */
	public void setCandidate(String candidateId) {
		this.candidateId = candidateId;
	}

	/**
	 * Get the current votes cast by the user
	 * 
	 * @return List of candidate Ids
	 */
	public List<String> getCurrentVotes() {
		return currentVotes;
	}

	/**
	 * Set the current votes cast by the user
	 * 
	 * @param currentVotes
	 */
	public void setCurrentVotes(List<String> currentVotes) {
		this.currentVotes = currentVotes;
	}

}
