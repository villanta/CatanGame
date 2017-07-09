package com.catangame.comms.messages.game;

import com.catangame.model.structures.Road;

public class PlaceStartingRoadAction extends GameActionMessage {

	private Road road;

	public PlaceStartingRoadAction(int playerId, ActionType actionType, Road road) {
		super(playerId, actionType);
		this.road = road;
	}

	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}

	/**
	 * @param road
	 *            the road to set
	 */
	public void setRoad(Road road) {
		this.road = road;
	}
}