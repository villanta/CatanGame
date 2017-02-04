package com.catangame;

import com.catangame.model.VertexLocation;
import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

public class City extends Building {

	public City(VertexLocation location, Color playerColor) {
		super(location, playerColor);
	}

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		gc.setFill(getPlayerColor());
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);

		Pair<double[], double[]> cityShape = getCityShape(radius, xOffset, yOffset);

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

		return new Pair<>(xPos, yPos);
	}

}
