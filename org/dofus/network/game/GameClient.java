package org.dofus.network.game;

import java.util.Stack;

import org.apache.mina.core.session.IoSession;
import org.dofus.game.action.GameAction;
import org.dofus.objects.Accounts;
import org.dofus.objects.Characters;

public class GameClient {

	private Game game;
	private IoSession session;
	
	private GameClientHandler handler;
	
	private Accounts account;
	private Characters characters;
	
	private Stack<GameAction> actions = new Stack<>();
	
	public GameClient(Game game, IoSession session) {
		setGame(game);
		setSession(session);
		
		this.getSession().write("HG");
	}

	public IoSession getSession() {
		return session;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public Accounts getAccount() {
		return account;
	}

	public void setAccount(Accounts account) {
		this.account = account;
	}

	public Characters getCharacter() {
		return characters;
	}

	public void setCharacters(Characters characters) {
		this.characters = characters;
	}

	public Stack<GameAction> getActions() {
		return actions;
	}

	public void setActions(Stack<GameAction> actions) {
		this.actions = actions;
	}
	
    public boolean isBusy() {
        return actions.size() > 0;
    }

	public GameClientHandler getHandler() {
		return handler;
	}

	public void setHandler(GameClientHandler handler) {
		this.handler = handler;
	}
}
