/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.vote.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.saunders.votecounter.repository.UserRepository;
import com.saunders.votecounter.vote.service.LoginService;

public class LoginServiceImpl implements LoginService {

	@Autowired
	UserRepository userRepository;

	@Override
	public void register(String sessionId, String userId) {

		userRepository.createSession(sessionId, userId);
	}

}
