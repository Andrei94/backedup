import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class MainWindow implements Initializable {
	public ListView<Folder> foldersToSync;
	public Label loggedInUsername;
	private MainWindowController controller = new MainWindowController();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		foldersToSync.getItems().setAll(controller.getSyncList());
	}

	public void openFileBrowser(MouseEvent mouseEvent) {
		File file = new DirectoryChooser().showDialog(foldersToSync.getScene().getWindow());
		controller.addToSyncList(file).ifPresent(folder -> foldersToSync.getItems().add(folder));
	}

	public void deleteTreeItemWithConfirmation(MouseEvent mouseEvent) {
		Folder selectedItem = foldersToSync.getSelectionModel().getSelectedItem();
		if(getSelectedButtonFromAlert() == ButtonBar.ButtonData.OK_DONE && controller.removeFromSyncList(selectedItem)) {
			foldersToSync.getItems().remove(selectedItem);
		}
	}

	private ButtonBar.ButtonData getSelectedButtonFromAlert() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(controller.getWarningTitle());
		alert.setContentText(controller.getWarningText());
		alert.getButtonTypes().setAll(
				new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE),
				new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE)
		);
		AtomicReference<ButtonBar.ButtonData> buttonData = new AtomicReference<>();
		alert.showAndWait().ifPresent(buttonType -> buttonData.set(buttonType.getButtonData()));
		return buttonData.get();
	}

	public void syncFolders(MouseEvent mouseEvent) {
		controller.sync();
	}

	public void openLoginWindow(MouseEvent mouseEvent) {
		String username = DialogOpener.openDialog("loginDialog.fxml", 400, 300);
		loggedInUsername.setText(controller.getLoggedInText(username));
	}
}
