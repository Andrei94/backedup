package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class WindowOpener {
	public static <T> void openWindow(String fxmlFile, T windowPayload) {
		Stage stage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
		try {
			stage.setScene(new Scene(fxmlLoader.load()));
		} catch(IOException e) {
			e.printStackTrace();
		}
		fxmlLoader.<WindowPayload<T>>getController().setPayload(windowPayload);
		stage.setResizable(false);
		stage.show();
	}
}
