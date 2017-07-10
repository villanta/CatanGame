package com.catangame.comms.messages.lobby;

import com.catangame.game.Player;

public class JoinLobbyResponse extends LobbyActionMessage {

	public JoinLobbyResponse() {
		// TODO Auto-generated constructor stub
	}

	public JoinLobbyResponse(Player player) {
		super(player, LobbyActionType.JOIN_LOBBY_RESPONSE);
		// TODO Auto-generated constructor stub
	}

}
