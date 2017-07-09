package com.catangame.comms.messages.lobby;

import com.catangame.game.Player;

public class SendMessage extends LobbyActionMessage {

	private String message;

	public SendMessage(Player player, String message) {
		super(player, LobbyActionType.SEND_MESSAGE);
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
