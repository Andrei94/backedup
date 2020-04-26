package downloader;

import adapter.S3Adapter;
import authentication.User;
import file.LocalFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class S3ObjectDownloader implements ObjectDownloader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private S3Adapter adapter;
	private User user;

	public S3ObjectDownloader(S3Adapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public boolean downloadDirectory(String remoteDir, String localDir) {
		return downloadDirectory(remoteDir, LocalFile.fromPath(Paths.get(localDir)));
	}

	boolean downloadDirectory(String remoteDir, LocalFile localDir) {
		if(user == null || !user.isAuthenticated())
			return false;
		logger.info("Downloading " + remoteDir + " to " + getDestination(remoteDir, localDir));
		Optional<LocalFile> isDownloadSuccessful = adapter.downloadDirectory(user.getName() + "/My Local PC/" + remoteDir, localDir.getPath());
		if(isDownloadSuccessful.isPresent()) {
			moveDownloadedFolder(remoteDir, localDir);
			logger.info("Finished downloading " + remoteDir + " to " + getDestination(remoteDir, localDir));
			return true;
		}
		logger.warning("Failed to download " + remoteDir + " to " + getDestination(remoteDir, localDir));
		return false;
	}

	void moveDownloadedFolder(String remoteDir, LocalFile localDir) {
		try {
			moveFolder(getLocalDownloadDirectory(remoteDir, localDir), getDestination(remoteDir, localDir));
			deleteFolder(getLocalDownloadDirectory("", localDir));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	void moveFolder(Path src, Path dst) throws IOException {
		if(Files.exists(src)) {
			FileUtils.copyDirectory(src.toFile(), dst.toFile());
		}
	}

	void deleteFolder(Path localDownloadDirectory) throws IOException {
		FileUtils.deleteDirectory(localDownloadDirectory.toFile());
	}

	Path getLocalDownloadDirectory(String remoteDirName, LocalFile localDir) {
		return Paths.get(localDir.getPath() + "/" + user.getName() + "/My Local PC/" + remoteDirName);
	}

	Path getDestination(String remoteDir, LocalFile localDir) {
		return Paths.get(localDir.getPath() + "/" + remoteDir);
	}

	@Override
	public void setLoggedInUser(User user) {
		this.user = user;
		user.getCredentials().ifPresent(adapter::updateCredentials);
	}

	@Override
	public void shutdown() {
		adapter.shutdownTransferManager();
	}

	@Override
	public void downloadFolderList() {
		if(user == null || !user.isAuthenticated())
			return;
		String folderListFile = "list.txt";
		logger.info("Downloading " + folderListFile);
		Optional<LocalFile> downloadedFile = adapter.downloadFile(user.getName() + "/" + folderListFile);
		if(downloadedFile.isPresent()) {
			moveDownloadedListFile(folderListFile, downloadedFile.get());
			logger.info("Finished downloading list.txt to " + downloadedFile.get().getPath());
		}
	}

	private void moveDownloadedListFile(String folderListFile, LocalFile downloadedFile) {
		try {
			FileUtils.moveFile(downloadedFile.toFile(), new File(folderListFile));
			FileUtils.deleteDirectory(downloadedFile.toFile());
		} catch(IOException e) {
			logger.log(Level.WARNING, "Failed to download list.txt", e);
		}
	}
}
