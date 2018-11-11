package org.dofus.network.server.handlers;

import org.dofus.database.objects.AccountData;
import org.dofus.network.server.Server;
import org.dofus.network.server.ServerClient;
import org.dofus.network.server.ServerClientHandler;

public class NicknameHandler extends ServerClientHandler {

	protected NicknameHandler(Server server, ServerClient client) {
		super(server, client);
		client.getSession().write("AlEr");
	}

	@Override
	public void parse(String packet) throws Exception {
		if(packet.equals("Af"))
			return;
		
		//TODO: Restriction name
		if(!AccountData.nicknameIsExist(packet)) {
			client.getAccount().setNickname(packet);
			AccountData.updateNickname(client.getAccount());
			client.setHandler(new ServerChoiceHandler(server, client));
		} else
			client.getSession().write("AlEs");
	}

	@Override
	public void onClosed() {
		// TODO Auto-generated method stub
	}

}
