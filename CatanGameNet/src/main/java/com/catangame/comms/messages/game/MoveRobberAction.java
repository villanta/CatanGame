package com.catangame.comms.messages.game;

import com.catangame.model.locations.HexLocation;

public class MoveRobberAction extends GameActionMessage {

	private int stealFromPlayer;
	private HexLocation newLocation;

	public MoveRobberAction(int playerId, ActionType actionType, HexLocation newLocation, int stealFromPlayer) {
		super(playerId, actionType);
		this.newLocation = newLocation;
		this.stealFromPlayer = stealFromPlayer;
	}

	/**
	 * @return the stealFromPlayer
	 */
	public int getStealFromPlayer() {
		return stealFromPlayer;
	}

	/**
	 * @param stealFromPlayer
	 *            the stealFromPlayer to set
	 */
	public void setStealFromPlayer(int stealFromPlayer) {
		this.stealFromPlayer = stealFromPlayer;
	}

	/**
	 * @return the newLocation
	 */
	public HexLocation getNewLocation() {
		return newLocation;
	}

	/**
	 * @param newLocation
	 *            the newLocation to set
	 */
	public void setNewLocation(HexLocation newLocation) {
		this.newLocation = newLocation;
	}

}
