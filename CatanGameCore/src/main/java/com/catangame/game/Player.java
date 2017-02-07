package com.catangame.game;

import javafx.scene.paint.Color;

public class Player {

	private int id;
	private Color color;
	private PlayerResources playerResources;

	public Player(int id, Color color) {
		super();
		this.id = id;
		this.color = color;
		playerResources = new PlayerResources();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	public PlayerResources getResources() {
		return playerResources;
	}
}
