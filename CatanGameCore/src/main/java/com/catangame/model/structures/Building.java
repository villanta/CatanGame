package com.catangame.model.structures;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.catangame.model.game.Player;
import com.catangame.model.locations.VertexLocation;
import com.catangame.model.resources.ResourceType;
import com.catangame.model.tiles.GameHex;
import com.catangame.util.CatanUtils;
import com.catangame.view.Drawable;

import javafx.scene.shape.Shape;

public abstract class Building implements Drawable {

	private VertexLocation location;
	private Player player;
	private boolean selected;

	public Building(VertexLocation location, Player player) {
		super();
		this.location = location;
		this.player = player;
	}

	/**
	 * @return the location
	 */
	public VertexLocation getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(VertexLocation location) {
		this.location = location;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * @return the selected
	 */
	@Override
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public void deselect() {
		this.selected = false;
	}

	@Override
	public void select() {
		this.selected = true;
	}

	public boolean isBuildingSelected(double x, double y) {
		return getShape().intersects(x, y, 1, 1);
	}

	public List<ResourceType> getResourcesOnDiceRoll(int diceRoll, List<GameHex> board) {
		List<ResourceType> resources = new ArrayList<>();

		CatanUtils.getAdjacentHexes(this.location).stream().forEach(location -> {
			Optional<GameHex> gameHex = board.stream().filter(hex -> hex.getLocation().equals(location)).findFirst();
			if (gameHex.isPresent() && gameHex.get().getDiceRoll() == diceRoll) {
				for (int i = 0; i < getResourceQuantityPerRoll(); i++) {
					resources.add(ResourceType.getResourceTypeFromHexType(gameHex.get().getType()));
				}
			}
		});

		return resources;
	}

	protected abstract Shape getShape();

	protected abstract int getResourceQuantityPerRoll();

	@Override
	public abstract boolean equals(Object other);
}