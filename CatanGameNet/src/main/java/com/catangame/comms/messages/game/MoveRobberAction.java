package com.catangame.comms.messages.game;

import com.catangame.model.locations.HexCoordinate;

public class MoveRobberAction extends GameActionMessage {

	private int stealFromPlayer;
	private HexCoordinate newLocation;

	public MoveRobberAction(int playerId, ActionType actionType, HexCoordinate newLocation, int stealFromPlayer) {
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
	public HexCoordinate getNewLocation() {
		return newLocation;
	}

	/**
	 * @param newLocation
	 *            the newLocation to set
	 */
	public void setNewLocation(HexCoordinate newLocation) {
		this.newLocation = newLocation;
	}

}
