package com.catangame.model;

import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

public class GameHex implements Drawable {

	private HexCoordinate coordinate;
	private HexType type;
	private int diceRoll;

	public GameHex(HexCoordinate coordinate, HexType type, int diceRoll) {
		super();
		this.coordinate = coordinate;
		this.type = type;
		this.diceRoll = diceRoll;
	}

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		Pair<double[], double[]> pair = HexMath.getAllCorners(coordinate, radius, xOffset, yOffset);

		drawHex(gc, pair);
		drawOutline(gc, pair);

		Point2D center = HexMath.getHexCenter(coordinate, radius, xOffset, yOffset);
		double circleRadius = radius / 4.0;

		if (!HexType.BARREN.equals(type)) {
			drawCircle(gc, center, circleRadius);
			drawRoll(gc, center, radius / 5, circleRadius);
		}
	}

	private void drawRoll(GraphicsContext gc, Point2D center, double textSize, double circleRadius) {
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(textSize));
		gc.fillText(Integer.toString(diceRoll), center.getX(), center.getY(), circleRadius * 2);
	}

	private void drawCircle(GraphicsContext gc, Point2D center, double circleRadius) {
		gc.setFill(Color.WHITE);
		gc.fillOval(center.getX() - circleRadius, center.getY() - circleRadius, circleRadius * 2,
				circleRadius * 2);
	}

	private void drawOutline(GraphicsContext gc, Pair<double[], double[]> pair) {

		gc.setLineWidth(3);
		for (int i = 0; i < 6; i++) {
			double startX = pair.getKey()[i];
			double startY = pair.getValue()[i];

			double endX = pair.getKey()[(i + 1) % 6];
			double endY = pair.getValue()[(i + 1) % 6];

			gc.strokeLine(startX, startY, endX, endY);
		}
	}

	private void drawHex(GraphicsContext gc, Pair<double[], double[]> pair) {
		gc.setFill(type.getColor());
		gc.fillPolygon(pair.getKey(), pair.getValue(), 6);
	}
}