package com.catangame.model;

public class EdgeLocation {

	private VertexLocation start;
	private VertexLocation end;

	public EdgeLocation(VertexLocation start, VertexLocation end) {
		super();
		this.start = start;
		this.end = end;
	}

	/**
	 * @return the start
	 */
	public VertexLocation getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(VertexLocation start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public VertexLocation getEnd() {
		return end;
	}

	/**
	 * @param end
	 *            the end to set
	 */
	public void setEnd(VertexLocation end) {
		this.end = end;
	}

}
