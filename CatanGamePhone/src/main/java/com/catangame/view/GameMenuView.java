package com.catangame.view;

import com.catangame.control.GameViewListener;
import com.catangame.util.FXUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GameMenuView extends VBox {

	private StackPane stackPane;

	private Effect effect;

	private boolean isShowing = false;
	private Scene scene;

	private Parent oldRoot;

	private GameViewListener gameViewListener;

	public GameMenuView(Scene scene, GameViewListener gameViewListener) {
		super(10.0);
		super.setPadding(new Insets(10.0));
		super.setAlignment(Pos.CENTER);
		this.scene = scene;
		this.gameViewListener = gameViewListener;

		initialiseFX();
		// After this has been added to the scene, it will need to have it's
		// position updated to ensure it is centerred.
		sceneProperty().addListener(obsV -> Platform.runLater(this::updatePosition));
	}

	private void initialiseFX() {
		initialiseStackPane();
		addButtons();
		initialisePositionListeners();
		initialiseEffect();
	}

	private void initialiseStackPane() {
		stackPane = new StackPane();
		FXUtils.setAllAnchors(stackPane, 0.0);

		stackPane.getChildren().add(this);
	}

	private void initialiseEffect() {
		ColorAdjust parentDarkeningEffect = new ColorAdjust();
		parentDarkeningEffect.setBrightness(-0.5);
		effect = parentDarkeningEffect;
	}

	private void addButtons() {
		Button returnToGameButton = new Button("Return to game");
		Button exitGameButton = new Button("Exit game");

		returnToGameButton.setOnAction(event -> onReturnToGameAction(event));
		exitGameButton.setOnAction(event -> onExitGameAction(event));

		super.getChildren().addAll(returnToGameButton, exitGameButton);
	}

	private void onExitGameAction(ActionEvent event) {
		gameViewListener.closeGame();
	}

	private void onReturnToGameAction(ActionEvent event) {
		hide();
		event.consume();
	}

	private void initialisePositionListeners() {
		setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

		stackPane.widthProperty().addListener(obsV -> updatePosition());
		stackPane.heightProperty().addListener(obsV -> updatePosition());
		widthProperty().addListener(obsV -> updatePosition());
		heightProperty().addListener(obsV -> updatePosition());

		updatePosition();
	}

	private void updatePosition() {
		double xOffset = (stackPane.getWidth() - getWidth()) / 2.0;
		double yOffset = (stackPane.getHeight() - getHeight()) / 2.0;

		setLayoutX(xOffset);
		setLayoutY(yOffset);
		autosize();
	}

	public void toggle() {
		if (isShowing) {
			hide();
		} else {
			show();
		}
	}

	private void hide() {
		isShowing = false;

		stackPane.getChildren().remove(oldRoot);
		scene.setRoot(oldRoot);
		oldRoot.setMouseTransparent(false);

		oldRoot.setEffect(null);
	}

	private void show() {
		isShowing = true;

		oldRoot = scene.getRoot();
		scene.setRoot(stackPane);
		oldRoot.setMouseTransparent(true);

		stackPane.getChildren().add(oldRoot);
		toFront();

		oldRoot.setEffect(effect);

		Platform.runLater(this::updatePosition);
	}
}
