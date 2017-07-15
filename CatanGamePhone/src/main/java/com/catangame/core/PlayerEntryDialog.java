package com.catangame.core;

import java.util.Optional;

import com.catangame.model.game.Player;
import com.catangame.util.FXUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class PlayerEntryDialog extends Stage {

	private Window parentWindow;
	private AnchorPane rootPane;
	
	private TextField playerNameField;
	private ComboBox<Color> colourComboBox;
	private Player player;

	public PlayerEntryDialog(Window parentWindow) {
		super();
		this.parentWindow = parentWindow;
		initialiseFX();
	}

	private void initialiseFX() {
		rootPane = new AnchorPane();
		setScene(new Scene(rootPane));
		GridPane gp = initialiseGridPane();
		
		Label playerNameLabel = new Label("Name: ");
		Label colourLabel = new Label("Preferred Color: ");
		
		playerNameField = new TextField();
		playerNameField.setPromptText("Enter name here...");
		
		colourComboBox = new ComboBox<>(getColours());
		
		Button acceptButton = new Button("Accept");
		acceptButton.setOnAction(this::onAccept);
		
		// row 1
		gp.add(playerNameLabel, 0, 1);
		gp.add(playerNameField, 1, 1);
		
		// row 2
		gp.add(colourLabel, 0, 2);
		gp.add(colourComboBox, 1, 2);
		
		// row 3
		gp.add(acceptButton, 1, 3);
	}

	private ObservableList<Color> getColours() {
		ObservableList<Color> list = FXCollections.observableArrayList();
		
		list.addAll(Color.RED, Color.BLUE, Color.GREEN);
		
		return list;
	}

	private GridPane initialiseGridPane() {
		GridPane gp = new GridPane();
		FXUtils.setAllAnchors(gp, 10.0);
		rootPane.getChildren().add(gp);

		RowConstraints rc1 = new RowConstraints();
		rc1.setVgrow(Priority.NEVER);
		RowConstraints rc2 = new RowConstraints();
		rc2.setVgrow(Priority.NEVER);
		RowConstraints rc3 = new RowConstraints();
		rc3.setVgrow(Priority.NEVER);
		RowConstraints rc4 = new RowConstraints();
		rc4.setVgrow(Priority.NEVER);
		
		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setHgrow(Priority.NEVER);
		ColumnConstraints cc2 = new ColumnConstraints();
		cc2.setHgrow(Priority.NEVER);

		return gp;
	}

	private void onAccept(ActionEvent event) {
		String playerName = playerNameField.getText();
		Color playerColour = colourComboBox.getValue();
		
		if (playerColour != null && !playerName.isEmpty()) {
			player = new Player(0, playerName, playerColour);
		}
		event.consume();
	}
	
	@Override
	public void showAndWait() {
		this.initOwner(parentWindow);
		this.initModality(Modality.APPLICATION_MODAL);
		super.showAndWait();
	}

	public Optional<Player> getPlayer() {
		return Optional.of(player);
	}
}
