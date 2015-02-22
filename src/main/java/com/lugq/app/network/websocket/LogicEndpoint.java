package com.lugq.app.network.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(value = "/end")
public class LogicEndpoint {

	private static Logger logger = LogManager.getLogger(LogicEndpoint.class);

	private static Set<Session> peers = Collections
			.synchronizedSet(new HashSet<Session>());

	@OnMessage
	public String onMessage(Session session, String str) {
		return null;
	}

	@OnOpen
	public void onOpen(Session peer) {
		logger.debug("one session opened.");
		peers.add(peer);
	}

	@OnClose
	public void onClose(Session peer) {
		logger.debug("one session closed.");
		peers.remove(peer);
	}
	
	@OnError
	public void onError(Session session, Throwable t) {
		logger.debug("onError.");
	}


}
