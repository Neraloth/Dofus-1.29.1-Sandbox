package org.dofus.network.server;

import org.apache.mina.core.session.IoSession;

public class ServerClient {

	private IoSession session;
	private ServerClientHandler handler; 
	
	public ServerClient(IoSession session) {
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
	
	public ServerClientHandler getHandler() {
		return handler;
	}
	
	public void setHandler(ServerClientHandler handler) {
		this.handler = handler;
	}
}
