package com.catangame.comms.interfaces;

import com.catangame.comms.messages.game.GameActionMessage;

@FunctionalInterface
public interface GameService {

	void messageReceived(GameActionMessage gameActionMessage);

}
