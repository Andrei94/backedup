import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindow implements Initializable, WindowPayload<String> {
	public TilePane foldersToSync;
	public Label loggedInUsername;
	private MainWindowController controller = new MainWindowController();
	private TileDecorator decorator = new TileDecorator();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addContents(getFolderImage());
	}

	private void addContents(Image image) {
		foldersToSync.getChildren().addAll(foldersToSync.getChildren().size() - 1,
				controller.getSyncList().stream().map(it -> decorator.tile(image, it)).collect(Collectors.toList()));
	}

	public void openDirectoryChooser(MouseEvent mouseEvent) {
		File file = new DirectoryChooser().showDialog(foldersToSync.getScene().getWindow());
		controller.addToSyncList(file).ifPresent(folder -> foldersToSync.getChildren().add(decorator.tile(getFolderImage(), folder)));
	}

	private Image getFolderImage() {
		return new Image(controller.getFolderImagePath(), 80, 80, true, true);
	}

	public void syncFolders(MouseEvent mouseEvent) {
		controller.sync();
	}

	public void downloadFolders(MouseEvent mouseEvent) {
		controller.download();
	}

	@Override
	public void setPayload(String payload) {
		controller.setLoggedInUsername(payload);
		loggedInUsername.setText(controller.getLoggedInText(payload));
	}
}
