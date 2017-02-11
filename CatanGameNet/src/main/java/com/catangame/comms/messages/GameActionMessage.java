package com.catangame.comms.messages;

public abstract class GameActionMessage {

	private int playerId;
	private ActionType actionType;

	public GameActionMessage(int playerId, ActionType actionType) {
		super();
		this.playerId = playerId;
		this.actionType = actionType;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId
	 *            the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * @return the actionType
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * @param actionType
	 *            the actionType to set
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
}
