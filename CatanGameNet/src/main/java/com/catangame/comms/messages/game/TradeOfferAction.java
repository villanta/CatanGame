package com.catangame.comms.messages.game;

import java.util.List;

import com.catangame.model.resources.ResourceType;

public class TradeOfferAction extends GameActionMessage {

	private List<ResourceType> offering;
	private List<ResourceType> requesting;
	private int tradeId;

	public TradeOfferAction(int playerId, ActionType actionType, int tradeId, List<ResourceType> offering,
			List<ResourceType> requesting) {
		super(playerId, actionType);
		this.tradeId = tradeId;
		this.offering = offering;
		this.requesting = requesting;
	}

	/**
	 * @return the tradeId
	 */
	public int getTradeId() {
		return tradeId;
	}

	/**
	 * @return the offering
	 */
	public List<ResourceType> getOffering() {
		return offering;
	}

	/**
	 * @return the requesting
	 */
	public List<ResourceType> getRequesting() {
		return requesting;
	}

}
