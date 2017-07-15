package com.catangame.comms.messages.lobby.actions;

import com.catangame.model.game.Player;

public class KickPlayerAction extends LobbyActionMessage {

	public KickPlayerAction() {
		// no-arg kryo constructor
	}
	
	public KickPlayerAction(Player player) {
		super(player, LobbyActionType.KICK_PLAYER);
	}
}
