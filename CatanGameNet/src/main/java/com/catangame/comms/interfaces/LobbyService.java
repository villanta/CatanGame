package com.catangame.comms.interfaces;

import com.catangame.Lobby;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.esotericsoftware.kryonet.Connection;

public interface LobbyService {

	void messageReceived(LobbyMessage lobbyMessage, Connection connection);

	void addListener(LobbyEventListener lobbyEventListener);
	void removeListener(LobbyEventListener lobbyEventListener);
	
	Lobby getLobby();
	void setLobby(Lobby lobby);	
}
