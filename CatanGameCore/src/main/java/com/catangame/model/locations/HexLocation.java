package com.catangame.model.locations;

/**
 * Hex grid uses pointy tops.
 * 
 * @author Jamie
 *
 */
public class HexLocation {

	private int x;
	private int y;
	private int z;
	
	public HexLocation() {
		// kryo constructor
	}

	public HexLocation(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public int getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public boolean equals(Object other) {
		
		if (!(other instanceof HexLocation)) {
			return false;
		} else {
			HexLocation otherHex = (HexLocation) other;
			return otherHex.getX() == x && otherHex.getY() == y && otherHex.getZ() == z;
		}
	}
	
	@Override
	public String toString() {
		return String.format("X: %d, Y: %d, Z: %d.", x, y, z);
	}

	public HexLocation deriveHex(int dx, int dy, int dz) {
		return new HexLocation(x + dx, y+dy, z+dz);
	}
}
