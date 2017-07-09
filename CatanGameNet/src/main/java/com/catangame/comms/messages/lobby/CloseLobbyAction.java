package com.catangame.comms.messages.lobby;

import com.catangame.game.Player;

public class CloseLobbyAction extends LobbyActionMessage {

	public CloseLobbyAction(Player player) {
		super(player, LobbyActionType.CLOSE_LOBBY);
		// TODO Auto-generated constructor stub
	}

}
