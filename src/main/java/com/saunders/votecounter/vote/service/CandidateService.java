/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.vote.service;

import java.util.List;

/**
 * Service to return candidate information
 *
 * @author davidsa1
 *
 */
public interface CandidateService {
	/**
	 * Get the candidates who can be voted for
	 * 
	 * @return List of candidates
	 */
	List<String> getCandidates();
}
