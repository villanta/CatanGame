package com.catangame.model;

import java.util.ArrayList;
import java.util.List;

import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class GameHex implements Drawable {

	protected HexCoordinate coordinate;
	private HexType type;
	private int diceRoll;
	private boolean selected;

	public GameHex(HexCoordinate coordinate, HexType type, int diceRoll) {
		super();
		this.coordinate = coordinate;
		this.type = type;
		this.diceRoll = diceRoll;
	}

	/**
	 * @return the coordinate
	 */
	public HexCoordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate
	 *            the coordinate to set
	 */
	public void setCoordinate(HexCoordinate coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * @return the type
	 */
	public HexType getType() {
		return type;
	}

	/**
	 * @return true if type is not water
	 */
	public boolean isLand() {
		return HexType.WATER != this.type;
	}
	
	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(HexType type) {
		this.type = type;
	}

	/**
	 * @return the diceRoll
	 */
	public int getDiceRoll() {
		return diceRoll;
	}

	/**
	 * @param diceRoll
	 *            the diceRoll to set
	 */
	public void setDiceRoll(int diceRoll) {
		this.diceRoll = diceRoll;
	}

	@Override
	public void deselect() {
		selected = false;
	}

	@Override
	public void select() {
		selected = true;
	}

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		Pair<double[], double[]> pair = HexMath.getAllCorners(coordinate, radius, xOffset, yOffset);

		drawHex(gc, pair);

		if (type != HexType.WATER) {
			drawOutline(gc, pair);

			Point2D center = HexMath.getHexCenter(coordinate, radius, xOffset, yOffset);
			double circleRadius = radius / 4.0;

			if (!HexType.BARREN.equals(type)) {
				drawCircle(gc, center, circleRadius);
				drawRoll(gc, center, radius / 5, circleRadius);
			}
		}
	}

	protected void drawCircle(GraphicsContext gc, Point2D center, double circleRadius) {
		gc.setFill(Color.WHITE.deriveColor(1, 1, 1, 0.5));
		gc.fillOval(center.getX() - circleRadius, center.getY() - circleRadius, circleRadius * 2, circleRadius * 2);
	}

	protected void drawOutline(GraphicsContext gc, Pair<double[], double[]> pair) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(3);
		for (int i = 0; i < 6; i++) {
			double startX = pair.getKey()[i];
			double startY = pair.getValue()[i];

			double endX = pair.getKey()[(i + 1) % 6];
			double endY = pair.getValue()[(i + 1) % 6];

			gc.strokeLine(startX, startY, endX, endY);
		}
	}

	protected void drawHex(GraphicsContext gc, Pair<double[], double[]> pair) {
		if (selected) {
			gc.setFill(type.getColor().darker());
		} else {
			gc.setFill(type.getColor());
		}

		gc.fillPolygon(pair.getKey(), pair.getValue(), 6);

		if (type == HexType.WATER) {
			gc.setStroke(type.getColor());
			gc.strokePolygon(pair.getKey(), pair.getValue(), 6);
		}
	}

	private void drawRoll(GraphicsContext gc, Point2D center, double textSize, double circleRadius) {
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(textSize));
		gc.fillText(Integer.toString(diceRoll), center.getX(), center.getY(), circleRadius * 2);
	}

	@Override
	public void calculateShape(double radius, double xOffset, double yOffset) {
		// not needed
	}

	@Override
	public boolean isSelected() {
		return selected;
	}
}