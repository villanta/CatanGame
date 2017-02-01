package com.catangame.util;

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class FXUtils {

	private FXUtils() {
		// Utility class needs no constructor
	}

	public static void setAllAnchors(Node n, double anchor) {
		AnchorPane.setTopAnchor(n, anchor);
		AnchorPane.setBottomAnchor(n, anchor);
		AnchorPane.setLeftAnchor(n, anchor);
		AnchorPane.setRightAnchor(n, anchor);
	}
}
