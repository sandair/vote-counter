/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.repository.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.saunders.votecounter.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class UserRepositoryImpl implements UserRepository {

	private static final Logger LOG = Logger.getLogger(UserRepositoryImpl.class);

	private final Map<String, String> userSessions = Collections.synchronizedMap(new HashMap<String, String>());

	@Override
	public void createSession(String sessionId, String userId) {
		userSessions.put(sessionId, userId);
	}

	@Override
	public void removeSession(String sessionId) {
		synchronized (userSessions) {
			if (userSessions.containsKey(sessionId)) {
				userSessions.remove(sessionId);
			}

		}
	}

	@Override
	public List<String> getSessionsFromUserId(String userId) {
		List<String> sessions = new ArrayList<>();
		synchronized (userSessions) {
			for (Entry<String, String> entry : userSessions.entrySet()) {
				if (userId.equals(entry.getValue())) {
					sessions.add(entry.getKey());
				}
			}
		}
		LOG.debug(String.format("Returning sessions: %s", StringUtils.join(sessions)));
		return sessions;
	}

}
