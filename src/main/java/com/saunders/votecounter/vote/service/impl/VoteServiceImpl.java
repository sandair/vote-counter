/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.vote.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.saunders.votecounter.constants.ConstantStrings;
import com.saunders.votecounter.model.VoteResponse;
import com.saunders.votecounter.model.VoteResult;
import com.saunders.votecounter.vote.service.VoteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VoteServiceImpl implements VoteService {

	private static final Logger LOG = Logger.getLogger(VoteServiceImpl.class);
	private static final int POOL_SIZE = 5;
	private final ExecutorService executor;
	private final Map<String, Integer> candidateVotes = Collections.synchronizedMap(new HashMap<String, Integer>());
	private final Map<String, List<String>> userVotes = Collections
			.synchronizedMap(new HashMap<String, List<String>>());

	// private final ObjectMapper objectMapper = new ObjectMapper();

	// @Autowired
	// private WebsocketServer websocketServer;

	public VoteServiceImpl(List<String> candidateIds) {
		executor = Executors.newFixedThreadPool(POOL_SIZE);

		// initialize all candidates to 0 votes
		for (String candidateId : candidateIds) {
			candidateVotes.put(candidateId, 0);
		}

	}

	@Override
	public void vote(final String userId, final String sessionId, final String candidateId) {

		executor.execute(new Runnable() {

			@Override
			public void run() {
				if (updateUserVotes(userId, sessionId, candidateId)) {
					// while we could get sum of the votes for each candidate
					// from the userVotes map this would take time.
					// So store this in a separate map so that no processing is
					// required to return the results and a quick response time
					// can be achieved.
					updateCandidateVotes(candidateId);
				}
			}
		});

	}

	@Override
	public Map<String, Integer> getResults(String candidateId) {

		Map<String, Integer> results = new HashMap<>();

		if (ConstantStrings.ALL_CANDIDATES.equals(candidateId)) {
			// return the entire map
			results = candidateVotes;
		} else {
			// otherwise return a map with one entry, the one for this candidate
			results.put(candidateId, candidateVotes.get(candidateId));
		}
		LOG.debug(String.format("Returning results: %s", candidateVotes.toString()));
		return results;
	}

	@Override
	public List<String> getUserVotes(String userId) {
		List<String> votes = new ArrayList<>();
		synchronized (userVotes) {

			if (userVotes.containsKey(userId)) {
				votes = userVotes.get(userId);
				LOG.debug(String.format("User %s, has voted for %s", userId, StringUtils.join(votes)));
			}
		}

		return votes;
	}

	private boolean updateUserVotes(String userId, String sessionId, String candidateId) {
		synchronized (userVotes) {
			List<String> votes = getUserVotes(userId);

			if (votes.size() == 3) {
				LOG.warn(String.format("Failed to cast vote for %s on behalf of %s", candidateId, userId));
				sendFailureResponse(sessionId, candidateId, votes);
				return false;
			} else {
				sendSuccessResponse(sessionId, candidateId, votes);
			}
			votes.add(candidateId);
			userVotes.put(userId, votes);
		}
		return true;

	}

	// Increment the vote for the selected candidate by 1
	private void updateCandidateVotes(String candidateId) {
		candidateVotes.put(candidateId, candidateVotes.get(candidateId) + 1);

	}

	private void sendFailureResponse(String sessionId, String candidateId, List<String> votes) {
		VoteResponse voteResponse = new VoteResponse();
		voteResponse.setVoteResult(VoteResult.FAIL);
		voteResponse.setCandidate(candidateId);
		voteResponse.setMessage("Maximum vote limit reached");
		voteResponse.setCurrentVotes(votes);
		// try {
		// websocketServer.sendMessage(sessionId,
		// objectMapper.writeValueAsString(voteResponse));
		// } catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	private void sendSuccessResponse(String sessionId, String candidateId, List<String> votes) {
		VoteResponse voteResponse = new VoteResponse();
		voteResponse.setVoteResult(VoteResult.FAIL);
		voteResponse.setCandidate(candidateId);
		voteResponse.setMessage("Maximum vote limit reached");
		voteResponse.setCurrentVotes(votes);
		// try {
		// websocketServer.sendMessage(sessionId,
		// objectMapper.writeValueAsString(voteResponse));
		// } catch (JsonProcessingException e) {
		// TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

}
