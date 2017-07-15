package com.catangame.model.locations;

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

	@Override
	public boolean equals(Object other) {
		if (other instanceof EdgeLocation) {
			return (start.equals(((EdgeLocation) other).getStart()) && end.equals(((EdgeLocation) other).getEnd()))
					|| (start.equals(((EdgeLocation) other).getEnd()) && end.equals(((EdgeLocation) other).getStart()));
		} else {
			return false;
		}
	}

}
