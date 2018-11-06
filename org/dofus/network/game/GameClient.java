package org.dofus.network.game;

import org.apache.mina.core.session.IoSession;

public class GameClient {

	private IoSession session;
	private GameClientHandler handler; 
	
	public GameClient(IoSession session) {
		setSession(session);
		//this.key = StringUtils.random(32);
		//this.getSession().write("HC" + getKey());
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}
	
	public GameClientHandler getHandler() {
		return handler;
	}
	
	public void setHandler(GameClientHandler handler) {
		this.handler = handler;
	}
}
