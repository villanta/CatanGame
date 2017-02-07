package com.catangame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class PlayerResources {

	private IntegerProperty wheatCount = new SimpleIntegerProperty(0);
	private IntegerProperty sheepCount = new SimpleIntegerProperty(0);
	private IntegerProperty lumberCount = new SimpleIntegerProperty(0);
	private IntegerProperty brickCount = new SimpleIntegerProperty(0);
	private IntegerProperty oreCount = new SimpleIntegerProperty(0);

	private List<Function<Void, Void>> listeners = new ArrayList<>();

	public IntegerProperty wheatCountProperty() {
		return this.wheatCount;
	}

	public int getWheatCount() {
		return this.wheatCountProperty().get();
	}

	public IntegerProperty sheepCountProperty() {
		return this.sheepCount;
	}

	public int getSheepCount() {
		return this.sheepCountProperty().get();
	}

	public IntegerProperty lumberCountProperty() {
		return this.lumberCount;
	}

	public int getLumberCount() {
		return this.lumberCountProperty().get();
	}

	public IntegerProperty brickCountProperty() {
		return this.brickCount;
	}

	public int getBrickCount() {
		return this.brickCountProperty().get();
	}

	public IntegerProperty oreCountProperty() {
		return this.oreCount;
	}

	public int getOreCount() {
		return this.oreCountProperty().get();
	}

	public void giveWheat(int amount) {
		this.wheatCount.set(getWheatCount() + amount);
		notifyListeners();
	}

	public void takeWheat(int amount) {
		this.wheatCount.set(getWheatCount() - amount);
		notifyListeners();
	}

	public void giveSheep(int amount) {
		this.sheepCount.set(getSheepCount() + amount);
		notifyListeners();
	}

	public void takeSheep(int amount) {
		this.sheepCount.set(getSheepCount() - amount);
		notifyListeners();
	}

	public void giveLumber(int amount) {
		this.lumberCount.set(getLumberCount() + amount);
		notifyListeners();
	}

	public void takeLumber(int amount) {
		this.lumberCount.set(getLumberCount() - amount);
		notifyListeners();
	}

	public void giveBrick(int amount) {
		this.brickCount.set(getBrickCount() + amount);
		notifyListeners();
	}

	public void takeBrick(int amount) {
		this.brickCount.set(getBrickCount() - amount);
		notifyListeners();
	}

	public void giveOre(int amount) {
		this.oreCount.set(getOreCount() + amount);
		notifyListeners();
	}

	public void takeOre(int amount) {
		this.oreCount.set(getOreCount() - amount);
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

	public void addListener(Function<Void, Void> listener) {
		listeners.add(listener);
	}

	public void removeListener(Function<Void, Void> listener) {
		listeners.add(listener);
	}

	private void notifyListeners() {
		listeners.stream().forEach(listener -> listener.apply(null));
	}
}
