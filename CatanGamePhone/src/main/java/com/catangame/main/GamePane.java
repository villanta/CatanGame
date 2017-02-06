package com.catangame.main;

import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import com.catangame.Building;
import com.catangame.CatanMouseListener.SelectionMode;
import com.catangame.MapArea;
import com.catangame.model.Player;
import com.catangame.util.CatanUtils;
import com.catangame.util.FXUtils;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GamePane extends AnchorPane {

	private MapArea area;

	public GamePane() {
		initialiseFX();
	}

	public void draw() {
		area.draw();
	}

	protected void onBuildSettlement(ActionEvent event) {
		List<Building> availableBuildings = CatanUtils.getAvailableSettlementLocations(area, new Player(0, Color.RED));

		area.setAvailableBuildings(availableBuildings);
		area.getListener().setMode(SelectionMode.SELECT_POTENTIAL_BUILDING);
		event.consume();
	}

	private void initialiseFX() {
		initialiseMap();
		initialiseButtonPanel();
	}

	private void initialiseButtonPanel() {
		VBox buttonBox = getButtonBox();

		ComboBox<SelectionMode> modeBox = getModeComboBox();
		Button placeBuildingButton = getPlaceBuildingButton();

		buttonBox.getChildren().add(new Button("TEST"));
		buttonBox.getChildren().add(modeBox);
		buttonBox.getChildren().add(placeBuildingButton);

		super.getChildren().add(buttonBox);
	}

	private Button getPlaceBuildingButton() {
		Button button = new Button("Build settlement");

		button.setOnAction(this::onBuildSettlement);

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
		VBox buttonPane = new VBox();
		AnchorPane.setTopAnchor(buttonPane, 10.0);
		AnchorPane.setLeftAnchor(buttonPane, 10.0);

		buttonPane.setSpacing(10.0);
		buttonPane.setPadding(new Insets(10.0));
		buttonPane.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(10), Insets.EMPTY)));
		return buttonPane;
	}

	private void initialiseMap() {
		area = new MapArea();
		FXUtils.setAllAnchors(area, 0.0);
		super.getChildren().add(area);
	}
}