package com.catangame.core;

import java.util.Arrays;
import java.util.List;

import javafx.scene.paint.Color;

public class CatanColour {

	public enum CatanColourEnum {
		BLUE(Color.BLUE.toString()), RED(Color.RED.toString()), GREEN(Color.GREEN.toString()), ORANGE(
				Color.ORANGE.toString()), PURPLE(Color.PURPLE.toString()), YELLOW(Color.YELLOW.toString());

		private String id;

		CatanColourEnum(String id) {
			this.id = id;
		}

		public String getId() {
			return id;
		}

		@Override
		public String toString() {
			return id;
		}

		public static CatanColourEnum getColour(String colourId) {
			List<CatanColourEnum> list = Arrays.asList(CatanColourEnum.values());
			return list.stream().filter(colorEnum -> colorEnum.getId().equals(colourId)).findFirst().orElse(null);
		}
	}

	private CatanColourEnum colourEnum;

	public CatanColour(CatanColourEnum colourEnum) {
		this.colourEnum = colourEnum;
	}

	public CatanColour() {
		// empty constructor for kryo
	}

	/**
	 * @return the colorEnum
	 */
	public CatanColourEnum getColourEnum() {
		return colourEnum;
	}

	/**
	 * @param colourEnum
	 *            the colorEnum to set
	 */
	public void setColorEnum(CatanColourEnum colourEnum) {
		this.colourEnum = colourEnum;
	}

	public Color getActualColor() {
		return Color.valueOf(colourEnum.getId());
	}
}
