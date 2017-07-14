package com.catangame.comms.interfaces;

import com.catangame.Lobby;
import com.catangame.comms.listeners.LobbyEventListener;
import com.catangame.comms.messages.lobby.LobbyMessage;
import com.catangame.game.Player;
import com.esotericsoftware.kryonet.Connection;

public interface LobbyService {

	void updateLobby(Lobby lobby);
	void messageReceived(LobbyMessage lobbyMessage, Connection connection);

	void addListener(LobbyEventListener lobbyEventListener);
	void removeListener(LobbyEventListener lobbyEventListener);

	Lobby getLobby();
	void setLobby(Lobby lobby);
	
	void closeLobby(Player player);
}
