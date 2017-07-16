/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.repository.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.collections4.keyvalue.DefaultMapEntry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {

	@Mock
	private Map<String, String> mockedUserSessions;

	private UserRepositoryImpl userRepositoryImpl;
	private static String webSocketSession;
	private static String userId;

	@Before
	public void setup() {
		userRepositoryImpl = new UserRepositoryImpl();
		webSocketSession = UUID.randomUUID().toString();
		userId = UUID.randomUUID().toString();

		Whitebox.setInternalState(userRepositoryImpl, "userSessions", mockedUserSessions);
	}

	@Test
	public void createSession_WhenCalled_UpdatesMap() {

		userRepositoryImpl.createSession(webSocketSession, userId);

		verify(mockedUserSessions).put(webSocketSession, userId);
	}

	@Test
	public void removeSession_SessionExists_UpdatesMap() {
		when(mockedUserSessions.containsKey(webSocketSession)).thenReturn(true);

		userRepositoryImpl.removeSession(webSocketSession);

		verify(mockedUserSessions).remove(webSocketSession);
	}

	@Test
	public void removeSession_SessionDoesNotExist_DoesNotUpdateMap() {
		when(mockedUserSessions.containsKey(webSocketSession)).thenReturn(false);

		userRepositoryImpl.removeSession(webSocketSession);

		verify(mockedUserSessions, never()).remove(webSocketSession);

	}

	@Test
	public void getSessionsFromUserId_TwoValidSessionsExist_TwoSessionsReturned() {
		Set<Entry<String, String>> entries = new HashSet<>();
		// add two sessions for this user
		Entry<String, String> firstSessionEntry = new DefaultMapEntry<String, String>(webSocketSession, userId);
		Entry<String, String> secondSessionEntry = new DefaultMapEntry<String, String>(UUID.randomUUID().toString(),
				userId);
		// add a session for a second user
		Entry<String, String> thirdSessionEntry = new DefaultMapEntry<String, String>(UUID.randomUUID().toString(),
				UUID.randomUUID().toString());
		entries.add(firstSessionEntry);
		entries.add(secondSessionEntry);
		entries.add(thirdSessionEntry);
		when(mockedUserSessions.entrySet()).thenReturn(entries);

		List<String> sessionIds = userRepositoryImpl.getSessionsFromUserId(userId);

		assertEquals("Incorrect number of sessions returned", 2, sessionIds.size());

	}

}
