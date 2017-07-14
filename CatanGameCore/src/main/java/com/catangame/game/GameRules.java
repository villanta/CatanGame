package com.catangame.game;

public class GameRules {

	private int playerLimit;
	private boolean joinableWhileActive;

	/**
	 * @return the playerLimit
	 */
	public int getPlayerLimit() {
		return playerLimit;
	}

	/**
	 * @param playerLimit
	 *            the playerLimit to set
	 */
	public void setPlayerLimit(int playerLimit) {
		this.playerLimit = playerLimit;
	}

	public boolean isJoinableWhileActive() {
		return joinableWhileActive;
	}
	
	public void setJoinableWhileActive(boolean joinableWhileActive) {
		this.joinableWhileActive = joinableWhileActive;
	}
}
