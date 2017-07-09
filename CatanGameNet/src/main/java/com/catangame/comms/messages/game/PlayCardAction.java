package com.catangame.comms.messages.game;

import java.util.List;

import com.catangame.game.DevelopmentCard;
import com.catangame.game.ResourceType;

public class PlayCardAction extends GameActionMessage {

	private DevelopmentCard card;
	private List<ResourceType> selectedResources;

	public PlayCardAction(int playerId, ActionType actionType, DevelopmentCard card, List<ResourceType> selectedResources) {
		super(playerId, actionType);
		this.card = card;
		this.selectedResources=selectedResources;
	}

	/**
	 * @return the card
	 */
	public DevelopmentCard getCard() {
		return card;
	}

	/**
	 * @return the selectedResources
	 */
	public List<ResourceType> getSelectedResources() {
		return selectedResources;
	}	
}