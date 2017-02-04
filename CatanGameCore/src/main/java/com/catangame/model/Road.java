package com.catangame.model;

import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Road implements Drawable {

	private EdgeLocation location;
	private Color playerColor;

	public Road(EdgeLocation location, Color playerColor) {
		super();
		this.location = location;
		this.playerColor = playerColor;
	}

	/**
	 * @return the location
	 */
	public EdgeLocation getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(EdgeLocation location) {
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

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		// start and end points in pixels (not accounting for offsets)
		Point2D start = HexMath.getHexCorner(
				HexMath.getHexCenter(location.getStart().getReferenceHex(), radius, xOffset, yOffset), radius,
				location.getStart().getVertexIndex());
		Point2D end = HexMath.getHexCorner(
				HexMath.getHexCenter(location.getEnd().getReferenceHex(), radius, xOffset, yOffset), radius,
				location.getEnd().getVertexIndex());

		double vectorX = end.getX() - start.getX();
		double vectorY = end.getY() - start.getY();

		double deltaX = vectorX * 0.25;
		double deltaY = vectorY * 0.25;

		gc.setLineWidth(radius / 6);

		gc.setStroke(playerColor);
		gc.strokeLine(start.getX() + deltaX, start.getY() + deltaY, end.getX() - deltaX, end.getY() - deltaY);
	}
}
