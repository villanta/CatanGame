package com.catangame.comms.messages.lobby;

import com.catangame.game.Player;

public class JoinLobbyRequest extends LobbyActionMessage {
	
	public JoinLobbyRequest(Player player) {
		super(player, LobbyActionType.JOIN_LOBBY);

	}
	
	
	
}
