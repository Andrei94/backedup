import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class S3ObjectDownloader implements ObjectDownloader {
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
		boolean isDownloadSuccessful = adapter.downloadDirectoryExcludingGlacier(username + "/" + remoteDir, localDir.getPath()) &&
				exists(getLocalDownloadDirectory(remoteDir, localDir));
		if(isDownloadSuccessful)
			moveDownloadedFolder(remoteDir, localDir);
		return isDownloadSuccessful;
	}

	boolean exists(Path file) {
		return Files.exists(file);
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
		Files.move(src, dst, StandardCopyOption.REPLACE_EXISTING);
	}

	void deleteFolder(Path localDownloadDirectory) throws IOException {
		Files.delete(localDownloadDirectory);
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
}
