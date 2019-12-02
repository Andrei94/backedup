import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class MainWindowController {
	private List<Folder> folders;
	private SyncFolderSaver saver;
	private ObjectUploader uploader;
	private ObjectDownloader downloader;
	private String loggedInUsername;

	MainWindowController() {
		this(ObjectUploaderFactory.createS3ObjectUploader(), ObjectDownloaderFactory.createObjectDownloader(), new SyncFolderLoader(), new SyncFolderSaver());
	}

	MainWindowController(ObjectUploader uploader, ObjectDownloader downloader, SyncFolderLoader loader, SyncFolderSaver saver) {
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

	boolean removeFromSyncList(Folder folder) {
		if(folder == null)
			return false;
		return folders.remove(folder);
	}

	void sync() {
		saver.save(folders.stream().map(folder -> folder.path.toString()).collect(Collectors.toList()));
		uploader.setLoggedInUsername(loggedInUsername);
		folders.forEach(folder -> uploader.uploadDirectory(folder.path));
	}

	boolean download() {
		downloader.setLoggedInUsername(loggedInUsername);
		return folders.stream().anyMatch(folder -> downloader.downloadDirectory(folder.path.getFileName().toString(), folder.path.getParent().toString()));
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

	public void setLoggedInUsername(String username) {
		this.loggedInUsername = username;
	}

	String getFolderImagePath() {
		return getClass().getResource("icons/folder_80px.png").toExternalForm();
	}
}