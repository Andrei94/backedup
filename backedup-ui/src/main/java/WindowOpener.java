import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

class WindowOpener {
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

	static <T> void openWindow(String fxmlFile, T windowPayload) {
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
