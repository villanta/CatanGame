package com.catangame.comms.messages.lobby.actions;

import com.catangame.model.game.Player;

public class LeaveLobbyAction extends LobbyActionMessage {
	
	public LeaveLobbyAction() {
		
	}
	
	public LeaveLobbyAction(Player player) {
		super(player, LobbyActionType.LEAVE_LOBBY);

	}

}
