package com.catangame.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import com.catangame.control.GameMouseListener.SelectionMode;
import com.catangame.game.GameView;
import com.catangame.game.Player;
import com.catangame.game.PlayerResources;
import com.catangame.game.ResourceCost;
import com.catangame.model.structures.Building;
import com.catangame.model.structures.Road;
import com.catangame.model.structures.Settlement;
import com.catangame.util.CatanUtils;
import com.catangame.util.FXUtils;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GamePane extends AnchorPane {

	private GameView gameView;
	private Player player;
	private DoubleProperty scale = new SimpleDoubleProperty(0);
	private Map<Node, Double> map = new HashMap<>();

	public GamePane() {
		initialiseFX();
	}

	public void draw() {
		gameView.draw();
	}

	protected void onBuildSettlement(ActionEvent event) {
		List<Building> availableBuildings = CatanUtils.getAvailableSettlementLocations(gameView.getModel(), player);

		gameView.getModel().repopulateAvailableBuildings(availableBuildings);
		gameView.getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
		event.consume();
	}

	protected void onBuildRoad(ActionEvent event) {
		List<Road> availableRoads = CatanUtils.getAvailableRoadLocations(gameView.getModel(), player);

		gameView.getModel().repopulateAvailableRoads(availableRoads);
		gameView.getMouseListener().setMode(SelectionMode.SELECT_POTENTIAL_ROAD);
		event.consume();
	}

	private void initialiseFX() {
		initialiseMap();
		player = gameView.getModel().getAllPlayers().get(0);
		gameView.setPlayer(player);
		gameView.start();
		initialiseButtonPanel();
		initialiseResourcePanel();
	}

	private void initialiseMap() {
		gameView = new GameView();
		Pane pane = gameView.getMapView();
		FXUtils.setAllAnchors(pane, 0.0);
		super.getChildren().add(pane);
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

		Node buildLabel = getBuildLabel();
		Node placeBuildingButton = getPlaceBuildingButton();
		Node placeRoadButton = getPlaceRoadButton();

		buttonBox.getChildren().add(buildLabel);
		buttonBox.getChildren().add(placeBuildingButton);
		buttonBox.getChildren().add(placeRoadButton);

		super.getChildren().add(buttonBox);
	}

	private Node getBuildLabel() {
		Label buildLabel = new Label("Build");
		buildLabel.setAlignment(Pos.BASELINE_CENTER);
		FXUtils.setAllAnchors(buildLabel, 0.0);
		AnchorPane pane = new AnchorPane(buildLabel);
		pane.prefHeightProperty().bind(pane.widthProperty().divide(2));

		this.scale.addListener((obsV, oldV, newV) -> {
			buildLabel.lookup(".text").setScaleX(scale.get());
			buildLabel.lookup(".text").setScaleY(scale.get());
		});
		return pane;
	}

	private Node getPlaceRoadButton() {
		Button button = new Button("Road");

		button.setOnAction(this::onBuildRoad);

		ResourceCost cost = Road.COST;

		button.setDisable(!player.getResources().canAfford(cost));
		player.getResources().addListener(v -> {
			button.setDisable(!player.getResources().canAfford(cost));
			return (Void) null;
		});

		FXUtils.setAllAnchors(button, 0.0);
		AnchorPane pane = new AnchorPane(button);
		pane.prefHeightProperty().bind(pane.widthProperty().divide(2));
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleText(button));
		scale.addListener((obsV, oldV, newV) -> {
			button.lookup(".text").setScaleX(scale.get());
			button.lookup(".text").setScaleY(scale.get());
		});

		return pane;
	}

	private Node getPlaceBuildingButton() {
		Button button = new Button("Settlement");

		button.setOnAction(this::onBuildSettlement);

		ResourceCost cost = Settlement.COST;

		button.setDisable(!player.getResources().canAfford(cost));
		player.getResources().addListener(v -> {
			button.setDisable(!player.getResources().canAfford(cost));
			return (Void) null;
		});

		FXUtils.setAllAnchors(button, 0.0);
		AnchorPane pane = new AnchorPane(button);
		pane.prefHeightProperty().bind(pane.widthProperty().divide(2));
		button.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> scaleText(button));
		scale.addListener((obsV, oldV, newV) -> {
			button.lookup(".text").setScaleX(scale.get());
			button.lookup(".text").setScaleY(scale.get());
		});

		return pane;
	}

	private VBox getButtonBox() {
		VBox buttonBox = new VBox();
		AnchorPane.setTopAnchor(buttonBox, 10.0);
		AnchorPane.setLeftAnchor(buttonBox, 10.0);
		buttonBox.prefWidthProperty().bind(gameView.getMapView().widthProperty().divide(10));

		buttonBox.prefHeightProperty().bind(gameView.getMapView().heightProperty().divide(4));

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

	private void scaleText(Button button) {
		double w = button.getWidth();
		double h = button.getHeight();

		double bw = button.prefWidth(-1);
		double bh = button.prefHeight(-1);

		if (w == 0 || h == 0 || bw == 0 || bh == 0) {
			return;
		}

		double hScale = w / bw;
		double vScale = h / bw;

		updateScaling(button, Math.max(hScale, vScale));
	}

	private void updateScaling(Node source, double scale) {
		map.put(source, scale);
		OptionalDouble newLowest = map.values().stream().mapToDouble(d -> d).min();
		if (newLowest.isPresent()) {
			this.scale.set(newLowest.getAsDouble());
		}
	}
}