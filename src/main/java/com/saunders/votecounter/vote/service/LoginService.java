/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.vote.service;

/**
 * Service to handle registration/login of a User
 * 
 * @author davidsa1
 *
 */
public interface LoginService {

	/**
	 * Register a new websocket session for a user
	 * 
	 * @param sessionId
	 *            websocket session Id
	 * @param userId
	 *            user e-mail address
	 */
	void register(String sessionId, String userId);
}
