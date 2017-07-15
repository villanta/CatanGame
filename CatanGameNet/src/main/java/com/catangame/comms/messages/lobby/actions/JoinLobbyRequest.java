package com.catangame.comms.messages.lobby.actions;

import com.catangame.model.game.Player;

public class JoinLobbyRequest extends LobbyActionMessage {
	
	public JoinLobbyRequest() {
		// empty constructor for kryo
	}
	
	public JoinLobbyRequest(Player player) {
		super(player, LobbyActionType.JOIN_LOBBY_REQUEST);
	}
}
