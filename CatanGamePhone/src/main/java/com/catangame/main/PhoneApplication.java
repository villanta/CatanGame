package com.catangame.main;

import com.catangame.interfaces.ClosableView;
import com.catangame.menu.MainMenuPane;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PhoneApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private Stage stage;

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		AnchorPane pane = new MainMenuPane();
		stage.setOnCloseRequest(this::onCloseRequest);
		stage.setScene(new Scene(pane, 1024, 768));
		stage.show();

		stage.widthProperty().addListener(this::resize);
		stage.heightProperty().addListener(this::resize);
	}

	private void resize(ObservableValue<? extends Number> obs, Number old, Number newV) {
		Parent root = stage.getScene().getRoot();
		if (root instanceof ClosableView) {
			((ClosableView)root).onResize();
		}
	}
	
	private void onCloseRequest(WindowEvent event) {
		Parent root = stage.getScene().getRoot();
		if (root instanceof ClosableView) {
			((ClosableView)root).onClose();
		}
		stage.close();
		event.consume();		
	}
}
