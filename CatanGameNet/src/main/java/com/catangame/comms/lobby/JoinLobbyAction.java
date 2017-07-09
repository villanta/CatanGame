package com.catangame.comms.lobby;

import com.catangame.game.Player;

public class JoinLobbyAction extends LobbyActionMessage {
	
	public JoinLobbyAction(Player player) {
		super(player, LobbyActionType.JOIN_LOBBY);

	}
	
	
	
}
