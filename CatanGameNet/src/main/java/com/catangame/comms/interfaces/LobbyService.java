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
	
	void addPingListener(PingListener pingListener);
	void removePingListener(PingListener pingListener);

	Lobby getLobby();
	void setLobby(Lobby lobby);
	
	void closeLobby(Player player);
	
	boolean isServer();
	
	void kickPlayer(Player player);
	void onClientDisconnect(Connection connection);
	
	void startGame(Lobby lobby);
}
