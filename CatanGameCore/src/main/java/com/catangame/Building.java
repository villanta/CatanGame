package com.catangame;

import com.catangame.model.Drawable;
import com.catangame.model.Player;
import com.catangame.model.VertexLocation;

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

	protected abstract Shape getShape();
	
	@Override
	public abstract boolean equals(Object other);
}