package dashboard;

import authentication.User;
import downloader.ObjectDownloader;
import downloader.ObjectDownloaderFactory;
import drive.DriveGateway;
import uploader.ObjectUploader;
import uploader.ObjectUploaderFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class DashboardController {
	private List<Folder> folders;
	private SyncFolderSaver saver;
	private ObjectUploader uploader;
	private ObjectDownloader downloader;
	private User loggedInUser;
	private DriveGateway driveGateway;

	DashboardController() {
		this(ObjectUploaderFactory.createS3ObjectUploader(), ObjectDownloaderFactory.createObjectDownloader(), new SyncFolderLoader(), new SyncFolderSaver());
	}

	DashboardController(ObjectUploader uploader, ObjectDownloader downloader, SyncFolderLoader loader, SyncFolderSaver saver) {
		this.uploader = uploader;
		this.downloader = downloader;
		this.saver = saver;
		folders = loader.load();
	}

	Optional<Folder> addToSyncList(File folder) {
		if(folder == null || isSubfolder(folder))
			return Optional.empty();
		Folder f = new Folder(Paths.get(folder.getPath()));
		folders.add(f);
		return Optional.of(f);
	}

	private boolean isSubfolder(File folder) {
		if(folders.isEmpty())
			return false;
		for(Folder value : folders) {
			Optional<Path> subpath = value.relativize(folder);
			if(subpath.isPresent() && !hasToGoBackInPath(subpath.get()))
				return true;
		}
		return false;
	}

	private boolean hasToGoBackInPath(Path subpath) {
		return subpath.startsWith("..");
	}

	boolean download(Folder folder) {
		downloader.setLoggedInUser(loggedInUser);
		return downloader.downloadDirectory(folder.path.getFileName().toString(), folder.path.getParent().toString());
	}

	String getWarningTitle() {
		return "Remove folder from Sync";
	}

	String getWarningText() {
		return "Are you sure you want to remove the folder from the sync?";
	}

	List<Folder> getSyncList() {
		return folders;
	}

	public String getLoggedInText(String username) {
		if(username == null || username.isEmpty())
			return "";
		return "Welcome " + username;
	}

	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	}

	String getFolderImagePath() {
		return getClass().getResource("/icons/dashboard/folder_80px.png").toExternalForm();
	}

	void cleanup() {
		downloader.shutdown();
		driveGateway.unmount();
	}

	public String getDownloadFinishedText() {
		return "Download finished";
	}

	public String getInformationTitle() {
		return "Backedup";
	}

	public String getUploadFinishedText() {
		return "Upload Finished";
	}

	public boolean upload(Folder folder) {
		uploader.setLoggedInUser(loggedInUser);
		uploader.uploadDirectory(folder.path);
		return true;
	}

	public void saveFolders() {
		if(loggedInUser == null || !loggedInUser.isAuthenticated())
			return;
		saver.save(folders.stream().map(folder -> folder.path.toString()).collect(Collectors.toList()));
	}

	public String getWIPImageUrl() {
		return getClass().getResource("/icons/dashboard/refresh_40px.png").toExternalForm();
	}

	public String getFailedImageUrl() {
		return getClass().getResource("/icons/dashboard/cancel_40px.png").toExternalForm();
	}

	public String getSucceededImageUrl() {
		return getClass().getResource("/icons/dashboard/ok_40px.png").toExternalForm();
	}

	public void setDriveGateway(DriveGateway driveGateway) {
		this.driveGateway = driveGateway;
	}
}