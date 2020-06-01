package dashboard;

import drive.SubscriptionSpace;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import utils.WindowPayload;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class Dashboard implements WindowPayload<LoginPayloadDashboard> {
	public TilePane foldersToSync;
	public Label loggedInUsername;
	public ProgressBar subscriptionSpaceProgress;
	public Text subscriptionSpaceProgressText;
	private DashboardController controller;
	private TileDecorator decorator = new TileDecorator();
	private ShowSubscriptionService showSubscriptionService;

	public void uploadFolders(MouseEvent mouseEvent) {
		controller.saveFolders();
		UploadFolderListWorker uploadFolderListWorker = new UploadFolderListWorker(controller);
		uploadFolderListWorker.setOnRunning(event -> setImageForFolder(controller.getWIPImageUrl()));
		uploadFolderListWorker.setOnFailed(event -> setImageForFolder(controller.getFailedImageUrl()));
		uploadFolderListWorker.setOnSucceeded(event -> {
			List<FolderProgressMediator> mediators = controller.getSyncList().stream()
					.map(folder -> new FolderProgressMediator(folder, getFolderImage(getFolderTile(folder))))
					.collect(Collectors.toList());
			new UploadDirectoryWorker(controller, mediators).restart();
		});
		uploadFolderListWorker.restart();
	}

	private void setImageForFolder(String imageUrl) {
		controller.getSyncList().forEach(folder ->
				new FolderProgressMediator(folder, getFolderImage(getFolderTile(folder))).update(imageUrl));
	}

	public void downloadFolders(MouseEvent mouseEvent) {
		controller.getSyncList().forEach(folder ->
				new DownloadDirectoryWorker(controller,
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

	@Override
	public void setPayload(LoginPayloadDashboard payload) {
		controller = new DashboardController(payload.getLoggedInUser());
		controller.setLoggedInUser(payload.getLoggedInUser());
		controller.setDriveGateway(payload.getDriveGateway());
		controller.setObjectDownloader(payload.getObjectDownloader());
		loggedInUsername.setText(controller.getLoggedInText(payload.getLoggedInUser().getName()));
		showSubscriptionService = new ShowSubscriptionService(controller);
		showSubscriptionService.setOnSucceeded(event -> showSubscriptionSpace((SubscriptionSpace) event.getSource().getValue()));
		showSubscriptionService.restart();
		addContents(getFolderImage());
		cleanupOnWindowClose();
	}

	private void addContents(Image image) {
		foldersToSync.getChildren().addAll(foldersToSync.getChildren().size() - 1,
				controller.getSyncList().stream().map(it ->
						decorator.contextMenu(decorator.tile(image, it), "Remove", event -> {
							foldersToSync.getChildren().remove(controller.getSyncList().indexOf(it));
							controller.removeFromSyncList(it);
							controller.saveFolders();
						})).collect(Collectors.toList()));
	}

	private void showSubscriptionSpace(SubscriptionSpace subscriptionSpace) {
		double usedSpace = subscriptionSpace.getUsedSpace();
		double totalSpace = subscriptionSpace.getTotalSpace();
		subscriptionSpaceProgress.setProgress(usedSpace / totalSpace);
		subscriptionSpaceProgressText.setText(String.format("%.2f GB / %.2f GB", usedSpace / (1024 * 1024 * 1024), totalSpace / (1024 * 1024 * 1024)));
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
