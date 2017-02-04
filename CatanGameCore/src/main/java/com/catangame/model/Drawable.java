package com.catangame.model;

import javafx.scene.canvas.GraphicsContext;

@FunctionalInterface
public interface Drawable {

	/**
	 * Method to draw this object on to the canvas.
	 */
	void draw(GraphicsContext gc, double radius, double xOffset, double yOffset);
}
