package com.catangame.comms.messages.lobby;

import java.util.Map;

public class PingMessage extends LobbyMessage{
	
	Map<Integer, Integer> playerIdToPingMap;

	public PingMessage() {
		// no arg for kryo
	}
	
	public PingMessage(Map<Integer, Integer> playerIdToPingMap) {
		super();
		this.playerIdToPingMap = playerIdToPingMap;
	}
	
	public Map<Integer, Integer> getPlayerIdToPingMap() {
		return playerIdToPingMap;
	}

	public void setPlayerIdToPingMap(Map<Integer, Integer> playerIdToPingMap) {
		this.playerIdToPingMap = playerIdToPingMap;
	}
}
