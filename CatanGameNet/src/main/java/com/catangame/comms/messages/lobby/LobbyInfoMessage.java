package com.catangame.comms.messages.lobby;

import com.catangame.Lobby;

public class LobbyInfoMessage {
	
	private Lobby lobby;

	public LobbyInfoMessage(Lobby lobby) {
		super();
		this.lobby = lobby;
	}
	
	public Lobby getLobby() {
		return lobby;
	}

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
}
