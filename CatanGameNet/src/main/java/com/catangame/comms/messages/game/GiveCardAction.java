package com.catangame.comms.messages.game;

import com.catangame.game.DevelopmentCard;

public class GiveCardAction extends GameActionMessage {

	private DevelopmentCard card;

	public GiveCardAction(int playerId, ActionType actionType, DevelopmentCard card) {
		super(playerId, actionType);
		this.card = card;
	}

	/**
	 * @return the card
	 */
	public DevelopmentCard getCard() {
		return card;
	}
}