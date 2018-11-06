package org.dofus.network.game;

public abstract class GameClientHandler {

    protected Game Game;
    protected GameClient client;

    protected GameClientHandler(Game Game, GameClient client) {
        this.Game = Game;
        this.client = client;
    }

    public abstract void parse(String packet) throws Exception;
    public abstract void onClosed();
}
