package dashboard;

import authentication.User;
import downloader.ObjectDownloader;

public class S3DownloaderMock implements ObjectDownloader {
	private User loggedInUser;
	public boolean shutdownCalled = false;

	@Override
	public boolean downloadDirectory(String remoteDir, String localDir) {
		return loggedInUser != null && loggedInUser.isAuthenticated();
	}

	@Override
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	}

	@Override
	public void shutdown() {
		shutdownCalled = true;
	}

	@Override
	public void downloadFolderList() {
	}
}
