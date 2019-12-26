package downloader;

import authentication.User;

public interface ObjectDownloader {
	boolean downloadDirectory(String remoteDir, String localDir);

	void setLoggedInUser(User user);

	void shutdown();
}
