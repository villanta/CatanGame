package com.catangame.comms.lobby;

import com.catangame.game.Player;

public class LeaveLobbyAction extends LobbyActionMessage {

	public LeaveLobbyAction(Player player) {
		super(player, LobbyActionType.LEAVE_LOBBY);

	}

}
