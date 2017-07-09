package com.catangame.comms.lobby;

import com.catangame.Lobby;

public class LobbyMessage {
	
	private Lobby lobby;

	public LobbyMessage(Lobby lobby) {
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
