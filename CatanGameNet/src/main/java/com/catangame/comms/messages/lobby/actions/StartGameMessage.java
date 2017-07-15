package com.catangame.comms.messages.lobby.actions;

import com.catangame.Lobby;
import com.catangame.model.game.Player;

public class StartGameMessage extends LobbyActionMessage {

	private Lobby lobby;

	public StartGameMessage() {
		super(null, LobbyActionType.START_GAME);
	}

	public StartGameMessage(Lobby lobby, Player player) {
		super(player, LobbyActionType.START_GAME);
		this.lobby = lobby;
	}

	/**
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * @param lobby
	 *            the lobby to set
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

}
