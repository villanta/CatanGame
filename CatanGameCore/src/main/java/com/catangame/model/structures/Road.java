package com.catangame.model.structures;

import com.catangame.game.Player;
import com.catangame.game.ResourceCost;
import com.catangame.model.Drawable;
import com.catangame.model.EdgeLocation;
import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class Road implements Drawable {

	public static final ResourceCost COST = new ResourceCost(0, 0, 1, 1, 0);

	private EdgeLocation location;
	private Player player;
	private boolean selected;

	private Shape shape;

	public Road(EdgeLocation location, Player player) {
		super();
		this.location = location;
		this.player = player;
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

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		// start and end points in pixels (not accounting for offsets)
		Point2D start = HexMath.getHexCorner(
				HexMath.getHexCenter(location.getStart().getReferenceHex(), radius, xOffset, yOffset), radius,
				location.getStart().getVertexIndex());
		Point2D end = HexMath.getHexCorner(
				HexMath.getHexCenter(location.getEnd().getReferenceHex(), radius, xOffset, yOffset), radius,
				location.getEnd().getVertexIndex());

		Point2D deltaVector = end.subtract(start).multiply(0.25);

		Pair<double[], double[]> roadShape = getRoadShape(start.add(deltaVector), end.subtract(deltaVector), radius);

		gc.setLineWidth(radius / 6);

		if (selected) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(3);
			gc.setFill(getPlayer().getColor().darker());
		} else {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.setFill(getPlayer().getColor());
		}

		gc.fillPolygon(roadShape.getKey(), roadShape.getValue(), roadShape.getKey().length);
		gc.strokePolygon(roadShape.getKey(), roadShape.getValue(), roadShape.getKey().length);
	}

	public boolean isRoadSelected(double x, double y) {
		if (shape == null) {
			return false;
		} else {
			return shape.intersects(x, y, 1, 1);
		}
	}

	@Override
	public void deselect() {
		this.selected = false;
	}

	@Override
	public void select() {
		this.selected = true;
	}

	private Pair<double[], double[]> getRoadShape(Point2D start, Point2D end, double radius) {

		double[] xPos = new double[4];
		double[] yPos = new double[4];

		double lineWidth = radius / 8;
		double angle = getAngle(start, end);

		// point 1 is left side at start side.
		xPos[0] = start.getX() + (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[0] = start.getY() + (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		// point 2 is left side at end side.
		xPos[1] = end.getX() + (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[1] = end.getY() + (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		// point 3 is right side at end side
		xPos[2] = end.getX() - (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[2] = end.getY() - (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		// point 4 is right side at start side
		xPos[3] = start.getX() - (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[3] = start.getY() - (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		Polygon polygon = new Polygon();
		for (int i = 0; i < 4; i++) {
			polygon.getPoints().add(xPos[i]);
			polygon.getPoints().add(yPos[i]);
		}
		this.shape = polygon;

		return new Pair<>(xPos, yPos);
	}

	private double getAngle(Point2D start, Point2D end) {
		// 0, 120, -120
		double deltaY = end.getY() - start.getY();
		double deltaX = end.getX() - start.getX();

		if (deltaY < 0) { // left side of hex
			if (deltaX > 1) {
				return -120.0;
			} else if (deltaX < -1) {
				return 120.0;
			} else {
				return 180.0;
			}
		} else { // right side of hex
			if (deltaX > 1) {
				return 120.0;
			} else if (deltaX < -1) {
				return -120.0;
			} else {
				return 180.0;
			}
		}
	}

	@Override
	public void calculateShape(double radius, double xOffset, double yOffset) {
		// start and end points in pixels (not accounting for offsets)
		Point2D start = HexMath.getHexCorner(
				HexMath.getHexCenter(location.getStart().getReferenceHex(), radius, xOffset, yOffset), radius,
				location.getStart().getVertexIndex());
		Point2D end = HexMath.getHexCorner(
				HexMath.getHexCenter(location.getEnd().getReferenceHex(), radius, xOffset, yOffset), radius,
				location.getEnd().getVertexIndex());

		Point2D deltaVector = end.subtract(start).multiply(0.25);

		start = start.add(deltaVector);
		end = end.subtract(deltaVector);

		double[] xPos = new double[4];
		double[] yPos = new double[4];

		double lineWidth = radius / 8;
		double angle = getAngle(start, end);

		// point 1 is left side at start side.
		xPos[0] = start.getX() + (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[0] = start.getY() + (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		// point 2 is left side at end side.
		xPos[1] = end.getX() + (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[1] = end.getY() + (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		// point 3 is right side at end side
		xPos[2] = end.getX() - (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[2] = end.getY() - (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		// point 4 is right side at start side
		xPos[3] = start.getX() - (lineWidth / 2) * Math.cos(Math.toRadians(angle));
		yPos[3] = start.getY() - (lineWidth / 2) * Math.sin(Math.toRadians(angle));

		Polygon polygon = new Polygon();
		for (int i = 0; i < 4; i++) {
			polygon.getPoints().add(xPos[i]);
			polygon.getPoints().add(yPos[i]);
		}
		this.shape = polygon;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Road) {
			return this.location.equals(((Road) other).getLocation())
					&& player.getId() == ((Road) other).getPlayer().getId();
		} else {
			return false;
		}
	}

	@Override
	public boolean isSelected() {
		return selected;
	}
}
