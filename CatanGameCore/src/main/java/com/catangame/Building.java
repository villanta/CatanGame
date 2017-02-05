package com.catangame;

import com.catangame.model.Drawable;
import com.catangame.model.VertexLocation;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Building implements Drawable {

	private VertexLocation location;
	private Color playerColor;
	private boolean selected;

	public Building(VertexLocation location, Color playerColor) {
		super();
		this.location = location;
		this.playerColor = playerColor;
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
	 * @return the playerColor
	 */
	public Color getPlayerColor() {
		return playerColor;
	}

	/**
	 * @param playerColor
	 *            the playerColor to set
	 */
	public void setPlayerColor(Color playerColor) {
		this.playerColor = playerColor;
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
}