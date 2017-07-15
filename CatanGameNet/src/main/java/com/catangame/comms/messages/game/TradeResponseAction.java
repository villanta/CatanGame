package com.catangame.comms.messages.game;

import java.util.List;

import com.catangame.model.resources.ResourceType;

public class TradeResponseAction extends GameActionMessage {

	private Object tradeId;
	private int fromPlayerId;
	private int toPlayerId;
	private List<ResourceType> toPlayerResources;
	private List<ResourceType> fromPlayerResources;
	private boolean accepted;

	public TradeResponseAction(int playerId, ActionType actionType, int tradeId, int fromPlayerId, int toPlayerId,
			List<ResourceType> fromPlayerResources, List<ResourceType> toPlayerResources, boolean accepted) {
		super(playerId, actionType);
		this.tradeId = tradeId;
		this.fromPlayerId = fromPlayerId;
		this.toPlayerId = toPlayerId;

		this.fromPlayerResources = fromPlayerResources;
		this.toPlayerResources = toPlayerResources;

		this.accepted = accepted;
	}

	/**
	 * @return the tradeId
	 */
	public Object getTradeId() {
		return tradeId;
	}

	/**
	 * @return the fromPlayerId
	 */
	public int getFromPlayerId() {
		return fromPlayerId;
	}

	/**
	 * @return the toPlayerId
	 */
	public int getToPlayerId() {
		return toPlayerId;
	}

	/**
	 * @return the toPlayerResources
	 */
	public List<ResourceType> getToPlayerResources() {
		return toPlayerResources;
	}

	/**
	 * @return the fromPlayerResources
	 */
	public List<ResourceType> getFromPlayerResources() {
		return fromPlayerResources;
	}

	/**
	 * @return the accepted
	 */
	public boolean isAccepted() {
		return accepted;
	}

}
