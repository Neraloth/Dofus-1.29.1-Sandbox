package org.dofus.network.server.handlers;

import org.dofus.database.objects.AccountData;
import org.dofus.network.server.Server;
import org.dofus.network.server.ServerClient;
import org.dofus.network.server.ServerClientHandler;
import org.dofus.objects.Accounts;

public class AuthentificationHandler extends ServerClientHandler {

	public AuthentificationHandler(Server server, ServerClient client) {
		super(server, client);
	}

	@Override
	public void parse(String packet) throws Exception {
		String[] data = packet.split("\n");
		
		Accounts account = AccountData.load(data[0].trim());
	
		if(account != null && account.valid(data[1].trim(), client.getKey())) {
			if(account.isBanned()) {
				System.out.println("Is banned");
				client.getSession().write("AlEb");
				return;
			}
			client.setAccount(account);
			client.setHandler(account.getNickname().isEmpty() ? new NicknameHandler(server, client) : new ServerChoiceHandler(server, client));
			
		} else
			client.getSession().write("AlEf");
	}

	@Override
	public void onClosed() {
		//Update account and remove in map
	}

}
