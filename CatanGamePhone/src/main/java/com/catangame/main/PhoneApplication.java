package com.catangame.main;

import com.catangame.menu.MainMenuPane;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PhoneApplication extends Application {
	private AnchorPane pane;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane = new MainMenuPane();
		stage.setScene(new Scene(pane, 1024, 768));
		stage.show();

		stage.widthProperty().addListener(this::resize);
		stage.heightProperty().addListener(this::resize);
	}

	private void resize(ObservableValue<? extends Number> obs, Number old, Number newV) {
		//pane.draw();
	}
}
