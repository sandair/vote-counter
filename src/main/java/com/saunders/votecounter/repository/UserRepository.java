/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.repository;

import java.util.List;

/**
 * Repository of Users with websocket sessions
 * 
 * @author davidsa1
 *
 */
public interface UserRepository {

	/**
	 * Add a new websocket session for a particular user. User can have multiple
	 * websocket sessions.
	 * 
	 * @param sessionId
	 *            websocket session
	 * @param userId
	 */
	public void createSession(String sessionId, String userId);

	/**
	 * Remove a closed websocket session
	 * 
	 * @param sessionId
	 *            websocket session
	 */
	public void removeSession(String sessionId);

	/**
	 * Get all websocket sessions associated with a user
	 * 
	 * @param userId
	 * @return List of websocket sessions
	 */
	public List<String> getSessionsFromUserId(String userId);

}
