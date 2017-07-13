package com.catangame.comms.messages.lobby;

import com.catangame.Lobby;

public class LobbyInfoResponse {
	
	private Lobby lobby;

	public LobbyInfoResponse() {
		super();
	}
	
	public LobbyInfoResponse(Lobby lobby) {
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
