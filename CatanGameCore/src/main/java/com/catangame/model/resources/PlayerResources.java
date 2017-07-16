package com.catangame.model.resources;

import java.util.ArrayList;
import java.util.List;

import com.catangame.view.PlayerResourceListener;

public class PlayerResources {

	private int wheatCount = 0;
	private int sheepCount = 0;
	private int lumberCount = 0;
	private int brickCount = 0;
	private int oreCount = 0;

	private List<PlayerResourceListener> listeners = new ArrayList<>();

	/**
	 * @return the wheatCount
	 */
	public int getWheatCount() {
		return wheatCount;
	}

	/**
	 * @param wheatCount the wheatCount to set
	 */
	public void setWheatCount(int wheatCount) {
		this.wheatCount = wheatCount;
	}

	/**
	 * @return the sheepCount
	 */
	public int getSheepCount() {
		return sheepCount;
	}

	/**
	 * @param sheepCount the sheepCount to set
	 */
	public void setSheepCount(int sheepCount) {
		this.sheepCount = sheepCount;
	}

	/**
	 * @return the lumberCount
	 */
	public int getLumberCount() {
		return lumberCount;
	}

	/**
	 * @param lumberCount the lumberCount to set
	 */
	public void setLumberCount(int lumberCount) {
		this.lumberCount = lumberCount;
	}

	/**
	 * @return the brickCount
	 */
	public int getBrickCount() {
		return brickCount;
	}

	/**
	 * @param brickCount the brickCount to set
	 */
	public void setBrickCount(int brickCount) {
		this.brickCount = brickCount;
	}

	/**
	 * @return the oreCount
	 */
	public int getOreCount() {
		return oreCount;
	}

	/**
	 * @param oreCount the oreCount to set
	 */
	public void setOreCount(int oreCount) {
		this.oreCount = oreCount;
	}

	public void giveWheat(int amount) {
		wheatCount = getWheatCount() + amount;
		notifyListeners();
	}

	public void takeWheat(int amount) {
		wheatCount = getWheatCount() - amount;
		notifyListeners();
	}

	public void giveSheep(int amount) {
		sheepCount = getSheepCount() + amount;
		notifyListeners();
	}

	public void takeSheep(int amount) {
		sheepCount = getSheepCount() - amount;
		notifyListeners();
	}

	public void giveLumber(int amount) {
		lumberCount = getLumberCount() + amount;
		notifyListeners();
	}

	public void takeLumber(int amount) {
		lumberCount = getLumberCount() - amount;
		notifyListeners();
	}

	public void giveBrick(int amount) {
		brickCount = getBrickCount() + amount;
		notifyListeners();
	}

	public void takeBrick(int amount) {
		brickCount = getBrickCount() - amount;
		notifyListeners();
	}

	public void giveOre(int amount) {
		oreCount = getOreCount() + amount;
		notifyListeners();
	}

	public void takeOre(int amount) {
		oreCount = getOreCount() - amount;
		notifyListeners();
	}

	public void payFor(ResourceCost cost) {
		takeWheat(cost.getWheatCost());
		takeSheep(cost.getSheepCost());
		takeLumber(cost.getLumberCost());
		takeBrick(cost.getBrickCost());
		takeOre(cost.getOreCost());
		notifyListeners();
	}

	public boolean canAfford(ResourceCost cost) {
		return cost.getWheatCost() <= getWheatCount() && cost.getSheepCost() <= getSheepCount()
				&& cost.getLumberCost() <= getLumberCount() && cost.getBrickCost() <= getBrickCount()
				&& cost.getOreCost() <= getOreCount();
	}

	public void addListener(PlayerResourceListener listener) {
		listeners.add(listener);
	}

	public void removeListener(PlayerResourceListener listener) {
		listeners.add(listener);
	}

	private void notifyListeners() {
		listeners.stream().forEach(listener -> listener.resourcesUpdated());
	}
}
