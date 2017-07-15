package com.catangame.comms.messages.game;

public class DiceRollRequest extends GameActionMessage {
	
	private int playerId;

	public DiceRollRequest() {
		//no-arg kryo
	}
	
	public DiceRollRequest(int playerId) {
		this.playerId = playerId;
	}
	
	public int getPlayerID() {
		return playerId;
	}

	public void setPlayerID(int playerId) {
		this.playerId = playerId;
	}
}
