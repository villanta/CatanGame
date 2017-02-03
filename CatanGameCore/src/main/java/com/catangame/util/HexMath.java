package com.catangame.util;

import com.catangame.model.HexCoordinate;

import javafx.geometry.Point2D;
import javafx.util.Pair;

/**
 * http://www.redblobgames.com/grids/hexagons/
 * 
 * @author Jamie
 *
 */

public class HexMath {

	private HexMath() {
		// utility class needs no constructor
	}
	
	/**
	 * @param coord
	 * @param radius
	 * @param xoffset pixel offset in x axis to ensure the pixel drawn is in the center (instead of the top left corner)
	 * @param yoffset pixel offset in y axis as explained above.
	 * @return
	 */
	public static Pair<double[], double[]> getAllCorners(HexCoordinate coord, double radius, double xoffset, double yoffset) {
		double[] xVals = new double[6];
		double[] yVals = new double[6];
		
		Point2D center = getHexCenter(coord, radius);
		
		for (int i = 0; i<6; i++) {
			Point2D corner = getHexCorner(center, radius, i);
			xVals[i] = corner.getX() + xoffset;
			yVals[i] = corner.getY() + yoffset;
		}
		
		return new Pair<>(xVals, yVals);
	}
	
	/**
	 * 
	 * @param center
	 *            Center coordinates
	 * @param radius
	 *            Distance from center to the corner
	 * @param corner
	 *            Which corner? corner pointing up is 0, then clockwise.
	 * @return
	 */
	public static Point2D getHexCorner(Point2D center, double radius, int corner) {
		double degrees = (((corner * 60.0) + 30.0) + 240.0) % 360.0;
		double radians = Math.toRadians(degrees);
		
		return new Point2D(center.getX() + radius * Math.cos(radians), center.getY() + radius * Math.sin(radians));
	}
	
	/**
	 * 
	 * @param coord Cube coordinat of hex.
	 * @param radius Distance from center to corner.
	 * @return
	 */
	public static Point2D getHexCenter(HexCoordinate coord, double radius) {
		// convert to axial
		double q = coord.getX();
		double r = coord.getZ();
		
		// convert to pixel
		double x = radius * Math.sqrt(3.0) * (q + r/2);
		double y = radius * 3/2 * r;
		return new Point2D(x, y);
	}
}
