package com.catangame.model;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

	/**
	 * Method to draw this object on to the canvas.
	 */
	void draw(GraphicsContext gc, double radius, double xOffset, double yOffset);

	/**
	 * Calculate the shape of the component
	 */
	void calculateShape(double radius, double xOffset, double yOffset);
	
	/**
	 * Method to deselect this component
	 */
	void deselect();

	/**
	 * Method to select this component
	 */
	void select();
}
