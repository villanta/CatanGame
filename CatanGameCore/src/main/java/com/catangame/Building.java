package com.catangame;

import com.catangame.model.Drawable;
import com.catangame.model.VertexLocation;

import javafx.scene.paint.Color;

public abstract class Building implements Drawable {

	private VertexLocation location;
	private Color playerColor;

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

}
