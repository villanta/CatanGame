package com.catangame.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import com.catangame.model.game.Player;
import com.catangame.model.resources.ResourceCost;
import com.catangame.model.structures.Road;
import com.catangame.model.structures.Settlement;
import com.catangame.util.FXUtils;
import com.catangame.view.interfaces.CommandViewListener;

import javafx.beans.property.DoubleProperty;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CommandView extends VBox {

	private static final String TEST_SUFFIX = ".text";

	private Map<Node, Double> map = new HashMap<>();

	private DoubleProperty scale;

	private Player player;
	
	private List<CommandViewListener> commandViewListeners = new ArrayList<>();

	public CommandView(Player player, DoubleProperty scale) {
		super(10.0);
		this.player = player;
		this.scale = scale;
		AnchorPane.setTopAnchor(this, 10.0);
		AnchorPane.setLeftAnchor(this, 10.0);

		setPadding(new Insets(10.0));
		setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));

		Node buildLabel = getBuildLabel();
		Node placeBuildingButton = getPlaceBuildingButton();
		Node placeRoadButton = getPlaceRoadButton();

		getChildren().add(buildLabel);
		getChildren().add(placeBuildingButton);
		getChildren().add(placeRoadButton);
	}

	private Node getBuildLabel() {
		Label buildLabel = new Label("Build");
		buildLabel.setAlignment(Pos.BASELINE_CENTER);
		FXUtils.setAllAnchors(buildLabel, 0.0);
		AnchorPane pane = new AnchorPane(buildLabel);
		pane.prefHeightProperty().bind(pane.widthProperty().divide(2));

		this.scale.addListener((obsV, oldV, newV) -> {
			buildLabel.lookup(TEST_SUFFIX).setScaleX(scale.get());
			buildLabel.lookup(TEST_SUFFIX).setScaleY(scale.get());
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
			button.lookup(TEST_SUFFIX).setScaleX(scale.get());
			button.lookup(TEST_SUFFIX).setScaleY(scale.get());
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
			button.lookup(TEST_SUFFIX).setScaleX(scale.get());
			button.lookup(TEST_SUFFIX).setScaleY(scale.get());
		});

		return pane;
	}

	protected void onBuildSettlement(ActionEvent event) {
		commandViewListeners.stream().forEach(listener -> listener.onBuildSettlement());
		
		
		event.consume();
	}

	protected void onBuildRoad(ActionEvent event) {
		commandViewListeners.stream().forEach(listener -> listener.onBuildRoad());
		
		event.consume();
	}

	private void scaleText(Button button) {
		double w = button.getWidth();
		double h = button.getHeight();

		double bw = button.prefWidth(-1);
		double bh = button.prefHeight(-1);

		if (Double.doubleToLongBits(w) == 0 || Double.doubleToLongBits(h) == 0 || Double.doubleToLongBits(bw) == 0
				|| Double.doubleToLongBits(bh) == 0) {
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

	public void addCommandListener(CommandViewListener listener) {
		commandViewListeners.add(listener);
	}
	
	public void removeCommandListener(CommandViewListener listener) {
		commandViewListeners.remove(listener);
	}
}
