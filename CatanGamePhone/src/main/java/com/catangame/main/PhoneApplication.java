package com.catangame.main;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private GamePane pane;

	@Override
	public void start(Stage stage) throws Exception {
		pane = new GamePane();
		stage.setScene(new Scene(pane, 1920, 1080));
		stage.show();

		stage.widthProperty().addListener(this::resize);
		stage.heightProperty().addListener(this::resize);
		pane.draw();
	}

	private void resize(ObservableValue<? extends Number> obs, Number old, Number newV) {
		pane.draw();
	}
}
