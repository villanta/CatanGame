package com.catangame.comms.messages.game;

public class BuyCardAction extends GameActionMessage {

	public BuyCardAction(int playerId, ActionType actionType) {
		super(playerId, actionType);
	}
}
