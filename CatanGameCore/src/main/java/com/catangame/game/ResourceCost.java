package com.catangame.game;

public class ResourceCost {

	private int wheatCost;
	private int sheepCost;
	private int lumberCost;
	private int brickCost;
	private int oreCost;

	public ResourceCost(int wheatCost, int sheepCost, int lumberCost, int brickCost, int oreCost) {
		super();
		this.wheatCost = wheatCost;
		this.sheepCost = sheepCost;
		this.lumberCost = lumberCost;
		this.brickCost = brickCost;
		this.oreCost = oreCost;
	}

	/**
	 * @return the wheatCost
	 */
	public int getWheatCost() {
		return wheatCost;
	}

	/**
	 * @param wheatCost
	 *            the wheatCost to set
	 */
	public void setWheatCost(int wheatCost) {
		this.wheatCost = wheatCost;
	}

	/**
	 * @return the sheepCost
	 */
	public int getSheepCost() {
		return sheepCost;
	}

	/**
	 * @param sheepCost
	 *            the sheepCost to set
	 */
	public void setSheepCost(int sheepCost) {
		this.sheepCost = sheepCost;
	}

	/**
	 * @return the lumberCost
	 */
	public int getLumberCost() {
		return lumberCost;
	}

	/**
	 * @param lumberCost
	 *            the lumberCost to set
	 */
	public void setLumberCost(int lumberCost) {
		this.lumberCost = lumberCost;
	}

	/**
	 * @return the brickCost
	 */
	public int getBrickCost() {
		return brickCost;
	}

	/**
	 * @param brickCost
	 *            the brickCost to set
	 */
	public void setBrickCost(int brickCost) {
		this.brickCost = brickCost;
	}

	/**
	 * @return the oreCost
	 */
	public int getOreCost() {
		return oreCost;
	}

	/**
	 * @param oreCost
	 *            the oreCost to set
	 */
	public void setOreCost(int oreCost) {
		this.oreCost = oreCost;
	}

}
