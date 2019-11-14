import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("mainWindow.fxml")), 600, 400));
		primaryStage.getScene().getStylesheets().add(getClass().getResource("button.css").toExternalForm());
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
