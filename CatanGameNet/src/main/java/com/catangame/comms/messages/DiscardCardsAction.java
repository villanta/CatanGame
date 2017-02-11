package com.catangame.comms.messages;

import java.util.List;

import com.catangame.game.ResourceType;

public class DiscardCardsAction extends GameActionMessage {

	private List<ResourceType> discardedCards;

	public DiscardCardsAction(int playerId, ActionType actionType, List<ResourceType> discardedCards) {
		super(playerId, actionType);
		this.discardedCards = discardedCards;
	}

	/**
	 * @return the discardedCards
	 */
	public List<ResourceType> getDiscardedCards() {
		return discardedCards;
	}

	/**
	 * @param discardedCards
	 *            the discardedCards to set
	 */
	public void setDiscardedCards(List<ResourceType> discardedCards) {
		this.discardedCards = discardedCards;
	}

}
