/*
 * Copyright David Saunders
 */

package com.saunders.votecounter.websocket;

import org.springframework.beans.factory.annotation.Autowired;

import com.saunders.votecounter.repository.UserRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/websocketendpoint")
public class WebsocketServer {

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@Autowired
	private UserRepository userRepository;

	@OnOpen
	public void onOpen(Session session) {

		System.out.println("Open Connection ...");
		// add session to set for use when sending message
		sessions.add(session);
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("Close Connection ...");
		// On registering, the user and websocket id are added to this repo,
		// remove now that websocket session is closed
		userRepository.removeSession(session.getId());
		sessions.remove(session);
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		// Do nothing, client interacts via rest calls
		System.out.println("Message from the client: " + message);
	}

	/**
	 * Send message to an individual websocket session
	 *
	 * @param sessionId
	 *            websocket sessionId
	 * @param message
	 *            Message to send
	 */
	public void sendMessage(String sessionId, String message) {
		try {
			for (Session session : sessions) {
				if (sessionId.equals(session.getId())) {
					session.getBasicRemote().sendText(message);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnError
	public void onError(Throwable e) {
		e.printStackTrace();
	}

}
