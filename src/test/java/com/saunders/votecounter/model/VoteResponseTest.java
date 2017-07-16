/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VoteResponseTest {
	private VoteResponse voteResponse;

	@Before
	public void setup() {
		voteResponse = new VoteResponse();
	}

	@Test
	public void verifyGettersAndSetters() {
		String candidateId = UUID.randomUUID().toString();
		String message = UUID.randomUUID().toString();
		VoteResult voteResult = VoteResult.SUCCESS;
		List<String> currentVotes = new ArrayList<>();
		currentVotes.add(candidateId);

		voteResponse.setCandidate(candidateId);
		voteResponse.setCurrentVotes(currentVotes);
		voteResponse.setMessage(message);
		voteResponse.setVoteResult(voteResult);

		assertEquals("candidates not equal", candidateId, voteResponse.getCandidate());
		assertEquals("currentVotes not equal", currentVotes, voteResponse.getCurrentVotes());
		assertEquals("message not equal", message, voteResponse.getMessage());
		assertEquals("currentVotes not equal", voteResult, voteResponse.getVoteResult());
	}

}
