package com.catangame.comms.interfaces;

public interface CatanEndPoint {

	ChatService getChatService();

	LobbyService getLobbyService();

	GameService getGameService();

	void disconnect();
}
