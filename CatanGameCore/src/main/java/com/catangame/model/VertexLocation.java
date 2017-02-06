package com.catangame.model;

public class VertexLocation {

	private HexCoordinate referenceHex;
	private int vertexIndex;

	public VertexLocation(HexCoordinate referenceHex, int vertexIndex) {
		super();
		this.referenceHex = referenceHex;
		this.vertexIndex = vertexIndex;
	}

	/**
	 * @return the referenceHex
	 */
	public HexCoordinate getReferenceHex() {
		return referenceHex;
	}

	/**
	 * @param referenceHex
	 *            the referenceHex to set
	 */
	public void setReferenceHex(HexCoordinate referenceHex) {
		this.referenceHex = referenceHex;
	}

	/**
	 * @return the vertexIndex
	 */
	public int getVertexIndex() {
		return vertexIndex;
	}

	/**
	 * @param vertexIndex
	 *            the vertexIndex to set
	 */
	public void setVertexIndex(int vertexIndex) {
		this.vertexIndex = vertexIndex;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof VertexLocation)) {
			return false;
		} else {
			VertexLocation otherVertex = (VertexLocation) other;
			return referenceHex.equals(otherVertex.getReferenceHex()) && vertexIndex == otherVertex.getVertexIndex();
		}
	}
}
