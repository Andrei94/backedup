package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;

import java.io.IOException;

public class WindowOpener {
	static void openInformationDialog(String title, String text) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(text);
		alert.getButtonTypes().setAll(
				new ButtonType("OK", ButtonBar.ButtonData.OK_DONE)
		);
		alert.showAndWait();
	}

	static <T> T openDialog(String fxmlFile, int width, int height) {
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
		try {
			stage.setScene(new Scene(fxmlLoader.load(), width, height));
		} catch(IOException e) {
			e.printStackTrace();
		}
		stage.showAndWait();
		return fxmlLoader.<Dialog<T>>getController().getResult();
	}

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
