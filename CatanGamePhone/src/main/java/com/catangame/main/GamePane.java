package com.catangame.main;

import java.util.List;

import com.catangame.MapArea;
import com.catangame.control.CatanMouseListener.SelectionMode;
import com.catangame.game.Player;
import com.catangame.game.PlayerResources;
import com.catangame.game.ResourceCost;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.model.structures.Settlement;
import com.catangame.util.CatanUtils;
import com.catangame.util.FXUtils;

import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GamePane extends AnchorPane {

	private MapArea area;
	private Player player;

	public GamePane() {
		initialiseFX();
	}

	public void draw() {
		area.draw();
	}

	protected void onBuildSettlement(ActionEvent event) {
		List<Building> availableBuildings = CatanUtils.getAvailableSettlementLocations(area, player);

		area.setAvailableBuildings(availableBuildings);
		area.getListener().setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
		event.consume();
	}

	protected void onBuildRoad(ActionEvent event) {
		List<Road> availableRoads = CatanUtils.getAvailableRoadLocations(area, player);

		area.setAvailableRoads(availableRoads);
		area.getListener().setMode(SelectionMode.SELECT_POTENTIAL_ROAD);
		event.consume();
	}

	private void initialiseFX() {
		initialiseMap();
		player = area.getAllPlayers().get(0);
		area.setPlayer(player);
		initialiseButtonPanel();
		initialiseResourcePanel();
	}

	private void initialiseResourcePanel() {
		VBox resourceBox = getResourceBox();

		PlayerResources resources = player.getResources();
		resources.giveWheat(2);
		resources.giveSheep(2);
		resources.giveLumber(2);
		resources.giveBrick(2);
		resources.giveOre(2);

		HBox wheatResources = getResourcesBox("Wheat: ", resources.wheatCountProperty());
		HBox sheepResources = getResourcesBox("Sheep: ", resources.sheepCountProperty());
		HBox lumberResources = getResourcesBox("Lumber: ", resources.lumberCountProperty());
		HBox brickResources = getResourcesBox("Brick: ", resources.brickCountProperty());
		HBox oreResources = getResourcesBox("Ore: ", resources.oreCountProperty());

		resourceBox.getChildren().addAll(wheatResources, sheepResources, lumberResources, brickResources, oreResources);

		super.getChildren().add(resourceBox);
	}

	private HBox getResourcesBox(String string, IntegerProperty simpleIntegerProperty) {
		HBox resourceBox = new HBox();
		resourceBox.setAlignment(Pos.BASELINE_RIGHT);

		resourceBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

		resourceBox.setSpacing(10);
		resourceBox.setPadding(new Insets(10));

		resourceBox.getChildren().add(new Label(string));

		Label label = new Label();
		simpleIntegerProperty.addListener((obsV, oldV, newV) -> label.setText(Integer.toString(newV.intValue())));
		label.setText(Integer.toString(simpleIntegerProperty.get()));
		resourceBox.getChildren().add(label);

		return resourceBox;
	}

	private void initialiseButtonPanel() {
		VBox buttonBox = getButtonBox();

		ComboBox<SelectionMode> modeBox = getModeComboBox();
		Button placeBuildingButton = getPlaceBuildingButton();
		Button placeRoadButton = getPlaceRoadButton();

		buttonBox.getChildren().add(modeBox);
		buttonBox.getChildren().add(placeBuildingButton);
		buttonBox.getChildren().add(placeRoadButton);

		super.getChildren().add(buttonBox);
	}

	private Button getPlaceRoadButton() {
		Button button = new Button("Build road");

		button.setOnAction(this::onBuildRoad);
		
		ResourceCost cost = Road.COST;

		button.setDisable(!player.getResources().canAfford(cost));
		player.getResources().addListener(v -> {
			button.setDisable(!player.getResources().canAfford(cost));
			return (Void) null;
		});

		return button;
	}

	private Button getPlaceBuildingButton() {
		Button button = new Button("Build settlement");

		button.setOnAction(this::onBuildSettlement);

		ResourceCost cost = Settlement.COST;

		button.setDisable(!player.getResources().canAfford(cost));
		player.getResources().addListener(v -> {
			button.setDisable(!player.getResources().canAfford(cost));
			return (Void) null;
		});

		return button;
	}

	private ComboBox<SelectionMode> getModeComboBox() {
		ComboBox<SelectionMode> modeBox = new ComboBox<>();
		modeBox.getItems().addAll(SelectionMode.values());
		modeBox.getSelectionModel().select(area.getListener().getMode());
		modeBox.getSelectionModel().selectedItemProperty()
				.addListener((obsV, oldV, newV) -> area.getListener().setMode(newV));
		return modeBox;
	}

	private VBox getButtonBox() {
		VBox buttonBox = new VBox();
		AnchorPane.setTopAnchor(buttonBox, 10.0);
		AnchorPane.setLeftAnchor(buttonBox, 10.0);

		buttonBox.setSpacing(10.0);
		buttonBox.setPadding(new Insets(10.0));
		buttonBox.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
		return buttonBox;
	}

	private VBox getResourceBox() {
		VBox resourceBox = new VBox();
		AnchorPane.setBottomAnchor(resourceBox, 10.0);
		AnchorPane.setLeftAnchor(resourceBox, 10.0);

		resourceBox.setSpacing(10.0);
		resourceBox.setPadding(new Insets(10.0));
		resourceBox.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
		return resourceBox;
	}

	private void initialiseMap() {
		area = new MapArea();
		FXUtils.setAllAnchors(area, 0.0);
		super.getChildren().add(area);
	}
}