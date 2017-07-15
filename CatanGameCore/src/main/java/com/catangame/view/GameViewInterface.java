package com.catangame.view;

import javafx.geometry.Point2D;

public interface GameViewInterface {

	void zoomOut();
	void zoomIn();

	void draw();

	double getTrueXOffset();
	double getTrueYOffset();

	Point2D getOffset();
	void setOffset(Point2D offset);

}
