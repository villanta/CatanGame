package com.catangame.comms.messages;

import com.catangame.model.structures.Road;

public class BuildRoadAction extends GameActionMessage {

	private Road road;

	public BuildRoadAction(int playerId, ActionType actionType, Road road) {
		super(playerId, actionType);
		this.road = road;
	}

	/**
	 * @return the road
	 */
	public Road getRoad() {
		return road;
	}
}