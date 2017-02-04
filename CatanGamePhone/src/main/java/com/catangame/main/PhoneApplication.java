package com.catangame.main;

import com.catangame.MapArea;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private MapArea mapArea;

	@Override
	public void start(Stage arg0) throws Exception {
		mapArea = new MapArea();
		arg0.setScene(new Scene(mapArea, 1920, 1080));
		arg0.show();

		arg0.widthProperty().addListener(this::resize);
		arg0.heightProperty().addListener(this::resize);
		mapArea.draw();
	}

	private void resize(ObservableValue<? extends Number> obs, Number old, Number newV) {
		mapArea.draw();
	}
}
