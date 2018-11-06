package org.dofus;

import java.io.IOException;

import org.dofus.database.Connector;
import org.dofus.network.game.Game;
import org.dofus.network.server.Server;

public class Main {

	public static Connector connector;
	
	public static void main(String[] args) throws IOException {
		long time = System.currentTimeMillis();
		System.out.println("Dofus 1.29.1 sandbox 1.0.0\n");
		connector = new Connector("127.0.0.1", "root", "", "sandbox");
		
		Server server = new Server();
		server.start((short) 499);
		
		Game game = new Game();
		game.start((short) 5555);
		
		System.in.read();
		
		game.stop();
		server.stop();
		connector.stop();
		
		System.out.println("Sandbox loaded in "+ ((System.currentTimeMillis() - time) / 1000) + " seconds.\n");
	}

}
