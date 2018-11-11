package org.dofus.network.server.handlers;

import org.dofus.network.server.Server;
import org.dofus.network.server.ServerClient;
import org.dofus.network.server.ServerClientHandler;

public class ServerChoiceHandler extends ServerClientHandler {

	protected ServerChoiceHandler(Server server, ServerClient client) {
		super(server, client);
		System.out.println("Server choice");
	}

	@Override
	public void parse(String packet) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
	}

}
