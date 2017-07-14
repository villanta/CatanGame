package com.catangame.comms.server;

import com.catangame.comms.interfaces.GameService;
import com.catangame.comms.messages.game.GameActionMessage;

public class GameServer implements GameService {

	private CatanServer server;

	public GameServer(CatanServer server) {
		this.server = server;
	}

	public void messageReceived(GameActionMessage gameActionMessage) {
		// TODO Auto-generated method stub
		
	}

}
