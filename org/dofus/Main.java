package org.dofus;

import java.io.IOException;

import org.dofus.database.Connector;
import org.dofus.network.game.Game;
import org.dofus.network.server.Server;
import org.dofus.utils.Constants;

public class Main {

	public static Connector connector;
	
	public static void main(String[] args) throws IOException {
		long time = System.currentTimeMillis();
		System.out.println("Dofus 1.29.1 sandbox " + Constants.APPLICATION_VERSION.sValue + "\n");
		connector = new Connector("127.0.0.1", "root", "", "dofus");
		
		Server server = new Server();
		server.start((short) 499);
		
		Game game = new Game();
		game.start((short) 5555);
		
		System.out.println("Sandbox loaded in "+ ((System.currentTimeMillis() - time) / 1000) + " seconds.\n");
		System.in.read();
		
		game.stop();
		server.stop();
		connector.stop();
	}
}
