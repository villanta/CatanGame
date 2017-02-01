package com.catangame.main;

import com.catangame.MapArea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhoneApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setScene(new Scene(new MapArea(), 1920, 1080));
		arg0.show();
	}

}
