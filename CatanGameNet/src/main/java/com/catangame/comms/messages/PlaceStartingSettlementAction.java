package com.catangame.comms.messages;

import java.util.List;

import com.catangame.game.ResourceType;
import com.catangame.model.structures.Settlement;

public class PlaceStartingSettlementAction extends GameActionMessage {

	private Settlement settlement;
	private List<ResourceType> receivedResources;

	public PlaceStartingSettlementAction(int playerId, ActionType actionType, Settlement settlement,
			List<ResourceType> receivedResources) {
		super(playerId, actionType);
		this.settlement = settlement;
		this.receivedResources = receivedResources;
	}

	/**
	 * @return the settlement
	 */
	public Settlement getSettlement() {
		return settlement;
	}

	/**
	 * @param settlement
	 *            the settlement to set
	 */
	public void setSettlement(Settlement settlement) {
		this.settlement = settlement;
	}

	/**
	 * @return the receivedResources
	 */
	public List<ResourceType> getReceivedResources() {
		return receivedResources;
	}

	/**
	 * @param receivedResources
	 *            the receivedResources to set
	 */
	public void setReceivedResources(List<ResourceType> receivedResources) {
		this.receivedResources = receivedResources;
	}
}
