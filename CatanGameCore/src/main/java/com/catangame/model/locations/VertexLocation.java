package com.catangame.model.locations;

import java.util.List;

import com.catangame.util.CatanUtils;

public class VertexLocation {

	private HexLocation referenceHex;
	private int vertexIndex;

	public VertexLocation(HexLocation referenceHex, int vertexIndex) {
		super();
		this.referenceHex = referenceHex;
		this.vertexIndex = vertexIndex;
	}

	/**
	 * @return the referenceHex
	 */
	public HexLocation getReferenceHex() {
		return referenceHex;
	}

	/**
	 * @param referenceHex
	 *            the referenceHex to set
	 */
	public void setReferenceHex(HexLocation referenceHex) {
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

	public List<HexLocation> getAdjacentHexes() {
		return CatanUtils.getAdjacentHexes(this);
		
	}
}
