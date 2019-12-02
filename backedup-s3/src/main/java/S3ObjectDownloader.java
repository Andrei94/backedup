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
				exists(remoteDir, localDir);
		if(isDownloadSuccessful)
			moveDownloadedFolder(remoteDir, localDir);
		return isDownloadSuccessful;
	}

	private void moveDownloadedFolder(String remoteDir, LocalFile localDir) {
		try {
			Files.move(getLocalDownloadDirectory(remoteDir, localDir), getDestination(remoteDir, localDir), StandardCopyOption.REPLACE_EXISTING);
			Files.delete(getLocalDownloadDirectory("", localDir));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	boolean exists(String remoteDir, LocalFile localDir) {
		return Files.exists(getLocalDownloadDirectory(remoteDir, localDir));
	}

	private Path getLocalDownloadDirectory(String remoteDirName, LocalFile localDir) {
		return Paths.get(localDir.getPath() + "/" + username + "/" + remoteDirName);
	}

	private Path getDestination(String remoteDir, LocalFile localDir) {
		return Paths.get(localDir.getPath() + remoteDir);
	}

	@Override
	public void setLoggedInUsername(String username) {
		this.username = username;
	}
}
