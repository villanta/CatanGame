package com.catangame.game;

import com.catangame.game.CatanColour.CatanColourEnum;

import javafx.scene.paint.Color;

public class Player {

	private int id;
	private String name;
	private CatanColour color;
	private PlayerResources playerResources;

	public Player() {
		// empty constructor for kryo
	}
	
	public Player(int id, String name, Color color) {
		super();
		this.id = id;
		this.name = name;
		this.color = new CatanColour(CatanColourEnum.getColour(color.toString()));
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
	 * 
	 * @return Player Name String
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            new player name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return Color.valueOf(color.getColourEnum().getId());
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(CatanColour color) {
		this.color = color;
	}

	public PlayerResources getResources() {
		return playerResources;
	}
}
