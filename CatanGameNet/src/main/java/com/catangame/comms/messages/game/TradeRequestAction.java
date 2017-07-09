package com.catangame.comms.messages.game;

import java.util.List;

import com.catangame.game.ResourceType;

public class TradeRequestAction extends GameActionMessage {

	private List<ResourceType> requestingResources;
	private List<ResourceType> offeringResources;
	private int tradeId;

	public TradeRequestAction(int playerId, ActionType actionType, int tradeId, List<ResourceType> requestingResources,
			List<ResourceType> offeringResources) {
		super(playerId, actionType);
		this.tradeId = tradeId;
		this.requestingResources = requestingResources;
		this.offeringResources = offeringResources;
	}

	/**
	 * @return the tradeId
	 */
	public int getTradeId() {
		return tradeId;
	}

	/**
	 * @return the requestingResources
	 */
	public List<ResourceType> getRequestingResources() {
		return requestingResources;
	}

	/**
	 * @return the offeringResources
	 */
	public List<ResourceType> getOfferingResources() {
		return offeringResources;
	}
}
