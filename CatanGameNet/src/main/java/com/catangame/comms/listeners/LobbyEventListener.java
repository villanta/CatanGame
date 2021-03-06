package com.catangame.comms.listeners;

import com.catangame.comms.messages.lobby.LobbyInfoResponse;
import com.catangame.comms.messages.lobby.actions.JoinLobbyResponse;
import com.catangame.comms.messages.lobby.actions.StartGameMessage;
import com.esotericsoftware.kryonet.Connection;

public interface LobbyEventListener {

	void updatedLobbyInfo(LobbyInfoResponse lobbyInfoResponse, Connection connection);

	void joinLobbyResponse(JoinLobbyResponse joinLobbyResponse, Connection connection);

	void lobbyClosed();

	void gameStarted(StartGameMessage startGameMessage);
}
