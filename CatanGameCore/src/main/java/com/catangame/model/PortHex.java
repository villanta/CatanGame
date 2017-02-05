package com.catangame.model;

import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class PortHex extends GameHex {

	private ResourceType resourceType;
	private int startPortVertexIndex;

	/**
	 * 
	 * @param coordinate
	 * @param resourceType
	 *            resource type of port, if null is a generic port
	 * @param startPortVertexIndex
	 */
	public PortHex(HexCoordinate coordinate, ResourceType resourceType, int startPortVertexIndex) {
		super(coordinate, HexType.WATER, 0);

		this.resourceType = resourceType;
		this.startPortVertexIndex = startPortVertexIndex;
	}

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		Pair<double[], double[]> pair = HexMath.getAllCorners(coordinate, radius, xOffset, yOffset);
		
		drawHex(gc, pair);

		Point2D center = HexMath.getHexCenter(coordinate, radius, xOffset, yOffset);
		double circleRadius = radius / 4.0;

		// draw ports
		drawPorts(gc, center, radius);

		// colour of circle according to resource type
		drawCircle(gc, center, circleRadius);
	}

	@Override
	protected void drawCircle(GraphicsContext gc, Point2D center, double circleRadius) {
		if (resourceType != null) {
			gc.setFill(resourceType.getColor());
		} else {
			// generic colour
		}
		gc.fillOval(center.getX() - circleRadius, center.getY() - circleRadius, circleRadius * 2, circleRadius * 2);
	}

	private void drawPorts(GraphicsContext gc, Point2D center, Double radius) {
		Point2D port1 = HexMath.getHexCorner(center, radius, startPortVertexIndex);
		Point2D port2 = HexMath.getHexCorner(center, radius, startPortVertexIndex + 1);

		gc.setLineDashes(radius / 10);
		gc.setLineWidth(1.5);
		gc.setStroke(Color.BLACK);
		gc.strokeLine(center.getX(), center.getY(), port1.getX(), port1.getY());
		gc.strokeLine(center.getX(), center.getY(), port2.getX(), port2.getY());
		gc.setLineDashes();

	}

	protected ResourceType getResourceType() {
		return resourceType;
	}

	protected void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

}
