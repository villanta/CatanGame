package com.catangame.view;

import com.catangame.model.game.Player;
import com.catangame.model.resources.PlayerResources;

import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class PlayerResourceView extends HBox {

	private Player player;

	public PlayerResourceView(Player player) {
		super(10.0);
		this.player = player;
		
		AnchorPane.setBottomAnchor(this, 10.0);
		AnchorPane.setLeftAnchor(this, 10.0);

		setPadding(new Insets(10.0));
		setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
		update();
	}
	
	public void update() {
		PlayerResources resources = player.getResources();

		HBox wheatResources = getResourcesBox("Wheat: ", resources.wheatCountProperty());
		HBox sheepResources = getResourcesBox("Sheep: ", resources.sheepCountProperty());
		HBox lumberResources = getResourcesBox("Lumber: ", resources.lumberCountProperty());
		HBox brickResources = getResourcesBox("Brick: ", resources.brickCountProperty());
		HBox oreResources = getResourcesBox("Ore: ", resources.oreCountProperty());

		getChildren().clear();
		getChildren().addAll(wheatResources, sheepResources, lumberResources, brickResources, oreResources);
	}
	
	private HBox getResourcesBox(String string, IntegerProperty simpleIntegerProperty) {
		HBox box = new HBox();
		box.setAlignment(Pos.BASELINE_RIGHT);

		box.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		box.setSpacing(10);
		box.setPadding(new Insets(10));

		box.getChildren().add(new Label(string));

		Label label = new Label();
		simpleIntegerProperty.addListener((obsV, oldV, newV) -> label.setText(Integer.toString(newV.intValue())));
		label.setText(Integer.toString(simpleIntegerProperty.get()));
		box.getChildren().add(label);

		return box;
	}
}
