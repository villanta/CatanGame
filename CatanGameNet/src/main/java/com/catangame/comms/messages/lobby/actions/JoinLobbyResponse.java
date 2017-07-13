package com.catangame.comms.messages.lobby.actions;

import com.catangame.game.Player;

public class JoinLobbyResponse extends LobbyActionMessage {

	private boolean response;

	public JoinLobbyResponse() {
		// empty constructor for kryo
	}

	public JoinLobbyResponse(Player player, boolean response) {
		super(player, LobbyActionType.JOIN_LOBBY_RESPONSE);
		this.response = response;
	}

	/**
	 * @return the response
	 */
	public boolean isResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(boolean response) {
		this.response = response;
	}

}
