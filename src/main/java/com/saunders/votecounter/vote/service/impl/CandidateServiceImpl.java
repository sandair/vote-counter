/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.vote.service.impl;

import org.springframework.beans.factory.annotation.Value;

import com.saunders.votecounter.vote.service.CandidateService;

import java.util.List;

public class CandidateServiceImpl implements CandidateService {

	@Value("#{'${candidates.ids}'.split(',')}")
	private List<String> candidateIds;

	@Override
	public List<String> getCandidates() {
		return candidateIds;
	}

}
