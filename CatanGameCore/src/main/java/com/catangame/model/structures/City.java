package com.catangame.model.structures;

import com.catangame.game.Player;
import com.catangame.game.ResourceCost;
import com.catangame.model.VertexLocation;
import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class City extends Building {

	public static final ResourceCost COST = new ResourceCost(2, 0, 0, 0, 3);

	private Shape shape;

	public City(VertexLocation location, Player player) {
		super(location, player);
	}

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		Pair<double[], double[]> cityShape = getCityShape(radius, xOffset, yOffset);

		if (isSelected()) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(3);
			gc.setFill(getPlayer().getColor().darker());
		} else {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.setFill(getPlayer().getColor());
		}

		gc.fillPolygon(cityShape.getKey(), cityShape.getValue(), cityShape.getKey().length);
		gc.strokePolygon(cityShape.getKey(), cityShape.getValue(), cityShape.getKey().length);
	}

	private Pair<double[], double[]> getCityShape(double radius, double xOffset, double yOffset) {
		// 5 point house shape, square with a triangle on top
		double width = radius / 3;
		double[] xPos = new double[5];
		double[] yPos = new double[5];

		Point2D location = HexMath.getCenterOfVertex(getLocation(), radius, xOffset, yOffset);

		// bottom left
		xPos[0] = location.getX() - (width / 2);
		yPos[0] = location.getY() + (width * 0.4);

		// bottom right
		xPos[1] = location.getX() + (width / 2);
		yPos[1] = location.getY() + (width * 0.4);

		// right
		xPos[2] = location.getX() + (width / 2);
		yPos[2] = location.getY() - (width * 0.4);

		// top
		xPos[3] = location.getX();
		yPos[3] = location.getY() - width * 0.8;

		// left
		xPos[4] = location.getX() - (width / 2);
		yPos[4] = location.getY() - (width * 0.4);

		Polygon polygon = new Polygon();
		for (int i = 0; i < 5; i++) {
			polygon.getPoints().add(xPos[i]);
			polygon.getPoints().add(yPos[i]);
		}
		this.shape = polygon;

		return new Pair<>(xPos, yPos);
	}

	@Override
	public Shape getShape() {
		return shape;
	}

	@Override
	public void calculateShape(double radius, double xOffset, double yOffset) {
		// 5 point house shape, square with a triangle on top
		double width = radius / 3;
		double[] xPos = new double[5];
		double[] yPos = new double[5];

		Point2D location = HexMath.getCenterOfVertex(getLocation(), radius, xOffset, yOffset);

		// bottom left
		xPos[0] = location.getX() - (width / 2);
		yPos[0] = location.getY() + (width * 0.4);

		// bottom right
		xPos[1] = location.getX() + (width / 2);
		yPos[1] = location.getY() + (width * 0.4);

		// right
		xPos[2] = location.getX() + (width / 2);
		yPos[2] = location.getY() - (width * 0.4);

		// top
		xPos[3] = location.getX();
		yPos[3] = location.getY() - width * 0.8;

		// left
		xPos[4] = location.getX() - (width / 2);
		yPos[4] = location.getY() - (width * 0.4);

		Polygon polygon = new Polygon();
		for (int i = 0; i < 5; i++) {
			polygon.getPoints().add(xPos[i]);
			polygon.getPoints().add(yPos[i]);
		}
		this.shape = polygon;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof City) {
			return getLocation().equals(((City) other).getLocation());
		} else {
			return false;
		}
	}
}
