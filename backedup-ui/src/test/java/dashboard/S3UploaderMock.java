package dashboard;

import authentication.User;
import uploader.ObjectUploader;

import java.nio.file.Path;

class S3UploaderMock implements ObjectUploader {
	private int timesUploadDirectoryCalled = 0;
	private User loggedInUser;
	private String host;

	@Override
	public boolean uploadDirectory(Path directory) {
		if(loggedInUser == null || !loggedInUser.isAuthenticated())
			return false;
		timesUploadDirectoryCalled++;
		return true;
	}

	@Override
	public void setLoggedInUser(User user) {
		this.loggedInUser = user;
	}

	@Override
	public boolean uploadFile(Path file) {
		return false;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	int getTimesUploadDirectoryCalled() {
		return timesUploadDirectoryCalled;
	}
}
