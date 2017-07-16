package com.catangame.view;

import com.catangame.model.game.Player;
import com.catangame.model.resources.PlayerResources;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PlayerResourceView extends HBox implements PlayerResourceListener {

	private Player player;
	private PlayerResources resources;

	public PlayerResourceView(Player player) {
		super(10.0);
		this.player = player;
		resources = player.getResources();

		AnchorPane.setBottomAnchor(this, 10.0);
		AnchorPane.setLeftAnchor(this, 10.0);

		setPadding(new Insets(10.0));
		setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
		update();
		resources.addListener(this);
	}

	public void update() {

		HBox wheatResources = getResourcesBox("Wheat: ", resources.getWheatCount());
		HBox sheepResources = getResourcesBox("Sheep: ", resources.getSheepCount());
		HBox lumberResources = getResourcesBox("Lumber: ", resources.getLumberCount());
		HBox brickResources = getResourcesBox("Brick: ", resources.getBrickCount());
		HBox oreResources = getResourcesBox("Ore: ", resources.getOreCount());

		getChildren().clear();
		getChildren().addAll(wheatResources, sheepResources, lumberResources, brickResources, oreResources);
	}

	private HBox getResourcesBox(String string, int value) {
		HBox box = new HBox();
		box.setAlignment(Pos.BASELINE_RIGHT);

		box.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		box.setSpacing(10);
		box.setPadding(new Insets(10));

		box.getChildren().add(new Label(string));

		Label label = new Label();
		label.setText(Integer.toString(value));
		box.getChildren().add(label);

		return box;
	}

	@Override
	public void resourcesUpdated() {
		Platform.runLater(() -> update());
	}
}
