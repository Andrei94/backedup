import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

public class S3ObjectDownloader implements ObjectDownloader {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private S3Adapter adapter;
	private String username;

	public S3ObjectDownloader(S3Adapter adapter) {
		this.adapter = adapter;
	}

	@Override
	public boolean downloadDirectory(String remoteDir, String localDir) {
		return downloadDirectory(remoteDir, LocalFile.fromPath(Paths.get(localDir)));
	}

	boolean downloadDirectory(String remoteDir, LocalFile localDir) {
		if(username == null || username.isEmpty())
			return false;
		logger.info("Downloading " + remoteDir);
		Optional<LocalFile> isDownloadSuccessful = adapter.downloadDirectoryExcludingGlacier(username + "/" + remoteDir, localDir.getPath());
		if(isDownloadSuccessful.isPresent()) {
			moveDownloadedFolder(remoteDir, localDir);
			return true;
		}
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
		return Paths.get(localDir.getPath() + "/" + username + "/" + remoteDirName);
	}

	Path getDestination(String remoteDir, LocalFile localDir) {
		return Paths.get(localDir.getPath() + remoteDir);
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.username = username;
	}

	@Override
	public void shutdown() {
		adapter.shutdownTransferManager();
	}
}
