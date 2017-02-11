package com.catangame.comms.messages;

public class BuyCardAction extends GameActionMessage {

	public BuyCardAction(int playerId, ActionType actionType) {
		super(playerId, actionType);
	}
}
