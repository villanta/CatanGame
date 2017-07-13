package com.catangame.comms.messages.lobby.actions;

import com.catangame.game.Player;

public abstract class LobbyActionMessage {
	
	private Player player;
	private LobbyActionType actionType;
	
	public LobbyActionMessage() {
		// no arg cons. for kryo
	}
	
	public LobbyActionMessage(Player player, LobbyActionType lobbyActionType) {
		super();
		this.player = player;
		this.actionType = lobbyActionType;
	}

	public LobbyActionType getActionType() {
		return actionType;
	}
	public void setActionType(LobbyActionType actionType) {
		this.actionType = actionType;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}	
}
