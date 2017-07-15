package com.catangame.model.structures;

import com.catangame.model.game.Player;
import com.catangame.model.locations.VertexLocation;
import com.catangame.model.resources.ResourceCost;
import com.catangame.util.HexMath;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

public class Settlement extends Building {

	public static final ResourceCost COST = new ResourceCost(1, 1, 1, 1, 0);

	private Shape shape;

	public Settlement(VertexLocation location, Player player) {
		super(location, player);
	}

	@Override
	public void draw(GraphicsContext gc, double radius, double xOffset, double yOffset) {
		Pair<double[], double[]> settlementShape = getSettlementShape(radius, xOffset, yOffset);

		if (isSelected()) {
			gc.setStroke(Color.WHITE);
			gc.setLineWidth(3);
			gc.setFill(getPlayer().getColor().darker());
		} else {
			gc.setStroke(Color.BLACK);
			gc.setLineWidth(1);
			gc.setFill(getPlayer().getColor());
		}

		gc.fillPolygon(settlementShape.getKey(), settlementShape.getValue(), settlementShape.getKey().length);
		gc.strokePolygon(settlementShape.getKey(), settlementShape.getValue(), settlementShape.getKey().length);
	}

	private Pair<double[], double[]> getSettlementShape(double radius, double xOffset, double yOffset) {
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
	protected Shape getShape() {
		return shape;
	}

	@Override
	public void calculateShape(double radius, double xOffset, double yOffset) {
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
		if (other instanceof Settlement) {
			return getLocation().equals(((Settlement) other).getLocation());
		} else {
			return false;
		}
	}

	@Override
	protected int getResourceQuantityPerRoll() {
		return 1;
	}
}
