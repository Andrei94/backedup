package dashboard;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;
import utils.WindowPayload;

import java.io.File;
import java.util.stream.Collectors;

public class Dashboard implements WindowPayload<LoginPayloadDashboard> {
	public TilePane foldersToSync;
	public Label loggedInUsername;
	private DashboardController controller;
	private TileDecorator decorator = new TileDecorator();

	public void uploadFolders(MouseEvent mouseEvent) {
		controller.saveFolders();
		controller.getSyncList().forEach(folder ->
				new UploadDirectoryWorker(controller,
						new FolderProgressMediator(folder, getFolderImage(getFolderTile(folder)))
				).restart()
		);
	}

	private ImageViewAdapter getFolderImage(Pane folderTile) {
		return new ImageViewAdapter((ImageView) folderTile.getChildren().get(1));
	}

	private Pane getFolderTile(Folder folder) {
		int index = controller.getSyncList().indexOf(folder);
		return (Pane) foldersToSync.getChildren().get(index);
	}

	public void downloadFolders(MouseEvent mouseEvent) {
		controller.getSyncList().forEach(folder ->
				new DownloadDirectoryWorker(controller,
						new FolderProgressMediator(folder, getFolderImage(getFolderTile(folder)))
				).restart()
		);
	}

	@Override
	public void setPayload(LoginPayloadDashboard payload) {
		controller = new DashboardController(payload.getLoggedInUser());
		controller.setLoggedInUser(payload.getLoggedInUser());
		controller.setDriveGateway(payload.getDriveGateway());
		loggedInUsername.setText(controller.getLoggedInText(payload.getLoggedInUser().getName()));
		addContents(getFolderImage());
		cleanupOnWindowClose();
	}

	private void addContents(Image image) {
		foldersToSync.getChildren().addAll(foldersToSync.getChildren().size() - 1,
				controller.getSyncList().stream().map(it -> decorator.tile(image, it)).collect(Collectors.toList()));
	}

	public void openDirectoryChooser(MouseEvent mouseEvent) {
		File file = new DirectoryChooser().showDialog(foldersToSync.getScene().getWindow());
		controller.addToSyncList(file).ifPresent(folder -> foldersToSync.getChildren().add(foldersToSync.getChildren().size() - 1, decorator.tile(getFolderImage(), folder)));
	}

	private Image getFolderImage() {
		return new Image(controller.getFolderImagePath(), 80, 80, true, true);
	}

	private void cleanupOnWindowClose() {
		loggedInUsername.getScene().getWindow().setOnCloseRequest(event -> controller.cleanup());
	}
}
