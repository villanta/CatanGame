package com.catangame.comms.messages.lobby.actions;

import com.catangame.game.Player;

public class SendMessageLobbyAction extends LobbyActionMessage {

	private String message;

	public SendMessageLobbyAction() {
	}
	
	public SendMessageLobbyAction(Player player, String message) {
		super(player, LobbyActionType.SEND_MESSAGE);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return String.format("%s : %s", getPlayer().getName(), message);
	}
}
